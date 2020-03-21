package com.say.say.sql;

import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;
/**
 * Class corresponding to xml file sqlMap.xml that is holding sql queries
 * Used to load and store sql queries.
 * @author gavrilo
 *
 */
@Component
@XmlRootElement
public class SqlMap {

	private Map<String, String> sqls;

	public Map<String, String> getSqls() {
		return sqls;
	}

	public void setSqls(Map<String, String> sqls) {
		this.sqls = sqls;
	}
	
	public String getSqlQuery(String key) {

		return sqls.get(key);
	}
	
}
