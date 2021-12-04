package com.finance.dao;

import java.io.Serializable;
import java.util.List;

/*import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.Transaction;*/

import org.hibernate.HibernateException;
import org.hibernate.JDBCException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.DisposableBean;

import com.finance.util.BaseException;
import com.finance.util.ConstraintException;

public class BaseDAOSupport implements DisposableBean/* extends HibernateDaoSupport */{
	private static SessionFactory sessionFactory;

	
	public Session openSession() throws BaseException{
		return sessionFactory.openSession();
	}
	
	public Session getSession() throws BaseException{
		return sessionFactory.getCurrentSession();
	}	

	public void closeSession(Session session) throws BaseException{
			if (session != null)
				session.close();
	}

	public void rollbackTransaction(Transaction tn) throws BaseException{
		if (tn != null)
			try {
				tn.rollback();
			} catch (HibernateException e) {
				throw new BaseException(e);
			}
	}

	public Object save(Object object) throws BaseException{
		Session session = openSession();
		Transaction tn = null;

		try{
			tn = (Transaction) session.beginTransaction();
			session.save(object);
			tn.commit();
		}
		catch (Exception ex){
			rollbackTransaction(tn);			
            
			if (ex instanceof ConstraintViolationException){
        		throw new BaseException("errors.uniqueConstraintError");//ERROR_UNIQUE_CONSTRAINT
			}
            if (ex instanceof JDBCException){
				/*
				 * if(((JDBCException)ex).getSQLException().getMessage().contains("Duplicate"))
				 * { throw new BaseException("error.budgetDuplicateEntry"); }
				 */
                throw new BaseException(((JDBCException)ex).getSQLException());
            }
			throw new BaseException(ex);
		}
		finally{
			closeSession(session);
		}

		return object;
	}

	public void update(Object object) throws BaseException{
		Session session = openSession();
		Transaction tn = null;

		try{
			tn = session.beginTransaction();
			session.update(object);
			tn.commit();
		}
		catch (Exception ex){
			rollbackTransaction(tn);
			
			if (ex instanceof ConstraintViolationException){
				throw new BaseException("errors.uniqueConstraintError");
			}
			throw new BaseException(ex);
		}
		finally{
			closeSession(session);
		}
	}

	
	public void delete(Object object) throws BaseException,ConstraintException { 
	   Session session = openSession(); 
	   Transaction tn = null;
	  
	   try {
		   tn =  session.beginTransaction(); 
		   session.delete(object); 
		   tn.commit(); 
	   }
	   catch (HibernateException e) { 
		   rollbackTransaction(tn);
	  
		   if (e instanceof ConstraintViolationException) {
			   throw new ConstraintException(e.getMessage()); 
		   }
		   throw new BaseException(e); 
		   
	    } finally { 
	    	closeSession(session); 
	    } 
	 }
	 
	public Object load(Class<?> classType, Serializable id)throws BaseException{
			Session session = openSession();
			try{			
				Object object = session.load(classType, id);
				return object;
			}
			catch (HibernateException e){			
				throw new BaseException(e);
			}
			finally{
				closeSession(session);
			}
	}

	public List<?> query(String fromClause) throws BaseException{
		Session session = openSession();
		
		try
		{
			Query query = session.createQuery(fromClause);
			query.setCacheable(true);
			
			List<?> list = query.list();		

			return list;
		}
		catch (HibernateException he)
		{			
			throw new BaseException(he);
		}
		finally
		{
			closeSession(session);
		}
	}
	
	public void destroy() throws BaseException{
		try{
			sessionFactory.close();
		}catch(HibernateException he){
			throw new BaseException(he.toString());//**
		}
	}

	public void setSessionFactory(SessionFactory sessionFactory){
		BaseDAOSupport.sessionFactory = sessionFactory;
	}

	public void deleteAll(List<Object> list) throws BaseException,ConstraintException { 
		   Session session = openSession(); 
		   Transaction tn = null;
		  
		   try {
			   tn =  session.beginTransaction(); 
			   for(Object object : list) {
				   session.delete(object); 
			   }
			   tn.commit(); 
		   }
		   catch (HibernateException e) { 
			   rollbackTransaction(tn);
		  
			   if (e instanceof ConstraintViolationException) {
				   throw new ConstraintException(e.getMessage()); 
			   }
			   throw new BaseException(e); 
			   
		    } finally { 
		    	closeSession(session); 
		    } 
	}
	
	public void saveAll(List<Object> objects) throws BaseException{
		Session session = openSession();
		Transaction tn = null;

		try{
			tn = (Transaction) session.beginTransaction();
			for(Object object : objects) {
				session.save(object);
			}
			tn.commit();
		}
		catch (Exception ex){
			rollbackTransaction(tn);			
            
			if (ex instanceof ConstraintViolationException){
        		throw new BaseException("errors.uniqueConstraintError");
			}
            if (ex instanceof JDBCException){
				/*
				 * if(((JDBCException)ex).getSQLException().getMessage().contains("Duplicate"))
				 * { throw new BaseException("error.budgetDuplicateEntry"); }
				 */
                throw new BaseException(((JDBCException)ex).getSQLException());
            }
			throw new BaseException(ex);
		}
		finally{
			closeSession(session);
		}

	}

}
