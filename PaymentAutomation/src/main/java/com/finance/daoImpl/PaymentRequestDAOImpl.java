package com.finance.daoImpl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.JDBCException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;

import com.finance.dao.BaseDAOSupport;
import com.finance.dao.BudgetDAO;
import com.finance.dao.PaymentRequestDAO;
import com.finance.domain.ApprovalWorkflow;
import com.finance.domain.Budget;
import com.finance.domain.BudgetCode;
import com.finance.domain.LedgerAccount;
import com.finance.domain.Location;
import com.finance.domain.PREntries;
import com.finance.domain.PRStakeholderSourceDoc;
import com.finance.domain.PRStatus;
import com.finance.domain.PaymentRequest;
import com.finance.domain.Serial;
import com.finance.domain.SourceDocument;
import com.finance.domain.Stakeholder;
import com.finance.domain.TempPREntries;
import com.finance.domain.UserSession;
import com.finance.dto.PREntriesDTO;
import com.finance.dto.PaymentRequestDTO;
import com.finance.dto.SourceDocumentDTO;
import com.finance.enumeration.AccessLevelEnum;
import com.finance.enumeration.ApprovalFlowEnum;
import com.finance.enumeration.BudgetTypeEnum;
import com.finance.enumeration.SourceDocTypeEnum;
import com.finance.util.BaseException;
import com.finance.util.ConstraintException;

public class PaymentRequestDAOImpl extends BaseDAOSupport implements PaymentRequestDAO{
	BudgetDAO budgetDAO;

	public BudgetDAO getBudgetDAO() {
		return budgetDAO;
	}
	public void setBudgetDAO(BudgetDAO budgetDAO) {
		this.budgetDAO = budgetDAO;
	}

	DecimalFormat decimalFormat = new DecimalFormat("000000");
	DecimalFormat decimalFormat2 = new DecimalFormat("000");
	Format formatter = new SimpleDateFormat("yy-MM-dd");
	SimpleDateFormat mySqlformatter = new SimpleDateFormat("yyyy-MM-dd");
	
	@Override
	@Transactional
	public Location getLocationById(int locationId) throws BaseException {
		Location location = (Location) getSession().load(Location.class, locationId);
		return location;		
	}
	
	@Override
	@Transactional
	public PaymentRequest getPayDeductionSummary(String[] sourceDocIds) throws BaseException {
		double payableAmount=0.0;
		double vatAmount=0.0;
		double nbtAmount=0.0;
		double payableOtherAmount=0.0;
		double payableTotal=0.0;
		
		double withholdingTax=0.0;
		double retainedAmount=0.0;
		double deductionOther=0.0;	
		double penaltyAmount=0.0;
		double deductionTotal=0.0;	
		
		double netAmount=0.0;
		
		if(sourceDocIds!=null) {
			for(String id : sourceDocIds) {
				SourceDocument sourceDocument = (SourceDocument) load(SourceDocument.class, Integer.parseInt(id));
				payableAmount = payableAmount+sourceDocument.getNetAmount();
				vatAmount = vatAmount+sourceDocument.getVatAmount();
				payableOtherAmount = payableOtherAmount+sourceDocument.getOtherTaxAmount();
				payableTotal = payableTotal + sourceDocument.getNetAmount() + sourceDocument.getVatAmount() + sourceDocument.getOtherTaxAmount();
			}
		}
		if(payableTotal==0)
			return null;
		PaymentRequest paymentRequest = new PaymentRequest();
		paymentRequest.setPayableAmount(payableAmount);
		paymentRequest.setVatAmount(vatAmount);
		paymentRequest.setPayableOtherAmount(payableOtherAmount);
		paymentRequest.setPayableTotal(payableTotal);
		return paymentRequest;
	}
	
	@Override
	@Transactional
	public List<TempPREntries> getTempPREntries(String userId) throws BaseException {
		Criteria criteria = getSession().createCriteria(TempPREntries.class);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		criteria.add(Restrictions.eq("userId", userId));
		List<TempPREntries> list = (List<TempPREntries>) criteria.list();
		return list;
	}
	
	@Override
	@Transactional
	public void createTempPREntry(TempPREntries tempPREntries, UserSession userSession) throws BaseException {
		try {
			
			LedgerAccount ledgerAccount = null;
			if(tempPREntries.getLedgerAccountId()>0)
				ledgerAccount = (LedgerAccount) load(LedgerAccount.class, tempPREntries.getLedgerAccountId());
			Stakeholder stakeholder =  (Stakeholder) load(Stakeholder.class, tempPREntries.getStakeholderId());
			
			tempPREntries.setLedgerAccountNo(ledgerAccount!=null?ledgerAccount.getAccountNo()+"-"+ledgerAccount.getAccountName():null);
			tempPREntries.setUserId(userSession.getUserId());
			tempPREntries.setStakeholderName(stakeholder.getFullName());
			save(tempPREntries);
		}catch(Exception e) {
			throw new BaseException(e.getMessage());
		}
	}
	
	@Override
	@Transactional
	public void deleteTempPREntries(String userId) throws BaseException {
		Criteria criteria = getSession().createCriteria(TempPREntries.class);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		criteria.add(Restrictions.eq("userId", userId));
		List list = (List<TempPREntries>) criteria.list();
		try {
			deleteAll(list);
		} catch (BaseException e) {
			throw new BaseException(e);
		} catch (ConstraintException e) {
			throw new BaseException(e);
		}
	}
	
