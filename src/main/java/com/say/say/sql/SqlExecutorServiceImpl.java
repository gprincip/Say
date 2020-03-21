package com.say.say.sql;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;

import com.say.say.model.Saying;


public class SqlExecutorServiceImpl implements SqlExecutorService {
	
	@Autowired
	SqlReader sqlReader;
	
	public void test() {
		
		SessionFactory f = new Configuration().configure().buildSessionFactory();
		Session session = f.openSession();
		Transaction transaction = session.beginTransaction();
		String queryString = sqlReader.getSqlQuery("getLastSayingFromIp");
		
		@SuppressWarnings("unchecked")
		NativeQuery<Saying> query = session.createSQLQuery(queryString);
		query.setParameter("ip", "0:0:0:0:0:0:0:1");
		transaction.commit();
		session.close();
		List<Saying> result = query.getResultList();
		System.out.println(result);
		
	}
	
}
