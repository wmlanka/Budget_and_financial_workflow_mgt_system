package com.finance.daoImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;

import com.finance.dao.BaseDAOSupport;
import com.finance.dao.BudgetCodeDAO;
import com.finance.domain.ActionCode;
import com.finance.domain.BudgetCode;
import com.finance.domain.UserSession;
import com.finance.dto.BudgetCodeDTO;
import com.finance.enumeration.BudgetTypeEnum;
import com.finance.util.BaseException;
import com.finance.util.ConstraintException;

public class BudgetCodeDAOImpl extends BaseDAOSupport implements BudgetCodeDAO{

	@Override
	@Transactional
	public List<BudgetCode> getAllBudgetCodeByActionType(int actionId) throws BaseException {
		Criteria criteria = getSession().createCriteria(BudgetCode.class);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		criteria.add(Restrictions.eq("actionId", actionId));
		List<BudgetCode> list = (List<BudgetCode>) criteria.list();
		return list;
	}

	@Override
	@Transactional
	public List<BudgetCode> getAllBudgetCode() throws BaseException {
		List<BudgetCode> list = null;
		try {
			list = getSession().createCriteria(BudgetCode.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		} catch (HibernateException e) {
			throw new BaseException(e);
		} catch (Exception e) {
			throw new BaseException(e);
		}
		return list;		
	}
	
	@Override
	@Transactional
	public List<BudgetCodeDTO> getAllBudgetCodeDTO() throws BaseException {
		List<BudgetCode> list = null;
		List<BudgetCodeDTO> listDTO = new ArrayList<BudgetCodeDTO>();
		try {
			list = getSession().createCriteria(BudgetCode.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
			for(BudgetCode budgetCode : list) {
				BudgetCodeDTO budgetCodeDTO = new BudgetCodeDTO();
				budgetCodeDTO.setBudgetCodeId(budgetCode.getBudgetCodeId());
				budgetCodeDTO.setBudgetCode(budgetCode.getBudgetCode());
				budgetCodeDTO.setDescription(budgetCode.getDescription());
				budgetCodeDTO.setStatus(budgetCode.getStatus().equals("A")?"Active":"Inactive");
				
				BudgetTypeEnum budgetTypeEnum = BudgetTypeEnum.getEnumByBudgetType(budgetCode.getBudgetType());
				budgetCodeDTO.setBudgetTypeStr(budgetTypeEnum.getDescription());
				
				ActionCode actionCode = (ActionCode) getSession().load(ActionCode.class, budgetCode.getActionId());
				budgetCodeDTO.setActionCodeDescr(actionCode.getDescription());

				listDTO.add(budgetCodeDTO);
			}
		} catch (HibernateException e) {
			throw new BaseException(e);
		} catch (Exception e) {
			throw new BaseException(e);
		}
		return listDTO;		
	}

	@Override
	@Transactional
	public BudgetCode getBudgetCodeById(int budgetCodeId) throws BaseException {
		BudgetCode budgetCode = (BudgetCode) getSession().load(BudgetCode.class, budgetCodeId);
		return budgetCode;
	}

	@Override
	@Transactional
	public void createBudgetCode(BudgetCode budgetCode, UserSession userSession) throws BaseException {
		budgetCode.setLastUpdatedBy(userSession.getUserId());
		budgetCode.setCreatedBy(userSession.getUserId());
		budgetCode.setCreatedDate(new Date());
		budgetCode.setLastUpdatedDate(new Date());
		budgetCode.setStatus("A");
		save(budgetCode);		
	}

	@Override
	@Transactional
	public void updateBudgetCode(BudgetCode budgetCode, UserSession userSession) throws BaseException {
		update(budgetCode);			
	}

	@Override
	@Transactional
	public void deleteBudgetCode(int budgetCodeId, UserSession userSession) throws BaseException {
		BudgetCode budgetCode = (BudgetCode) getSession().load(BudgetCode.class, budgetCodeId);
		if(budgetCode != null){
			try {
				delete(budgetCode);
			} catch (BaseException e) {
				throw new BaseException(e);

			} catch (ConstraintException e) {
				throw new BaseException("error.budgetCodeDeletion");
			}
		}		
	}

}
