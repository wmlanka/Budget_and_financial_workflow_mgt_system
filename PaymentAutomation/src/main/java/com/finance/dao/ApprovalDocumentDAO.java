package com.finance.dao;

import java.util.List;

import org.hibernate.Session;

import com.finance.domain.ApprovalDocument;
import com.finance.domain.UserSession;
import com.finance.util.BaseException;

public interface ApprovalDocumentDAO {
	public List<ApprovalDocument> getAllApprovalDocument(UserSession userSession) throws BaseException;
	public ApprovalDocument getApprovalDocumentById(int approvalDocId) throws BaseException;
	public void deleteApprovalDocument(int approvalDocId, UserSession userSession) throws BaseException;
	public void createApprovalDocument(ApprovalDocument approvalDocument, UserSession userSession) throws BaseException;
	public void updateApprovalDocument(ApprovalDocument approvalDocument, UserSession userSession) throws BaseException;
	public List<ApprovalDocument> getApprovalDocByApprovalType(String approvalType, UserSession userSession) throws BaseException;
	public List<ApprovalDocument> getAvailableApprovalDoc(String approvalType, int approvalDocId, UserSession userSession) throws BaseException;
	public ApprovalDocument updateApprovalDocumentBalance(int approvalDocId, double usedAmount, boolean isRelease, UserSession userSession,Session session) throws BaseException;
}
