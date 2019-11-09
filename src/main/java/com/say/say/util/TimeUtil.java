package com.say.say.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.say.say.model.TimeUnit;

public class TimeUtil {
	
	private static final Logger log = LoggerFactory.getLogger(TimeUtil.class);

	/**
	 * 
	 * @param ms       milliseconds
	 * @param timeUnit time unit to convert milliseconds to
	 * @return time in desired timeUnit or null if timeUnit is still not
	 *         supported/implemented
	 */
	public static Double millisecondsToTimeUnit(double ms, TimeUnit timeUnit) {

		switch (timeUnit) {
		case MILLISECOND:
			return ms;
		case SECOND:
			return ms / 1000.0;
		case MINUTE:
			return (ms / 1000.0) / 60.0;
		case HOUR:
			return (ms / 1000.0) / 60.0 / 60.0;
		}

		return null;
	}

	private static TimeUnit discoverTimeUnitFromString(String timeUnitStr) {

		if (timeUnitStr.equalsIgnoreCase("ms") || timeUnitStr.equalsIgnoreCase("milliseconds")
				|| timeUnitStr.equalsIgnoreCase("millisecond")) {
			return TimeUnit.MILLISECOND;
		} else if (timeUnitStr.equalsIgnoreCase("second") || timeUnitStr.equalsIgnoreCase("seconds")) {
			return TimeUnit.SECOND;
		} else if (timeUnitStr.equalsIgnoreCase("minute") || timeUnitStr.equalsIgnoreCase("minutes")) {
			return TimeUnit.MINUTE;
		}

		return TimeUnit.UNKNOWN;
	}

	/**
	 * Returns milliseconds consisted in each time unit
	 * @param timeUnit
	 * @return
	 */
	private static Long unitToMilliseconds(TimeUnit timeUnit) {

		switch (timeUnit) {
		case MILLISECOND:
			return 1L;
		case SECOND:
			return 1000L;
		case MINUTE:
			return 1000L * 60;
		case HOUR:
			return 1000L * 60 * 60;
		}

		return null;

	}
	
	/**
	 * Populates a map which key is a timeUnit and value is a amount of that unit for passed number of milliseconds. </br>
	 * For example, 1000 * 60 + 2500 (1  minute and 2.5 seconds) could produce 1minute, 2 seconds and 500 milliseconds
	 * @param ms
	 * @param units
	 * @return
	 */
	public static Map<TimeUnit, Double> populateTimeUnitQuantityMap(double ms, List<TimeUnit> units) {
		
		List<TimeUnit> sorted = sortTimeUnitsDesc(units);
		
		//map holding data for each time unit, how much of it there is in the passed ms
		Map<TimeUnit, Double> timeUnitQuantityMap = new TreeMap<TimeUnit, Double>();
		
		//units start from the biggest
		for(TimeUnit unit : sorted) {
			
			Double quantity = millisecondsToTimeUnit(ms, unit);
			if(quantity < 1) { //if quantity is less then 1, skip current unit and continue on smaller unit
				continue;
			}
			
			Double quantityRounded = Math.floor(quantity);
			timeUnitQuantityMap.put(unit, quantityRounded);
			ms -= (quantityRounded * unitToMilliseconds(unit)); //substract milliseconds that are stored inside current unit (in 1sec 1000ms are stored...)			
			
		}
		
		return timeUnitQuantityMap;
	}

	public static List<String> formatTimeUnitQuantityMap(Map<TimeUnit, Double> timeUnitQuantityMap) {
		
		List<String> result = new ArrayList<String>();
		
		for(Map.Entry<TimeUnit, Double> entry : timeUnitQuantityMap.entrySet()) {
			
			result.add(Math.round(entry.getValue()) + " " + formatTimeUnit(entry.getKey(), entry.getValue()));
			
		}
		
		Collections.reverse(result);
		
		return result;
	}
	
	private static String formatTimeUnit(TimeUnit unit, double quantity) {
		switch(unit) {
		case MILLISECOND : return "ms";
		case SECOND : return "sec";
		case MINUTE : return quantity > 1 ? "mins" : "min";
		case HOUR : return quantity > 1 ? "hours" : "hour";
		}
		return null;
	}

	private static List<TimeUnit> sortTimeUnitsDesc(List<TimeUnit> units) {
		
		Collections.sort(units);
		List<TimeUnit> sorted = new ArrayList<TimeUnit>();
		
		for(int i=units.size()-1; i>=0; i--) {
			sorted.add(units.get(i));
		}
		
		return sorted;
		
	}
	
	public static String formatCooldownTimeInMsForDisplay(String msStr, List<String> timeUnitsToDisplayFromConfig, int numberOfUnitsToDisplay) {
		
		List<TimeUnit> timeUnits = new ArrayList<TimeUnit>();
		
		for(String timeUnitStr : timeUnitsToDisplayFromConfig) {
			
			TimeUnit unit = discoverTimeUnitFromString(timeUnitStr);
			if(unit == TimeUnit.UNKNOWN) {
				log.error("Passed time unit is not recognized! [" + timeUnitStr + "]");
				continue;
			}
			timeUnits.add(unit);
		}
		
		Double ms = null;
		try {
			ms = Double.parseDouble(msStr);
		}catch(Exception e) {
			log.error("Error parsing milliseconds to double! [" + msStr + "]");
			return null;
		}
		
		Map<TimeUnit, Double> timeUnitsQuantityMap = populateTimeUnitQuantityMap(ms, timeUnits);
		List<String> formatted = formatTimeUnitQuantityMap(timeUnitsQuantityMap);
		if(formatted.size() > numberOfUnitsToDisplay) {
			formatted = formatted.subList(0, numberOfUnitsToDisplay-1);
		}
		
		String result = "";
		for(int i=0; i<formatted.size(); i++) {
			
			result += formatted.get(i);
			if(i != formatted.size()-1) {
				result +=", ";
			}
			
		}
		
		return result;
	}
	
	public static List<String> CsvTimeUnitsToList(String csv) {
		
		List<String> retList = new ArrayList<String>();
		
		String []splitted = csv.split(",");
		for(String str : splitted) {
			retList.add(str);
		}
		
		return retList;
	}
	
	public static void main(String []args) {
		
		
		List<TimeUnit> timeUnits = new ArrayList<TimeUnit>();
		timeUnits.add(TimeUnit.MINUTE);
		timeUnits.add(TimeUnit.SECOND);
		timeUnits.add(TimeUnit.MILLISECOND);
		timeUnits.add(TimeUnit.HOUR);
		
		List<String> res = formatTimeUnitQuantityMap(populateTimeUnitQuantityMap(1000*60*3 + 2500, timeUnits));
		System.out.println(res);
		
	}

}