	@Override
	@Transactional
	public PaymentRequest createPaymentRequest(PaymentRequest paymentRequest, int stakeholderId, List<Integer> selectedSourceDocs,UserSession userSession) throws BaseException {
		Session session = openSession();
		Transaction tx = null;
		
		try {
			tx = (Transaction) session.beginTransaction();
			Budget bud = getBudgetDAO().getBudgetForPayRequest(paymentRequest.getBudgetCodeId(),paymentRequest.getBudgetYear());
 
			Budget budget = updateBudget(bud.getBudgetId(), paymentRequest.getNetAmount(), userSession, session);
			session.update(budget);//check and update budget
			
			paymentRequest.setBudgetId(bud.getBudgetId());
			paymentRequest.setDepartmentId(userSession.getDepartmentId());
			paymentRequest.setCostCenter(userSession.getDepartmentId()+"");
			paymentRequest.setPrDate(new Date());
			paymentRequest.setPrfStatus("DRAFT");
			paymentRequest.setCreatedBy(userSession.getUserId());
			paymentRequest.setCreatedDate(new Date());
			paymentRequest.setLastUpdatedBy(userSession.getUserId());
			paymentRequest.setLastUpdatedDate(new Date());
			paymentRequest.setPrNumber(generatePRNumber(userSession));
			paymentRequest.setStakeholderId(stakeholderId);
			session.save(paymentRequest);
			
			List<TempPREntries> tempEntries = getTempPREntries(userSession.getUserId());
			for(TempPREntries tempPREntries : tempEntries) {
				PREntries prEntries = new PREntries();
				prEntries.setPrId(paymentRequest.getPaymentRequestId());
				prEntries.setStakeholderId(tempPREntries.getStakeholderId());
				prEntries.setDrCr(tempPREntries.getDrCr());
				prEntries.setLedgerAccountId(tempPREntries.getLedgerAccountId()<=0?null:tempPREntries.getLedgerAccountId());
				prEntries.setStakeholderId(tempPREntries.getStakeholderId());
				prEntries.setAmount(tempPREntries.getAmount());
				prEntries.setCreatedBy(userSession.getUserId());
				prEntries.setCreatedDate(new Date());
				prEntries.setDepartmentId(userSession.getDepartmentId());
				session.save(prEntries);
				//session.delete(tempPREntries);
			}
			
			for(Integer id : selectedSourceDocs) {
				PRStakeholderSourceDoc prStakeholderSourceDoc = new PRStakeholderSourceDoc();
				prStakeholderSourceDoc.setPrId(paymentRequest.getPaymentRequestId());
				prStakeholderSourceDoc.setStakeholderId(stakeholderId);
				prStakeholderSourceDoc.setSourceDocId(id);
				prStakeholderSourceDoc.setCreatedBy(userSession.getUserId());
				prStakeholderSourceDoc.setCreatedDate(new Date());
				prStakeholderSourceDoc.setDepartmentId(userSession.getDepartmentId());
				session.save(prStakeholderSourceDoc);
				
				//update Source Doc
				SourceDocument sourceDocument = (SourceDocument) load(SourceDocument.class, id);
				sourceDocument.setIsPRUsed("Y");
				sourceDocument.setLastUpdatedBy(userSession.getUserId());
				sourceDocument.setLastUpdatedDate(new Date());
				session.update(sourceDocument);
			}
			
			PRStatus prStatus = createPRStatus(paymentRequest.getPaymentRequestId(), null, "DRAFT", userSession);
			session.save(prStatus);

			tx.commit();
		} catch (Exception ex) {
			rollbackTransaction(tx);
            
			if (ex instanceof ConstraintViolationException){
        		throw new BaseException("errors.uniqueConstraintError");
			}
            if (ex instanceof JDBCException){
                throw new BaseException(((JDBCException)ex).getSQLException());
            }
			throw new BaseException(ex);
		}
		finally{
			closeSession(session);
		}
		return paymentRequest;
	}
	
