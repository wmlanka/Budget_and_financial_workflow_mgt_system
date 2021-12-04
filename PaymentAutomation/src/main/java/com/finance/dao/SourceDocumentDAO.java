package com.finance.dao;

import java.util.List;

import com.finance.domain.Budget;
import com.finance.domain.DocumentUpload;
import com.finance.domain.SourceDocApprovals;
import com.finance.domain.SourceDocument;
import com.finance.domain.TempSourceDocApprovals;
import com.finance.domain.UserSession;
import com.finance.dto.SourceDocumentDTO;
import com.finance.util.BaseException;

public interface SourceDocumentDAO {
	public List<SourceDocApprovals> getAllSourceDocApprovals() throws BaseException;
	public List<TempSourceDocApprovals> getTempSourceDocApprovals(String userId) throws BaseException;
	public void createTempSourceApprovals(TempSourceDocApprovals tempSourceDocApprovals, UserSession userSession) throws BaseException;
	public void deleteTempSourceDocApprovals(String userId) throws BaseException;
	void deleteTempSourceDocAppById(int tempSourceDocAppId, UserSession userSession) throws BaseException;

	void createSourceDocument(SourceDocument sourceDocument,UserSession userSession, String[] removeTempApprovals, DocumentUpload documentUpload) throws BaseException;
	void updateSourceDocument(SourceDocument sourceDocument, UserSession userSession,String[] removeTempApprovals, DocumentUpload documentUpload) throws BaseException;
	void deleteSourceDocument(int sourceDocumentId, UserSession userSession) throws BaseException;
	
	public List<SourceDocumentDTO> getAllSourceDocumentDTO(UserSession userSession) throws BaseException;
	public SourceDocument getSourceDocumentById(int sourceDocumentId) throws BaseException;
	public SourceDocument getSourceDocumentAllDataById(int sourceDocumentId, UserSession userSession) throws BaseException;
	public List<SourceDocumentDTO> getSourceDocByStakeholderId(int stakeholderId) throws BaseException;
	public SourceDocument getSourceDocForPR(int sourceDocumentId) throws BaseException;
}
