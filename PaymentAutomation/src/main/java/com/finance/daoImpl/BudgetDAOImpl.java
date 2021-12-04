package com.finance.daoImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.JDBCException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;

import com.finance.dao.BaseDAOSupport;
import com.finance.dao.BudgetDAO;
import com.finance.domain.ActionCode;
import com.finance.domain.ApprovalDocument;
import com.finance.domain.Budget;
import com.finance.domain.BudgetCode;
import com.finance.domain.UserSession;
import com.finance.dto.BudgetDTO;
import com.finance.enumeration.BudgetTypeEnum;
import com.finance.util.BaseException;
import com.finance.util.ConstraintException;

public class BudgetDAOImpl extends BaseDAOSupport implements BudgetDAO{

	@Override
	@Transactional
	public List<Budget> getAllBudget() throws BaseException {
		List<Budget> list = null;
		try {
			list = getSession().createCriteria(Budget.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		} catch (HibernateException e) {
			throw new BaseException(e);
		} catch (Exception e) {
			throw new BaseException(e);
		}
		return list;
	}
	
	@Override
	@Transactional
	public List<BudgetDTO> getAllBudgetDTO() throws BaseException {
		List<BudgetDTO> listDTO = new ArrayList<BudgetDTO>();
		try {
			List<Budget> list = getSession().createCriteria(Budget.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).addOrder(Order.desc("budgetId")).list();
			for(Budget budget:list) {
				BudgetDTO budgetDTO = new BudgetDTO();
				budgetDTO.setBudgetId(budget.getBudgetId());
				budgetDTO.setActionId(budget.getActionId());
				budgetDTO.setBudgetCodeId(budget.getBudgetCodeId());
				budgetDTO.setApprovalType(budget.getApprovalType());
				budgetDTO.setApprovalDocId(budget.getApprovalDocId());
				budgetDTO.setAllocatedAmount(budget.getAllocatedAmount());
				budgetDTO.setUtilizedAmount(budget.getUtilizedAmount());
				budgetDTO.setBalanceAmount(budget.getBalanceAmount());
				budgetDTO.setStatus(budget.getStatus().equals("A")?"Active":"Inactive");
				budgetDTO.setYear(budget.getYear());
				budgetDTO.setDescription(budget.getDescription());
				
				BudgetTypeEnum budgetTypeEnum = BudgetTypeEnum.getEnumByBudgetType(budget.getBudgetType());
				budgetDTO.setBudgetType(budgetTypeEnum.getDescription());
				
				ActionCode actionCode = (ActionCode) getSession().load(ActionCode.class, budget.getActionId());
				budgetDTO.setActionCode(actionCode.getDescription());
				
				BudgetCode budgetCode = (BudgetCode) getSession().load(BudgetCode.class, budget.getBudgetCodeId());
				budgetDTO.setBudgetCode(budgetCode.getDescription());
				
				ApprovalDocument approvalDocument = (ApprovalDocument) getSession().load(ApprovalDocument.class, budget.getApprovalDocId());
				if(approvalDocument!=null){
					budgetDTO.setApproveDocNo(approvalDocument.getDocName());
					budgetDTO.setApprovalRefNo(approvalDocument.getReferenceNo());
				}
				listDTO.add(budgetDTO);
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
	public List<BudgetDTO> searchAllBudgetDTO(String year) throws BaseException {
		List<BudgetDTO> listDTO = new ArrayList<BudgetDTO>();
		try {
			List<Budget> list = getSession().createCriteria(Budget.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).
					addOrder(Order.desc("budgetId")).
					add(Restrictions.eq("year", year)).list();
			for(Budget budget:list) {
				BudgetDTO budgetDTO = new BudgetDTO();
				budgetDTO.setBudgetId(budget.getBudgetId());
				budgetDTO.setActionId(budget.getActionId());
				budgetDTO.setBudgetCodeId(budget.getBudgetCodeId());
				budgetDTO.setApprovalType(budget.getApprovalType());
				budgetDTO.setApprovalDocId(budget.getApprovalDocId());
				budgetDTO.setAllocatedAmount(budget.getAllocatedAmount());
				budgetDTO.setUtilizedAmount(budget.getUtilizedAmount());
				budgetDTO.setBalanceAmount(budget.getBalanceAmount());
				budgetDTO.setStatus(budget.getStatus().equals("A")?"Active":"Inactive");
				budgetDTO.setYear(budget.getYear());
				budgetDTO.setDescription(budget.getDescription());
				
				BudgetTypeEnum budgetTypeEnum = BudgetTypeEnum.getEnumByBudgetType(budget.getBudgetType());
				budgetDTO.setBudgetType(budgetTypeEnum.getDescription());
				
				ActionCode actionCode = (ActionCode) getSession().load(ActionCode.class, budget.getActionId());
				budgetDTO.setActionCode(actionCode.getDescription());
				
				BudgetCode budgetCode = (BudgetCode) getSession().load(BudgetCode.class, budget.getBudgetCodeId());
				budgetDTO.setBudgetCode(budgetCode.getDescription());
				
				ApprovalDocument approvalDocument = (ApprovalDocument) getSession().load(ApprovalDocument.class, budget.getApprovalDocId());
				if(approvalDocument!=null){
					budgetDTO.setApproveDocNo(approvalDocument.getDocName());
					budgetDTO.setApprovalRefNo(approvalDocument.getReferenceNo());
				}
				listDTO.add(budgetDTO);
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
	public Budget getBudgetById(int budgetId) throws BaseException {
		Budget budget = (Budget) getSession().load(Budget.class, budgetId);
		return budget;
	}

	@Override
	@Transactional
	public void deleteBudget(int budgetId, UserSession userSession) throws BaseException {
		//Budget budget = (Budget) getSession().load(Budget.class, budgetId);	
		Budget budget = (Budget) load(Budget.class, budgetId);

		if(budget != null){
			try {
				delete(budget);
			
			} catch (BaseException e) {
				throw new BaseException(e);

			} catch (ConstraintException e) {
				throw new BaseException("error.budgetDeletion");
			}
		}
	}

	@Override
	@Transactional
	public void createBudget(Budget budget, UserSession userSession) throws BaseException {
		Session session = openSession();
		Transaction tx = null;
		
		try {
			tx = (Transaction) session.beginTransaction();
			
			//check approval
			ApprovalDocument approvalDocument = (ApprovalDocument) session.load(ApprovalDocument.class, budget.getApprovalDocId());
			
			budget.setLastUpdatedBy(userSession.getUserId());
			budget.setCreatedBy(userSession.getUserId());
			budget.setCreatedDate(new Date());
			budget.setLastUpdatedDate(new Date());
			budget.setStatus("A");
			session.save(budget);
			
			approvalDocument.setUtilizedAmount(approvalDocument.getUtilizedAmount()+budget.getAllocatedAmount());
			approvalDocument.setBalanceAmount(approvalDocument.getBalanceAmount()-budget.getAllocatedAmount());
			approvalDocument.setIsUsed("Y");
			session.saveOrUpdate(approvalDocument);
			
			tx.commit();
		} catch (Exception ex) {
			rollbackTransaction(tx);			
            
			if (ex instanceof ConstraintViolationException){
        		throw new BaseException("error.budgetDuplicateEntry");
			}
            if (ex instanceof JDBCException){
                throw new BaseException(((JDBCException)ex).getSQLException());
            }
			throw new BaseException(ex);
		}
		finally{
			closeSession(session);
		}
	
	}

	@Override
	@Transactional
	public void updateBudget(Budget budget, UserSession userSession) throws BaseException {

		Session session = openSession();
		Transaction tx = null;
		
		try {
			tx = (Transaction) session.beginTransaction();
			
			Budget oldBudget  = (Budget) getSession().load(Budget.class, budget.getBudgetId());
			double oldBudgetAmount = oldBudget.getAllocatedAmount();
			double newBudgetAmount = budget.getAllocatedAmount();
			double addtionalAmt = newBudgetAmount-oldBudgetAmount;
			
			ApprovalDocument approvalDocument = (ApprovalDocument) session.load(ApprovalDocument.class, budget.getApprovalDocId());
		
		

			budget.setLastUpdatedDate(new Date());
			session.update(budget);
			
			//update approval
			double newUtilized = approvalDocument.getUtilizedAmount() + addtionalAmt;
			approvalDocument.setUtilizedAmount(newUtilized);
			approvalDocument.setBalanceAmount(approvalDocument.getBalanceAmount()- addtionalAmt);
			approvalDocument.setIsUsed(newUtilized==0?"N":"Y");
			session.saveOrUpdate(approvalDocument);
			
			tx.commit();
			
		} catch (Exception ex) {
			rollbackTransaction(tx);			
            
			if (ex instanceof ConstraintViolationException){
        		throw new BaseException("error.budgetDuplicateEntry");
			}
            if (ex instanceof JDBCException){
                throw new BaseException(((JDBCException)ex).getSQLException());
            }
			throw new BaseException(ex);
		}
		finally{
			closeSession(session);
		}
	}
	
	

	@Override
	@Transactional
	public Budget getBudgetForPayRequest(int budgetCodeId, String year) throws BaseException {
		try {
			Criteria criteria = getSession().createCriteria(Budget.class);
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
			criteria.add(Restrictions.eq("budgetCodeId", budgetCodeId));
			criteria.add(Restrictions.eq("year", year));
			Budget budget = criteria.list().size()>0?(Budget) criteria.list().get(0):null;//there should be only one record
			if(budget!=null && budget.getBalanceAmount()>0)//check this in edit pr
				return budget;
			else
				return null;
		}catch (Exception e) {
			throw new BaseException(e.getMessage());
		}
	}
	
	@Override
	@Transactional
	public List<BudgetDTO> getBudgetSummaryData(String budgetType, int actionCodeId, int budgetCodeId, String year) throws BaseException {
		List<BudgetDTO> listDTO = new ArrayList<BudgetDTO>();
		try {
			Criteria criteria = getSession().createCriteria(Budget.class);
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
			criteria.add(Restrictions.eq("budgetType", budgetType));
			if(actionCodeId!=0)
				criteria.add(Restrictions.eq("actionId", actionCodeId));
			if(budgetCodeId!=0)
				criteria.add(Restrictions.eq("budgetCodeId", budgetCodeId));
		
			//criteria.add(Restrictions.eq("status", "A"));
			
			List<Budget> list = criteria.list();
			for(Budget budget:list) {
				BudgetDTO budgetDTO = new BudgetDTO();
				budgetDTO.setBudgetId(budget.getBudgetId());
				budgetDTO.setActionId(budget.getActionId());
				budgetDTO.setBudgetCodeId(budget.getBudgetCodeId());
				budgetDTO.setApprovalType(budget.getApprovalType());
				budgetDTO.setApprovalDocId(budget.getApprovalDocId());
				budgetDTO.setAllocatedAmount(budget.getAllocatedAmount());
				budgetDTO.setUtilizedAmount(budget.getUtilizedAmount());
				budgetDTO.setBalanceAmount(budget.getBalanceAmount());
				budgetDTO.setStatus(budget.getStatus());
				budgetDTO.setYear(budget.getYear());
				budgetDTO.setDescription(budget.getDescription());
				
				BudgetTypeEnum budgetTypeEnum = BudgetTypeEnum.getEnumByBudgetType(budget.getBudgetType());
				budgetDTO.setBudgetType(budgetTypeEnum.getDescription());
				
				ActionCode actionCode = (ActionCode) getSession().load(ActionCode.class, budget.getActionId());
				budgetDTO.setActionCode(actionCode.getDescription());
				
				BudgetCode budgetCode = (BudgetCode) getSession().load(BudgetCode.class, budget.getBudgetCodeId());
				budgetDTO.setBudgetCode(budgetCode.getDescription());
				
				ApprovalDocument approvalDocument = (ApprovalDocument) getSession().load(ApprovalDocument.class, budget.getApprovalDocId());
				if(approvalDocument!=null){
					budgetDTO.setApproveDocNo(approvalDocument.getDocName());
					budgetDTO.setApprovalRefNo(approvalDocument.getReferenceNo());
				}
				listDTO.add(budgetDTO);
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
	public double getBudgetUtilizeOfMonth(int year, int month, BudgetTypeEnum budgetTypeEnum) throws BaseException{
		try {

			String SQL_QUERY ="select sum(pr.netAmount) from PaymentRequest pr where  year(pr.lastUpdatedDate)="
					+ year + " and month(pr.lastUpdatedDate)="+month + " and pr.budgetType='"+budgetTypeEnum.getBudgetType()+"'";
			Query query = getSession().createQuery(SQL_QUERY);
			Double sum = (Double) query.uniqueResult();
			return sum!=null?sum.doubleValue():0.00;
		} catch (Exception e) {
			throw new BaseException(e.getMessage());
		}
	}
	
}
