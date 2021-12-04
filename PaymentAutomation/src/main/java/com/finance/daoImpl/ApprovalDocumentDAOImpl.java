package com.finance.daoImpl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.finance.dao.ApprovalDocumentDAO;
import com.finance.dao.BaseDAOSupport;
import com.finance.domain.ApprovalDocument;
import com.finance.domain.UserSession;
import com.finance.util.BaseException;
import com.finance.util.ConstraintException;

public class ApprovalDocumentDAOImpl extends BaseDAOSupport implements ApprovalDocumentDAO{

	@Override
	@Transactional
	public List<ApprovalDocument> getAllApprovalDocument(UserSession userSession) throws BaseException {
		List<ApprovalDocument> list = null;
		try {
			//list = getSession().createCriteria(ApprovalDocument.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
			Criteria criteria = getSession().createCriteria(ApprovalDocument.class);
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
			criteria.add(Restrictions.eq("departmentId", userSession.getDepartmentId()));
			list = (List<ApprovalDocument>) criteria.list();
		} catch (HibernateException e) {
			throw new BaseException(e);
		} catch (Exception e) {
			throw new BaseException(e);
		}
		return list;
	}

	@Override
	@Transactional
	public ApprovalDocument getApprovalDocumentById(int approvalDocId) throws BaseException {
		ApprovalDocument approvalDocument = (ApprovalDocument) getSession().load(ApprovalDocument.class, approvalDocId);
		return approvalDocument;
	}

	@Override
	@Transactional
	public void createApprovalDocument(ApprovalDocument approvalDocument, UserSession userSession) throws BaseException {
		approvalDocument.setLastUpdatedBy(userSession.getUserId());
		approvalDocument.setCreatedBy(userSession.getUserId());
		approvalDocument.setCreatedDate(new Date());
		approvalDocument.setLastUpdatedDate(new Date());
		approvalDocument.setStatus("A");
		approvalDocument.setDepartmentId(userSession.getDepartmentId());
		save(approvalDocument);
		//getSession().persist(approvalDocument);
	}

	@Override
	@Transactional
	public void updateApprovalDocument(ApprovalDocument approvalDocument, UserSession userSession) throws BaseException {
		ApprovalDocument old = (ApprovalDocument) getSession().load(ApprovalDocument.class, approvalDocument.getApprovalDocId());
//		if(old.getApprovedFullAmount()==old.getUtilizedAmount() && old.getIsPartPayment().equals("N")) {
//			throw new BaseException("errors.cannotUpdateApproval");
//		}
//		else if(old.getUtilizedAmount()>0 && !old.getIsPartPayment().equals(approvalDocument.getIsPartPayment())) {
//			throw new BaseException("errors.cannotChangePartPaymentOption");
//		}
		approvalDocument.setLastUpdatedBy(userSession.getUserId());
		approvalDocument.setLastUpdatedDate(new Date());
		update(approvalDocument);
		//getSession().saveOrUpdate(approvalDocument);
	}
	
	@Override
	@Transactional
	public void deleteApprovalDocument(int approvalDocId, UserSession userSession) throws BaseException {
		ApprovalDocument approvalDocument = (ApprovalDocument) getSession().load(ApprovalDocument.class, approvalDocId);
		
		if(approvalDocument != null){
			//getSession().delete(approvalDocument);
			try {
				delete(approvalDocument);
			} catch (BaseException e) {
				throw new BaseException(e);

			} catch (ConstraintException e) {
				throw new BaseException("error.approvalDocDeletion");
			}
		}
	}

	@Override
	@Transactional
	public List<ApprovalDocument> getApprovalDocByApprovalType(String approvalType, UserSession userSession) throws BaseException {
		Criteria criteria = getSession().createCriteria(ApprovalDocument.class);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		criteria.add(Restrictions.eq("approvalType", approvalType));
		criteria.add(Restrictions.eq("departmentId", userSession.getDepartmentId()));//*
		List<ApprovalDocument> list = (List<ApprovalDocument>) criteria.list();
		return list;
	}
	
	@Override
	@Transactional
	public List<ApprovalDocument> getAvailableApprovalDoc(String approvalType, int approvalDocId, UserSession userSession) throws BaseException {
		Criteria criteria = getSession().createCriteria(ApprovalDocument.class);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		criteria.add(Restrictions.eq("approvalType", approvalType));
		//criteria.add(Restrictions.gt("balanceAmount", 0.00));
		criteria.add(Restrictions.eq("departmentId", userSession.getDepartmentId()));
		//criteria.add(Restrictions.or(Restrictions.gt("balanceAmount", 0.00), Restrictions.eq("approvalDocId", approvalDocId)));
		if(approvalDocId>0)
			criteria.add(Restrictions.eq("approvalDocId", approvalDocId));
		else {
			criteria.add(Restrictions.gt("balanceAmount", 0.00));
			criteria.add(Restrictions.eq("status", "A"));//new
		}
		List<ApprovalDocument> list = (List<ApprovalDocument>) criteria.list();
		return list;
	}
	
	@Override
	@Transactional
	public ApprovalDocument updateApprovalDocumentBalance(int approvalDocId, double usedAmount, boolean isRelease, UserSession userSession, Session session) throws BaseException {
		ApprovalDocument approvalDocument = (ApprovalDocument) getSession().load(ApprovalDocument.class, approvalDocId);
		if(isRelease) {
			double newUtilized = approvalDocument.getUtilizedAmount()-usedAmount;
			approvalDocument.setUtilizedAmount(newUtilized);
			approvalDocument.setBalanceAmount(approvalDocument.getBalanceAmount()+usedAmount);
			approvalDocument.setIsUsed(newUtilized==0?"N":"Y");
		}else {
			approvalDocument.setUtilizedAmount(approvalDocument.getUtilizedAmount()+usedAmount);
			approvalDocument.setBalanceAmount(approvalDocument.getBalanceAmount()-usedAmount);
			approvalDocument.setIsUsed("Y");
		}
		approvalDocument.setLastUpdatedBy(userSession.getUserId());
		if(session==null)
			update(approvalDocument);
//		else
//			session.update(approvalDocument);//commit will happend in parent method
		return approvalDocument;
			
	}
}