	@Override
	@Transactional
	public PaymentRequest updatePaymentRequest(PaymentRequest paymentRequest, int stakeholderId, List<Integer> selectedSourceDocs,UserSession userSession) throws BaseException {
		Session session = openSession();
		Transaction tx = null;
		
		try {
			tx = (Transaction) session.beginTransaction();
			
			PaymentRequest oldRequest = (PaymentRequest) load(PaymentRequest.class, paymentRequest.getPaymentRequestId());
			int oldBudgetId = oldRequest.getBudgetId();
			double oldNetAmount = oldRequest.getNetAmount();
			Budget bud = getBudgetDAO().getBudgetForPayRequest(paymentRequest.getBudgetCodeId(),paymentRequest.getBudgetYear());
 
			//reverse budget
			Budget oldBudget = reverseBudget(oldBudgetId, oldNetAmount, userSession, session);
			session.update(oldBudget);
			
			//update budget
			Budget budget = updateBudget(bud.getBudgetId(), paymentRequest.getNetAmount(), userSession, session);
			session.update(budget);
			
			paymentRequest.setBudgetId(bud.getBudgetId());
			paymentRequest.setDepartmentId(userSession.getDepartmentId());
			paymentRequest.setCostCenter(userSession.getDepartmentId()+"");
			paymentRequest.setPrfStatus("DRAFT");
			paymentRequest.setLastUpdatedBy(userSession.getUserId());
			paymentRequest.setLastUpdatedDate(new Date());
			paymentRequest.setStakeholderId(stakeholderId);
			session.update(paymentRequest);
			
			List<TempPREntries> tempEntries = getTempPREntries(userSession.getUserId());
			
			//delete old entries
			List<PREntries> oldEntries = getPREntries(paymentRequest.getPaymentRequestId());
			for(PREntries o : oldEntries) {
				session.delete(o);
			}
			
			//save new entries
			for(TempPREntries tempPREntries : tempEntries) {
				PREntries prEntries = new PREntries();
				prEntries.setPrId(paymentRequest.getPaymentRequestId());
				prEntries.setStakeholderId(tempPREntries.getStakeholderId());
				prEntries.setDrCr(tempPREntries.getDrCr());
				prEntries.setLedgerAccountId(tempPREntries.getLedgerAccountId()<=0?null:tempPREntries.getLedgerAccountId());//check
				prEntries.setStkAccountNo(tempPREntries.getStkAccountNo());
				prEntries.setAmount(tempPREntries.getAmount());
				prEntries.setCreatedBy(userSession.getUserId());
				prEntries.setCreatedDate(new Date());
				prEntries.setDepartmentId(userSession.getDepartmentId());
				session.save(prEntries);
				//session.delete(tempPREntries);
			}
			
			List<PRStakeholderSourceDoc> oldPrStkSrcDoc = getPRStakeholderSourceDoc(paymentRequest.getPaymentRequestId());
			for(PRStakeholderSourceDoc o : oldPrStkSrcDoc) {
				session.delete(o);
				
				//update old Source Doc
				SourceDocument sourceDocument = (SourceDocument) session.load(SourceDocument.class, o.getSourceDocId());
				sourceDocument.setIsPRUsed("N");
				session.update(sourceDocument);
			}
			
			//save new source docs
			for(Integer id : selectedSourceDocs) {
				PRStakeholderSourceDoc prStakeholderSourceDoc = new PRStakeholderSourceDoc();
				prStakeholderSourceDoc.setPrId(paymentRequest.getPaymentRequestId());
				prStakeholderSourceDoc.setStakeholderId(stakeholderId);
				prStakeholderSourceDoc.setSourceDocId(id);
				prStakeholderSourceDoc.setCreatedBy(userSession.getUserId());
				prStakeholderSourceDoc.setCreatedDate(new Date());
				prStakeholderSourceDoc.setDepartmentId(userSession.getDepartmentId());
				session.save(prStakeholderSourceDoc);
				
				//update Source Doc
				SourceDocument sourceDocument = (SourceDocument) session.load(SourceDocument.class, id);
				sourceDocument.setIsPRUsed("Y");
				session.update(sourceDocument);
			}
			
			PRStatus prStatus = createPRStatus(paymentRequest.getPaymentRequestId(), null, "DRAFT", userSession);
			session.save(prStatus);
			
	
			tx.commit();
			
		} catch (Exception ex) {
			rollbackTransaction(tx);	
			
			if (ex instanceof ConstraintViolationException){
        		throw new BaseException("errors.uniqueConstraintError");
			}
            if (ex instanceof JDBCException){
                throw new BaseException(((JDBCException)ex).getSQLException());
            }
			throw new BaseException(ex);
		}
		finally{
			closeSession(session);
		}
		return paymentRequest;
	}
	
	public List<PRStakeholderSourceDoc> getPRStakeholderSourceDoc(int prId) throws BaseException {
		Criteria criteria = getSession().createCriteria(PRStakeholderSourceDoc.class);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		criteria.add(Restrictions.eq("prId", prId));
		List<PRStakeholderSourceDoc> list = (List<PRStakeholderSourceDoc>) criteria.list();
		return list;
	}
	
	public PRStatus createPRStatus(int prId, String rejectReason, String status, UserSession userSession) {
		PRStatus prStatus = new PRStatus();
		prStatus.setPrId(prId);
		prStatus.setStatus(status);
		prStatus.setRejectReason(rejectReason);
		prStatus.setDepartmentId(userSession.getDepartmentId());
		prStatus.setUserLevel(userSession.getUserType());
		prStatus.setCreatedBy(userSession.getUserId());
		prStatus.setCreatedDate(new Date());
		return prStatus;
		
	}
	
	public Budget updateBudget(int budgetId, double amount, UserSession userSession, Session session) throws BaseException {
		Budget budget = (Budget) session.load(Budget.class, budgetId);
		double newUtilized = budget.getUtilizedAmount()+amount;
		double newBalance = budget.getBalanceAmount() - amount;
		
		if(newBalance<0) {
			throw new BaseException("errors.budgetnotenough");
		}
		budget.setUtilizedAmount(newUtilized);
		budget.setBalanceAmount(newBalance);
		return budget;
	}
	
	public Budget reverseBudget(int budgetId, double amount, UserSession userSession, Session session) throws BaseException {
		Budget budget = (Budget) session.load(Budget.class, budgetId);
		double newUtilized = budget.getUtilizedAmount()-amount;
		double newBalance = budget.getBalanceAmount() + amount;
		budget.setUtilizedAmount(newUtilized);
		budget.setBalanceAmount(newBalance);
		budget.setLastUpdatedDate(new Date());
		return budget;
	}
	
	@Override
	@Transactional
	public void deleteTempPREntryById(int tempPREntryId) throws BaseException {
		try {
			TempPREntries temp = (TempPREntries) load(TempPREntries.class, tempPREntryId);
			delete(temp);
		} catch (BaseException e) {
			throw new BaseException(e);
		} catch (ConstraintException e) {
			throw new BaseException(e);
		}		
	}
	
	public String generatePRNumber(UserSession userSession) throws BaseException {
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		Criteria criteria = getSession().createCriteria(Serial.class);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		criteria.add(Restrictions.eq("departmentId", userSession.getDepartmentId()));
		criteria.add(Restrictions.eq("financialYear", currentYear));
		Serial serial =  criteria.list().size()>0?((Serial) criteria.list().get(0)):null;
		if(serial==null) {
			throw new BaseException("errors.serialnotfound"); 
		}
		
		String prNo = currentYear+"-"+decimalFormat2.format(userSession.getDepartmentId())+"-"+
						decimalFormat.format(serial.getSerialNo()+1);

		serial.setSerialNo(serial.getSerialNo()+1);
		update(serial);
		return prNo;
	}
	
	@Override
	@Transactional
	public double[] checkDrCRofTempPREntries(String userId) throws BaseException {
		Criteria criteria = getSession().createCriteria(TempPREntries.class);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		criteria.add(Restrictions.eq("userId", userId));
		List<TempPREntries> list = (List<TempPREntries>) criteria.list();
		double drTot = 0.00;
		double crTot = 0.00;
		for(TempPREntries o :list) {
			if(o.getDrCr().equals("DR"))
				drTot = drTot + o.getAmount();
			else if(o.getDrCr().equals("CR"))
				crTot = crTot + o.getAmount();
		}
		double entries[] = {drTot,crTot};
		return entries;
		
//		if(drTot!=crTot) {
//			throw new BaseException("error.drcrnottally");
//		}
	}
		
