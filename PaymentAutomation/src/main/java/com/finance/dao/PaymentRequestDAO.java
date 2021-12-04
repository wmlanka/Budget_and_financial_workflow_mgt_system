package com.finance.dao;

import java.util.Date;
import java.util.List;

import com.finance.domain.ApprovalWorkflow;
import com.finance.domain.Location;
import com.finance.domain.PaymentRequest;
import com.finance.domain.TempPREntries;
import com.finance.domain.UserSession;
import com.finance.dto.PREntriesDTO;
import com.finance.dto.PaymentRequestDTO;
import com.finance.dto.SourceDocumentDTO;
import com.finance.util.BaseException;

public interface PaymentRequestDAO {
	public Location getLocationById(int locationId)throws BaseException;
	public PaymentRequest getPayDeductionSummary(String[] sourceDocIds) throws BaseException;
	public List<TempPREntries> getTempPREntries(String userId) throws BaseException;
	public void createTempPREntry(TempPREntries tempPREntries, UserSession userSession) throws BaseException;
	public void deleteTempPREntries(String userId) throws BaseException;
	public void deleteTempPREntryById(int tempPREntryId) throws BaseException;
	
	public PaymentRequest getPaymentRequestById(int id) throws BaseException;
	public PaymentRequest getPaymentRequestAllDataById(int id, UserSession userSession) throws BaseException ;
	
	public PaymentRequest createPaymentRequest(PaymentRequest paymentRequest, int stakeholderId, List<Integer> selectedSourceDocs,UserSession userSession) throws BaseException;
	public PaymentRequest updatePaymentRequest(PaymentRequest paymentRequest, int stakeholderId, List<Integer> selectedSourceDocs,UserSession userSession) throws BaseException;
	
	public double[] checkDrCRofTempPREntries(String userId) throws BaseException;
	public List<PaymentRequestDTO> searchPR(int stakeholderId, Integer seqNo, Date fromDate, Date toDate, String status, String UserType, int departmentId, UserSession userSession) throws BaseException;
	
	public List<SourceDocumentDTO> getSourceDocForPR(int stakeholderId, int prId, UserSession userSession, String status) throws BaseException;
	public ApprovalWorkflow getCurrentApprovalByPRId(int prId, UserSession userSession) throws BaseException;
	
	//report
	public List<PaymentRequestDTO> getPaymentSummaryData(int seqNo,String year, int costCenter, String status, int stakehlderId,
			double minimumAmount, Date fromDate, Date toDate, int invoiceId, UserSession userSession) throws BaseException;
	
	public int getPendingApprovalsforUser(UserSession userSession)throws BaseException;//Notification
	public void sendForApproval(int paymentRequestId, UserSession userSession) throws BaseException;
	public void approvePayment(int paymentRequestId, int workflowId, UserSession userSession) throws BaseException;
	public void rejectPayment(int paymentRequestId, int workflowId, String reason, UserSession userSession) throws BaseException;
	public void cancelPayment(int paymentRequestId, int workflowId, String reason, UserSession userSession) throws BaseException;
	
	//Report - Daily
	public List<PREntriesDTO> getDailyGLEntries(int departmentId, Date date) throws BaseException;
	public List<PaymentRequestDTO> getDailyPRReport(int departmentId, Date date) throws BaseException;
}
