package com.say.say.sql;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

/**
 * Class used to unmarshal (read) xml file holding sql queries (sqlMap.xml)
 * and obtain them. 
 * @author gavrilo
 *
 */
@Component
public class SqlReader {

	@Autowired
	SqlMap sqlMap;
	
	JAXBContext context;
	
	private void init() {
		try {
			context = JAXBContext.newInstance(SqlMap.class);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Method for obtaining sql query as a string from a key
	 * @param key of the query
	 * @return sql query as a string
	 */
	public String getSqlQuery(String key) {
		
		if(context == null) {
			init();
		}
		
		try {
			Unmarshaller unmarshaller = context.createUnmarshaller();
			File sqlMapFile = new ClassPathResource("static/files/xml/sqlMap.xml").getFile();
			SqlMap map = (SqlMap)unmarshaller.unmarshal(sqlMapFile);
			return map.getSqlQuery(key);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