	@Override
	@Transactional
	public List<PaymentRequestDTO> searchPR(int stakeholderId, Integer seqNo, Date fromDate, Date toDate, String status, String userType, int departmentId, UserSession userSession) throws BaseException{
		try {
			Criteria criteria = getSession().createCriteria(PaymentRequest.class);
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
			
			if(isDepartmentFilterReq(userSession)) {
				criteria.add(Restrictions.eq("departmentId", userSession.getDepartmentId()));
			}
			if(seqNo!=null && seqNo>0) {
				String prNo = decimalFormat.format(seqNo);
				criteria.add(Restrictions.like("prNumber", prNo,MatchMode.END));
			}
			if(fromDate!=null)
				criteria.add(Restrictions.ge("prDate", fromDate));
			if(toDate!=null)
				criteria.add(Restrictions.le("prDate", toDate));
			if(stakeholderId>0)
				criteria.add(Restrictions.eq("stakeholderId", stakeholderId));
			if(!status.equals("-1"))
				criteria.add(Restrictions.eq("prfStatus", status));
			
			List<PaymentRequest> list = criteria.list();
			List<PaymentRequestDTO> dtoList = new ArrayList<PaymentRequestDTO>();
			
			for(PaymentRequest o : list) {
				ApprovalWorkflow approvalWorkflow = getCurrentApprovalByPRId(o.getPaymentRequestId(), userSession);
				
				if(!userType.equals("-1") || departmentId>0) {
					if(approvalWorkflow==null)
						continue;//skip pay request
					else {
						if(!userType.equals("-1")) {
							if(approvalWorkflow!=null && !approvalWorkflow.getUserType().equals(userType)) {
								continue;
							}
						}
						if(departmentId>0 && approvalWorkflow.getDepartmentId()!= departmentId) {
							continue;
						}
					}
				}
			
				
				PaymentRequestDTO dto = new PaymentRequestDTO();
				dto.setPaymentRequestId(o.getPaymentRequestId());
				dto.setPrfStatus(o.getPrfStatus());
				dto.setPrDate(o.getPrDate());
				dto.setPrDateStr(formatter.format(o.getPrDate()));
				dto.setSubject(o.getSubject());
				
				Stakeholder st =  (Stakeholder) load(Stakeholder.class, o.getStakeholderId());
				dto.setStakeholderName(st.getFullName());
				dto.setBudgetYear(o.getBudgetYear());
				dto.setPrNumber(o.getPrNumber());
				dto.setNetAmount(o.getNetAmount());
				
				dto.setBudgetYear(o.getBudgetYear());
				
				BudgetCode budgetCode = (BudgetCode) load(BudgetCode.class, o.getBudgetCodeId());
				dto.setBudgetCode(budgetCode.getDescription());
				
				if(approvalWorkflow!=null)
					dto.setNextApproval(approvalWorkflow.getDepartmentId()+"-"+AccessLevelEnum.getEnumByCode(approvalWorkflow.getUserType()).getDescription());				
				dtoList.add(dto);
		
			}
			return dtoList;
		}catch (BaseException e) {
			throw new BaseException(e);
		} catch (Exception e) {
			throw new BaseException(e);
		}
	}
	
	public boolean isDepartmentFilterReq(UserSession userSession) {
		if(userSession.getUserType().equals(AccessLevelEnum.SUPER_USER.getCode()) || userSession.getUserType().equals(AccessLevelEnum.SYSTEM_ADMIN.getCode())) {
			return false;
		}
		if(userSession.getDepartmentId()==600 && (userSession.getUserType().equals(AccessLevelEnum.OFFICER_A.getCode()) || userSession.getUserType().equals(AccessLevelEnum.OFFICER_B.getCode()))) {//finance
			return false;
		}
		return true;
	}
	
	@Override
	@Transactional
	public PaymentRequest getPaymentRequestById(int id) throws BaseException {
		PaymentRequest paymentRequest = (PaymentRequest) getSession().load(PaymentRequest.class, id);
		return paymentRequest;
	}
	
	public List<PREntries> getPREntries(int prId) throws BaseException {
		Criteria criteria = getSession().createCriteria(PREntries.class);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		criteria.add(Restrictions.eq("prId", prId));
		List<PREntries> list = (List<PREntries>) criteria.list();
		return list;
	}
	
	@Override
	@Transactional
	public PaymentRequest getPaymentRequestAllDataById(int paymentRequestId, UserSession userSession) throws BaseException {
		try {
			PaymentRequest paymentRequest = (PaymentRequest) getSession().load(PaymentRequest.class, paymentRequestId);
			
			if(paymentRequest.getPrfStatus().equals("REJECTED")) {
				paymentRequest.setRejectReason(getRejectCancelReason(paymentRequestId));
			}if(paymentRequest.getPrfStatus().equals("CANCELED")) {
				paymentRequest.setCancelReason(getRejectCancelReason(paymentRequestId));
			}
			
			//get entries
			Criteria criteria = getSession().createCriteria(PREntries.class);
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
			criteria.add(Restrictions.eq("prId", paymentRequestId));
			List<PREntries> list = (List<PREntries>) criteria.list();
	
			//save to temp approvals
			List tempList = new ArrayList<TempPREntries>();
			for(PREntries e :list) {
				TempPREntries temp = new TempPREntries();
				temp.setPrId(e.getPrId());
				temp.setStakeholderId(e.getStakeholderId());
				temp.setDrCr(e.getDrCr());
				temp.setLedgerAccountId(e.getLedgerAccountId()==null?0:e.getLedgerAccountId());
				temp.setStkAccountNo(e.getStkAccountNo());
				temp.setAmount(e.getAmount());
				temp.setUserId(userSession.getUserId());
				
				if(e.getLedgerAccountId()!=null && e.getLedgerAccountId()>0) {
					LedgerAccount ledgerAccount = (LedgerAccount) load(LedgerAccount.class, e.getLedgerAccountId());
					temp.setLedgerAccountNo(ledgerAccount.getAccountNo()+"-"+ledgerAccount.getAccountName());
				}
				Stakeholder stakeholder =  (Stakeholder) load(Stakeholder.class, e.getStakeholderId()); 
				temp.setStakeholderName(stakeholder.getFullName());			
				tempList.add(temp);
			}
			saveAll(tempList);
	
			return paymentRequest;
			
		}catch (BaseException e) {
			throw new BaseException(e);
		} catch (Exception e) {
			throw new BaseException(e);
		}
	}
	
