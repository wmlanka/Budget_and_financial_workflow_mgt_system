package com.finance.daoImpl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;

import com.finance.dao.BaseDAOSupport;
import com.finance.dao.LedgerAccountDAO;
import com.finance.domain.LedgerAccount;
import com.finance.domain.UserSession;
import com.finance.util.BaseException;
import com.finance.util.ConstraintException;

public class LedgerAccountDAOImpl extends BaseDAOSupport implements LedgerAccountDAO{

	@Override
	@Transactional
	public List<LedgerAccount> getAllLedgerAccount() throws BaseException {
		List<LedgerAccount> list = null;
		try {
			list = getSession().createCriteria(LedgerAccount.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		} catch (HibernateException e) {
			throw new BaseException(e);
		} catch (Exception e) {
			throw new BaseException(e);
		}
		return list;
	}

	@Override
	@Transactional
	public LedgerAccount getLedgerAccountById(int ledgerAccountId) throws BaseException {
		LedgerAccount ledgerAccount = (LedgerAccount) getSession().load(LedgerAccount.class, ledgerAccountId);
		return ledgerAccount;
	}

	@Override
	@Transactional
	public void createLedgerAccount(LedgerAccount ledgerAccount, UserSession userSession) throws BaseException {
		ledgerAccount.setLastUpdatedBy(userSession.getUserId());
		ledgerAccount.setCreatedBy(userSession.getUserId());
		ledgerAccount.setCreatedDate(new Date());
		ledgerAccount.setLastUpdatedDate(new Date());
		ledgerAccount.setStatus("A");
		save(ledgerAccount);
		//getSession().persist(ledgerAccount);
		
	}

	@Override
	@Transactional
	public void updateLedgerAccount(LedgerAccount ledgerAccount, UserSession userSession) throws BaseException {
		ledgerAccount.setLastUpdatedBy(userSession.getUserId());
		ledgerAccount.setLastUpdatedDate(new Date());
		update(ledgerAccount);
		//getSession().saveOrUpdate(ledgerAccount);
	}
	
	@Override
	@Transactional
	public void deleteLedgerAccount(int ledgerAccountId, UserSession userSession) throws BaseException {
		LedgerAccount ledgerAccount = (LedgerAccount) getSession().load(LedgerAccount.class, ledgerAccountId);
		
		if(ledgerAccount != null){
			//getSession().delete(approvalDocument);
			try {
				delete(ledgerAccount);
			} catch (BaseException e) {
				throw new BaseException(e);

			} catch (ConstraintException e) {
				throw new BaseException("error.ledgerAccountDeletion");
			}
		}
		
	}
	
	@Override
	@Transactional
	public List<LedgerAccount> getAllLedgerAccountByStatus(String status) throws BaseException {		
		try {
			Criteria criteria = getSession().createCriteria(LedgerAccount.class);
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
			criteria.add(Restrictions.eq("status", status));
			List<LedgerAccount> list = (List<LedgerAccount>) criteria.list();
			for(LedgerAccount o : list) {
				o.setAccountStr(o.getAccountNo()+"-"+o.getAccountName());
			}
			return list;
		} catch (HibernateException e) {
			throw new BaseException(e);
		} catch (Exception e) {
			throw new BaseException(e);
		}
	}

}
