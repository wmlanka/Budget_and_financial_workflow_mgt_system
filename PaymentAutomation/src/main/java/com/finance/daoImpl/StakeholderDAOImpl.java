package com.finance.daoImpl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.finance.dao.BaseDAOSupport;
import com.finance.dao.StakeholderDAO;
import com.finance.domain.Stakeholder;
import com.finance.domain.UserSession;
import com.finance.util.BaseException;
import com.finance.util.ConstraintException;

public class StakeholderDAOImpl extends BaseDAOSupport implements StakeholderDAO{
	//private SessionFactory sessionFactory;
	  
    public StakeholderDAOImpl(SessionFactory sessionFactory) {
        //this.sessionFactory = sessionFactory;
        setSessionFactory(sessionFactory);
    }
      

	@Override
	@Transactional
	public List<Stakeholder> getAllStakeholder() throws BaseException{
//		List<Stakeholder> list = (List<Stakeholder>)getSessionFactory().getCurrentSession().createCriteria(Stakeholder.class)
//	                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
//	                .list();
//	    return list;
		List<Stakeholder> list = null;
		try {
			list = getSession().createCriteria(Stakeholder.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).
					addOrder(Order.desc("lastUpdatedDate")).list();
		} catch (HibernateException e) {
			throw new BaseException(e);
		} catch (Exception e) {
			throw new BaseException(e);
		}
		return list;
	}


	@Override
	@Transactional
	public Stakeholder getStakeholderById(int stakeholderId) throws BaseException {
		Stakeholder stakeholder = null;
		stakeholder = (Stakeholder) getSession().load(Stakeholder.class, stakeholderId);
		return stakeholder;
	}


	@Override
	@Transactional
	public void deleteStakeholder(int stakeholderId, UserSession userSession) throws BaseException{
		Stakeholder stakeholder = null;
		stakeholder = (Stakeholder) getSession().load(Stakeholder.class, stakeholderId);
		if(stakeholder != null){
			//getSession().delete(approvalDocument);
			try {
				delete(stakeholder);
			} catch (BaseException e) {
				throw new BaseException(e);

			} catch (ConstraintException e) {
				throw new BaseException("error.stakeholderDeletion");
			}
		}
	}


	@Override
	@Transactional
	public void createStakeholder(Stakeholder stakeholder, UserSession userSession)throws BaseException {
		stakeholder.setLastUpdatedBy(userSession.getUserId());
		stakeholder.setCreatedBy(userSession.getUserId());
		stakeholder.setCreatedDate(new Date());
		stakeholder.setLastUpdatedDate(new Date());
		stakeholder.setStatus("A");
		getSession().persist(stakeholder);
	}


	@Override
	@Transactional
	public void updateStakeholder(Stakeholder stakeholder, UserSession userSession) throws BaseException{

		getSession().saveOrUpdate(stakeholder);
	}
	
	@Override
	@Transactional
	public List<Stakeholder> getAllActiveStakeholder() throws BaseException{
		List<Stakeholder> list = null;
		try {
			Criteria criteria = getSession().createCriteria(Stakeholder.class);
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
			criteria.add(Restrictions.eq("status", "A"));
			list = (List<Stakeholder>) criteria.list();
		} catch (HibernateException e) {
			throw new BaseException(e);
		} catch (Exception e) {
			throw new BaseException(e);
		}
		return list;
	}
	
	

}