	private String getRejectCancelReason(int prId){
		try {
			String SQL_QUERY =" from PRStatus as ps where ps.prId="+ prId 
					+" and ps.prStatusId=(select max(ps2.prStatusId) from PRStatus ps2 where ps2.prId=ps.prId)";
			Query query = getSession().createQuery(SQL_QUERY);
			PRStatus o = (PRStatus) query.uniqueResult();
			if(o!=null)
				return o.getRejectReason();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	@Override
	@Transactional
	public List<SourceDocumentDTO> getSourceDocForPR(int stakeholderId, int prId, UserSession userSession, String status) throws BaseException {
		try {
			List<Integer> sourceIds = new ArrayList<Integer>();
			List<SourceDocument> list = new ArrayList<SourceDocument>();
			List<SourceDocumentDTO> listDTO = new ArrayList<SourceDocumentDTO>();

			if(prId>0) {
				Criteria criteria = getSession().createCriteria(PRStakeholderSourceDoc.class);
				criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
				criteria.add(Restrictions.eq("prId", prId));
				criteria.add(Restrictions.eq("stakeholderId", stakeholderId));
				criteria.setProjection(Projections.property("sourceDocId"));
				sourceIds = criteria.list();
			}
			if(status==null || status.equals(""))
				status = "DRAFT";//payment creation stage
			
			boolean allLoad = false;
			if((status.equals("DRAFT") || status.equals("REJECTED")) && 
					(userSession.getUserType().equals("SYSTEM_ADMIN")|| userSession.getUserType().equals("SYSTEM_OPERATOR"))) {
				allLoad = true;
			}
			
			//get invoices
			Criteria criteria = getSession().createCriteria(SourceDocument.class);
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
			criteria.add(Restrictions.eq("stakeholderId", stakeholderId));
			criteria.add(Restrictions.eq("status", "A"));
			if(allLoad && userSession!=null)
				criteria.add(Restrictions.eq("departmentId", userSession.getDepartmentId()));
			
			if(allLoad && (sourceIds==null || sourceIds.size()==0)) {
				criteria.add(Restrictions.eq("isPRUsed", "N"));
				
			}else if(allLoad) {
//				criteria.add(Restrictions.eq("isPRUsed", "N"));
//				criteria.add(Restrictions.in("sourceDocumentId", sourceIds));
				criteria.add(Restrictions.or(Restrictions.eq("isPRUsed", "N"),Restrictions.in("sourceDocumentId", sourceIds)));

			}
			else if(sourceIds!=null && sourceIds.size()>0)
				criteria.add(Restrictions.in("sourceDocumentId", sourceIds));
			else
				return listDTO;// no data
				
			list = criteria.list();
			
			Format formatter = new SimpleDateFormat("yyyy-MM-dd");
			for(SourceDocument sourceDocument : list) {
				SourceDocumentDTO sourceDocumentDTO = new SourceDocumentDTO();
				sourceDocumentDTO.setSourceDocumentId(sourceDocument.getSourceDocumentId());
				
				SourceDocTypeEnum sourceDocTypeEnum = SourceDocTypeEnum.getEnumBySourceDocType(sourceDocument.getDocumentType());
				sourceDocumentDTO.setDocumentType(sourceDocTypeEnum.getDescription());
				
				sourceDocumentDTO.setReferenceNo(sourceDocument.getReferenceNo());
				sourceDocumentDTO.setDocumentDateStr(formatter.format(sourceDocument.getDocumentDate()));
				sourceDocumentDTO.setNetAmount(new BigDecimal(sourceDocument.getNetAmount()));
				sourceDocumentDTO.setGrossAmount(new BigDecimal(sourceDocument.getGrossAmount()));
				sourceDocumentDTO.setVatAmount(new BigDecimal(sourceDocument.getVatAmount()));
				Stakeholder stakeholder = (Stakeholder) load(Stakeholder.class, sourceDocument.getStakeholderId());
				sourceDocumentDTO.setStakeholderName(stakeholder.getFullName());
				
				if(sourceIds.contains(sourceDocument.getSourceDocumentId())) {
					sourceDocumentDTO.setSelect("checked");
				}
				//if(sourceDocument.getSourceDocumentId()==53)
					//sourceDocumentDTO.setSelect("checked"); // testing
				listDTO.add(sourceDocumentDTO);
			}
			return listDTO;

		}catch (Exception e) {
			throw new BaseException(e);
		}
		
	}
	
	public List<ApprovalWorkflow> createApprovalWorkflow(PaymentRequest paymentRequest, UserSession userSession) {
		List<Map<AccessLevelEnum, Integer>> list = new ArrayList<Map<AccessLevelEnum, Integer>>();
		Map<AccessLevelEnum, Integer> map = new HashMap<AccessLevelEnum,Integer>();
		
		if(userSession.getDepartmentId()!=600) {
			map = new HashMap<AccessLevelEnum,Integer>();
			map.put(ApprovalFlowEnum.BASIC_DEPARTMENT.getLevel1(), paymentRequest.getDepartmentId());
			list.add(map);
			
			map = new HashMap<AccessLevelEnum,Integer>();
			map.put(ApprovalFlowEnum.BASIC_DEPARTMENT.getLevel2(), paymentRequest.getDepartmentId());
			list.add(map);
		}

			map = new HashMap<AccessLevelEnum,Integer>();
			map.put(ApprovalFlowEnum.BASIC_FINANCE.getLevel1(), 600);
			list.add(map);
			
			map = new HashMap<AccessLevelEnum,Integer>();
			map.put(ApprovalFlowEnum.BASIC_FINANCE.getLevel2(), 600);
			list.add(map);
	
		int sequence=0;
		List<ApprovalWorkflow> approvalList = new ArrayList<ApprovalWorkflow>();
		for (Map<AccessLevelEnum,Integer> m : list) {
			for (Map.Entry<AccessLevelEnum,Integer> entry : m.entrySet()) {
				ApprovalWorkflow approvalWorkflow = new ApprovalWorkflow();
				approvalWorkflow.setPrId(paymentRequest.getPaymentRequestId());
				approvalWorkflow.setSequenceNo(++sequence);
				approvalWorkflow.setUserType(entry.getKey().getCode());
				approvalWorkflow.setDepartmentId(entry.getValue());//department id
				approvalWorkflow.setIsComplete("N");
				approvalWorkflow.setIsCancelled("N");
				approvalWorkflow.setIsDiscard("N");
				approvalWorkflow.setCreatedDate(new Date());
				approvalWorkflow.setCreatedUser(userSession.getUserId());
				approvalWorkflow.setUpdatedDate(null);
				approvalWorkflow.setUpdatedUser(null);
				approvalList.add(approvalWorkflow);
			}
		}
		return approvalList;
	}
	
	@Override
	@Transactional
	public ApprovalWorkflow getCurrentApprovalByPRId(int prId, UserSession userSession) throws BaseException {
		Criteria criteria = getSession().createCriteria(ApprovalWorkflow.class);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		criteria.add(Restrictions.eq("prId", prId));
		criteria.add(Restrictions.eq("isComplete", "N"));
		criteria.add(Restrictions.eq("isCancelled", "N")); 
		criteria.add(Restrictions.eq("isDiscard", "N"));
		criteria.addOrder(Order.asc("sequenceNo"));
		//criteria.add(Restrictions.eq("isComplete", 'N'));
		ApprovalWorkflow o = criteria.list().size()>0? ((ApprovalWorkflow)criteria.list().get(0)):null;
		return o;
	}
	
	@Override
	@Transactional
	public List<PaymentRequestDTO> getPaymentSummaryData(int seqNo,String year, int costCenter, String status, int stakehlderId,
			double minimumAmount, Date fromDate, Date toDate, int invoiceId, UserSession userSession) throws BaseException {
		try {
			Criteria criteria = getSession().createCriteria(PaymentRequest.class);
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
			
			
			if(costCenter>0) {
				criteria.add(Restrictions.eq("departmentId", costCenter));
			}
			if(costCenter<=0) {
				if(isDepartmentFilterReq(userSession)) {
					criteria.add(Restrictions.eq("departmentId", userSession.getDepartmentId()));
				}
			}
			if(seqNo>0) {
				String prNo = decimalFormat.format(seqNo);
			}
			if(year!=null && !year.equals("")) {
				criteria.add(Restrictions.like("prNumber", year+"",MatchMode.START));
			}
			if(status!=null && !status.equals("") && !status.equals("-1")) {
				criteria.add(Restrictions.eq("prfStatus", status));
			}
			if(stakehlderId>0) {
				criteria.add(Restrictions.eq("stakeholderId", stakehlderId));
			}
			if(minimumAmount>0) {
				criteria.add(Restrictions.ge("netAmount", minimumAmount));
			}
			if(fromDate!=null)
				criteria.add(Restrictions.ge("prDate", fromDate));
			
			if(toDate!=null)
				criteria.add(Restrictions.le("prDate", toDate));
			
			List<PaymentRequest> list = criteria.list();
			List<PaymentRequestDTO> dtoList = new ArrayList<PaymentRequestDTO>();
			
			for(PaymentRequest o : list) {
				

				PaymentRequestDTO dto = new PaymentRequestDTO();
				dto.setPaymentRequestId(o.getPaymentRequestId());
				dto.setPrfStatus(o.getPrfStatus());
				dto.setPrDate(o.getPrDate());
				dto.setPrDateStr(formatter.format(o.getPrDate()));
				dto.setSubject(o.getSubject());
				
				String payMode = o.getPayMode();
				
				dto.setPayMode(payMode);
				
				Stakeholder st =  (Stakeholder) load(Stakeholder.class, o.getStakeholderId());
				dto.setStakeholderName(st.getFullName());
				dto.setBudgetYear(o.getBudgetYear());
				dto.setPrNumber(o.getPrNumber());
				dto.setNetAmount(o.getNetAmount());
				
				dto.setBudgetYear(o.getBudgetYear());
				
				BudgetCode budgetCode = (BudgetCode) load(BudgetCode.class, o.getBudgetCodeId());
				dto.setBudgetCode(budgetCode.getDescription());
				dto.setBudgetType(BudgetTypeEnum.getEnumByBudgetType(o.getBudgetType()).getDescription());
				dto.setDepartmentId(o.getDepartmentId());
				dtoList.add(dto);
		
			}		
			return dtoList;
		
			
		}catch (BaseException e) {
			throw new BaseException(e);
		} catch (Exception e) {
			throw new BaseException(e);
		}
		
	}
	
	@Override
	@Transactional
	public int getPendingApprovalsforUser(UserSession userSession) throws BaseException{//Notification
		try {
			String SQL_QUERY =" from ApprovalWorkflow as a where a.isComplete='N' and a.isCancelled='N' and a.isDiscard='N'"+
					" and a.sequenceNo = "
					+ "(select min(a2.sequenceNo) from ApprovalWorkflow a2 where a.prId=a2.prId and a2.isComplete='N' and a2.isCancelled='N' and a2.isDiscard='N')" +
					" and a.userType='"+userSession.getUserType()+"' and a.departmentId="+userSession.getDepartmentId();//sgroup by prId";
			
			Query query = getSession().createQuery(SQL_QUERY);
			List<ApprovalWorkflow> list = query.list();
			return list.size();
		} catch (Exception e) {
			throw new BaseException(e.getMessage());
		}	
	}
	
	@Override
	@Transactional
	public void sendForApproval(int paymentRequestId, UserSession userSession) throws BaseException {
		Session session = openSession();
		Transaction tx = null;
		try {
			tx = (Transaction) session.beginTransaction();
			
			PaymentRequest paymentRequest =  (PaymentRequest) load(PaymentRequest.class, paymentRequestId); 
			paymentRequest.setLastUpdatedBy(userSession.getUserId());
			paymentRequest.setLastUpdatedDate(new Date());
			paymentRequest.setPrfStatus("PENDING");
			session.update(paymentRequest);
			
			PRStatus prStatus = createPRStatus(paymentRequestId, null, "PENDING", userSession);
			session.save(prStatus);
			
			List<ApprovalWorkflow> approvals = createApprovalWorkflow(paymentRequest, userSession);
			for(ApprovalWorkflow workflow:approvals) {
				session.save(workflow);
			}
			tx.commit();
			
		}catch (Exception ex) {
			rollbackTransaction(tx);	
            
			if (ex instanceof ConstraintViolationException){
        		throw new BaseException("errors.uniqueConstraintError");
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
	public void approvePayment(int paymentRequestId, int workflowId, UserSession userSession) throws BaseException {
		Session session = openSession();
		Transaction tx = null;
		try {
			tx = (Transaction) session.beginTransaction();
			
			PaymentRequest paymentRequest =  (PaymentRequest) load(PaymentRequest.class, paymentRequestId); 
			ApprovalWorkflow approvalWorkflow =   (ApprovalWorkflow) load(ApprovalWorkflow.class, workflowId); 
			int maxSequence = sequenceCountForApprovalWorkflow(paymentRequest.getPaymentRequestId());
			String newStatus = "PENDING";
			if(approvalWorkflow.getSequenceNo()==maxSequence) {
				newStatus = "APPROVED";
			}

			//update PR
			paymentRequest.setLastUpdatedBy(userSession.getUserId());
			paymentRequest.setPrfStatus(newStatus);
			session.update(paymentRequest);
			
			//create PRStatus record
			PRStatus prStatus = createPRStatus(paymentRequestId, null, newStatus, userSession);
			session.save(prStatus);
			
			//update ApprovalWorkflow
			approvalWorkflow.setIsComplete("Y");
			approvalWorkflow.setUpdatedUser(userSession.getUserId());
			session.update(approvalWorkflow);
			
			tx.commit();
			
		}catch (Exception ex) {
			rollbackTransaction(tx);	
            
			if (ex instanceof ConstraintViolationException){
        		throw new BaseException("errors.uniqueConstraintError");
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
	
	public int sequenceCountForApprovalWorkflow(int prId) throws BaseException {
		String SQL_QUERY ="select max(pr.sequenceNo) from ApprovalWorkflow pr where pr.prId="+prId+" and pr.isDiscard='N'";
		Query query = getSession().createQuery(SQL_QUERY);
		Integer maxSequence = (Integer) query.uniqueResult();
		if(maxSequence==null)
			return 0;
		else
			return maxSequence; 
	}
	
	@Override
	@Transactional
	public void rejectPayment(int paymentRequestId, int workflowId, String reason, UserSession userSession) throws BaseException {
		Session session = openSession();
		Transaction tx = null;
		try {
			tx = (Transaction) session.beginTransaction();
			
			PaymentRequest paymentRequest =  (PaymentRequest) load(PaymentRequest.class, paymentRequestId); 
			String newStatus = "REJECTED";

			//update PR
			paymentRequest.setLastUpdatedBy(userSession.getUserId());
			paymentRequest.setLastUpdatedDate(new Date());
			paymentRequest.setPrfStatus(newStatus);
			session.update(paymentRequest);
			
			//create PRStatus record
			PRStatus prStatus = createPRStatus(paymentRequestId, reason, newStatus, userSession);
			session.save(prStatus);
			
			//update ApprovalWorkflows
			List<ApprovalWorkflow> approvals = getApprovalWorkflowById(paymentRequestId);
			for(ApprovalWorkflow approvalWorkflow:approvals) {
				if(approvalWorkflow.getWorkflowId()==workflowId)
					approvalWorkflow.setIsComplete("Y");
				approvalWorkflow.setIsDiscard("Y");
				approvalWorkflow.setUpdatedUser(userSession.getUserId());
				session.update(approvalWorkflow);
			}
			tx.commit();
			
		}catch (Exception ex) {
			rollbackTransaction(tx);	
            
			if (ex instanceof ConstraintViolationException){
        		throw new BaseException("errors.uniqueConstraintError");
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
	
	public List<ApprovalWorkflow> getApprovalWorkflowById(int prId) throws BaseException {
		Criteria criteria = getSession().createCriteria(ApprovalWorkflow.class);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		criteria.add(Restrictions.eq("prId", prId));
		criteria.add(Restrictions.eq("isDiscard", "N"));
		List<ApprovalWorkflow> list = criteria.list().size()>0? criteria.list():null;
		return list;
	}
	
	@Override
	@Transactional
	public void cancelPayment(int paymentRequestId, int workflowId, String reason, UserSession userSession) throws BaseException {
		Session session = openSession();
		Transaction tx = null;
		try {
			tx = (Transaction) session.beginTransaction();
			
			PaymentRequest paymentRequest =  (PaymentRequest) session.load(PaymentRequest.class, paymentRequestId); 
			String newStatus = "CANCELED";
			
			//update PR
			paymentRequest.setLastUpdatedBy(userSession.getUserId());
			paymentRequest.setLastUpdatedDate(new Date());
			paymentRequest.setPrfStatus(newStatus);
			session.update(paymentRequest);
			
			//create PRStatus record
			PRStatus prStatus = createPRStatus(paymentRequestId, reason, newStatus, userSession);
			session.save(prStatus);
			
			//update ApprovalWorkflows
			if(workflowId>0) {
				List<ApprovalWorkflow> approvals = getApprovalWorkflowById(paymentRequestId);
				for(ApprovalWorkflow approvalWorkflow:approvals) {
					if(approvalWorkflow.getWorkflowId()==workflowId)
						approvalWorkflow.setIsComplete("Y");
					approvalWorkflow.setIsDiscard("Y");
					approvalWorkflow.setUpdatedUser(userSession.getUserId());
					approvalWorkflow.setUpdatedDate(new Date());
					session.update(approvalWorkflow);
				}
			}
			
			Budget budget = reverseBudget(paymentRequest.getBudgetId(), paymentRequest.getNetAmount(), userSession, session);
			session.update(budget);
			
			Criteria criteria = session.createCriteria(PRStakeholderSourceDoc.class);
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
			criteria.add(Restrictions.eq("prId", paymentRequest.getPaymentRequestId()));
			List<PRStakeholderSourceDoc> listSrc = (List<PRStakeholderSourceDoc>) criteria.list();
			
		
			tx.commit();
			
		}catch (Exception ex) {
			rollbackTransaction(tx);	
            
			if (ex instanceof ConstraintViolationException){
        		throw new BaseException("errors.uniqueConstraintError");
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
	public List<PREntriesDTO> getDailyGLEntries(int departmentId, Date date) throws BaseException {
		try {
			String mysqlDateString = formatter.format(date);
	
			String SQL_QUERY ="select pr.prNumber, pr.stakeholderId, pr.payMode, pr.paymentRequestId"
					+ " from PaymentRequest pr, PRStatus ps where pr.paymentRequestId=ps.prId"
					+ " and ps.status='APPROVED' and ps.createdDate ='"+mysqlDateString+"'";
			if(departmentId>0) {
				//SQL_QUERY += " and pr.departmentId="+departmentId;
			}
			
			Query query = getSession().createQuery(SQL_QUERY);
			List<Object[]> list = query.list();
			List<PREntriesDTO> listDTO = new ArrayList<PREntriesDTO>();
			for(Object[] o :list) {
				PREntriesDTO prEntriesDTO = new PREntriesDTO();
				prEntriesDTO.setPrNo(o[0].toString());
				
				Stakeholder stakeholder = (Stakeholder) load(Stakeholder.class, Integer.parseInt(o[1].toString())); 
				prEntriesDTO.setStakeholderName(stakeholder.getFullName());

				
				String payMode = o[2]!=null?o[2].toString():"";
				prEntriesDTO.setPayMode(payMode);
				//prEntriesDTO.setPayMode(o[2]!=null?o[2].toString():"");
				prEntriesDTO.setPrId(Integer.parseInt(o[3].toString()));
				listDTO.add(prEntriesDTO);
			}
			
			return listDTO;
		} catch (Exception e) {
			throw new BaseException(e.getMessage());
		}	
		
	}
	
	@Override
	@Transactional
	public List<PaymentRequestDTO> getDailyPRReport(int departmentId, Date date) throws BaseException {
		try {
			String mysqlDateString = formatter.format(date);
			String SQL_QUERY ="select pr.prNumber, pr.stakeholderId, pr.payMode, pr.paymentRequestId,"
					+ " pr.budgetType, pr.actionCodeId ,pr.budgetCodeId, pr.createdBy,"
					+ " pr.netAmount, pr.prfStatus, pr.prDate, pr.budgetId"
					+ " from PaymentRequest pr  where "
					+ " pr.prDate ='"+mysqlDateString+"'";
			if(departmentId>0) {
				SQL_QUERY += " and pr.departmentId="+departmentId;
			}
			
			Query query = getSession().createQuery(SQL_QUERY);
			List<Object[]> list = query.list();
			List<PaymentRequestDTO> listDTO = new ArrayList<PaymentRequestDTO>();
			for(Object[] o :list) {
				PaymentRequestDTO paymentRequestDTO = new PaymentRequestDTO();
				paymentRequestDTO.setPrNumber(o[0].toString());
				
				Stakeholder stakeholder = (Stakeholder) load(Stakeholder.class, Integer.parseInt(o[1].toString())); 
				paymentRequestDTO.setStakeholderName(stakeholder.getFullName());
				
				String payMode = o[2]!=null?o[2].toString():"";
				
				paymentRequestDTO.setPayMode(payMode);
				paymentRequestDTO.setPaymentRequestId(Integer.parseInt(o[3].toString()));
				paymentRequestDTO.setBudgetType(o[4]!=null?o[4].toString():"");
				paymentRequestDTO.setBudgetId(Integer.parseInt(o[11].toString()));
				paymentRequestDTO.setNetAmount(Double.parseDouble(o[8].toString()));
				paymentRequestDTO.setPrfStatus(o[9]!=null?o[9].toString():"");
				paymentRequestDTO.setPrDateStr((o[10].toString()));
				paymentRequestDTO.setCreatedBy(o[7].toString());
				listDTO.add(paymentRequestDTO);
			}
			
			return listDTO;
		} catch (Exception e) {
			throw new BaseException(e.getMessage());
		}	
		
	}

}
