package com.finance.daoImpl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.finance.dao.ActionCodeDAO;
import com.finance.dao.BaseDAOSupport;
import com.finance.domain.ActionCode;
import com.finance.domain.ApprovalDocument;
import com.finance.domain.LedgerAccount;
import com.finance.domain.UserSession;
import com.finance.util.BaseException;
import com.finance.util.ConstraintException;

public class ActionCodeDAOImpl extends BaseDAOSupport implements ActionCodeDAO{

	@Override
	@Transactional
	public List<ActionCode> getAllActionCodeByBudgetType(String budgetType) throws BaseException {
		Criteria criteria = getSession().createCriteria(ActionCode.class);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		criteria.add(Restrictions.eq("budgetType", budgetType));
		List<ActionCode> list = (List<ActionCode>) criteria.list();
		return list;		
	}

	@Override
	@Transactional
	public List<ActionCode> getAllActionCode() throws BaseException {
		List<ActionCode> list = null;
		try {
			list = getSession().createCriteria(ActionCode.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		} catch (HibernateException e) {
			throw new BaseException(e);
		} catch (Exception e) {
			throw new BaseException(e);
		}
		return list;
	}

	@Override
	@Transactional
	public ActionCode getActionCodeById(int actionId) throws BaseException {
		ActionCode actionCode = (ActionCode) getSession().load(ActionCode.class, actionId);
		return actionCode;
	}

	@Override
	@Transactional
	public void createActionCode(ActionCode actionCode,UserSession userSession) throws BaseException {
		actionCode.setLastUpdatedBy(userSession.getUserId());
		actionCode.setCreatedBy(userSession.getUserId());
		actionCode.setCreatedDate(new Date());
		actionCode.setLastUpdatedDate(new Date());
		actionCode.setStatus("A");
		save(actionCode);
	}

	@Override
	@Transactional
	public void updateActionCode(ActionCode actionCode,UserSession userSession) throws BaseException {
		update(actionCode);		
	}

	@Override
	@Transactional
	public void deleteActionCode(int actionId,UserSession userSession) throws BaseException {
		ActionCode actionCode = (ActionCode) getSession().load(ActionCode.class, actionId);
		if(actionCode != null){
			try {
				delete(actionCode);
			} catch (BaseException e) {
				throw new BaseException(e);

			} catch (ConstraintException e) {
				throw new BaseException("error.actionCodeDeletion");
			}
		}		
	}
		
}
