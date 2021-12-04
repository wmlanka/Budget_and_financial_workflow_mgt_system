package com.finance.daoImpl;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.JDBCException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;

import com.finance.dao.ApprovalDocumentDAO;
import com.finance.dao.BaseDAOSupport;
import com.finance.dao.SourceDocumentDAO;
import com.finance.domain.ApprovalDocument;
import com.finance.domain.DocumentUpload;
import com.finance.domain.SourceDocApprovals;
import com.finance.domain.SourceDocument;
import com.finance.domain.Stakeholder;
import com.finance.domain.TempSourceDocApprovals;
import com.finance.domain.UserSession;
import com.finance.dto.SourceDocumentDTO;
import com.finance.enumeration.SourceDocTypeEnum;
import com.finance.util.BaseException;
import com.finance.util.ConstraintException;

public class SourceDocumentDAOImpl extends BaseDAOSupport implements SourceDocumentDAO{
	ApprovalDocumentDAO approvalDocumentDAO;
	
	public ApprovalDocumentDAO getApprovalDocumentDAO() {
		return approvalDocumentDAO;
	}
	public void setApprovalDocumentDAO(ApprovalDocumentDAO approvalDocumentDAO) {
		this.approvalDocumentDAO = approvalDocumentDAO;
	}

	@Override
	@Transactional
	public List<SourceDocApprovals> getAllSourceDocApprovals() throws BaseException {
		List<SourceDocApprovals> list = null;
		try {
			list = getSession().createCriteria(SourceDocApprovals.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		} catch (HibernateException e) {
			throw new BaseException(e);
		} catch (Exception e) {
			throw new BaseException(e);
		}
		return list;
	}
	
	@Override
	@Transactional
	public List<TempSourceDocApprovals> getTempSourceDocApprovals(String userId) throws BaseException {
		Criteria criteria = getSession().createCriteria(TempSourceDocApprovals.class);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		criteria.add(Restrictions.eq("userId", userId));
		List<TempSourceDocApprovals> list = (List<TempSourceDocApprovals>) criteria.list();
		return list;
	}
	
	@Override
	@Transactional
	public void createTempSourceApprovals(TempSourceDocApprovals tempSourceDocApprovals, UserSession userSession) throws BaseException {
		try {
			
			ApprovalDocument approvalDocument = (ApprovalDocument) load(ApprovalDocument.class, tempSourceDocApprovals.getApprovalDocId());
			String isPartPay=approvalDocument.getIsPartPayment();
			
			Criteria criteria = getSession().createCriteria(TempSourceDocApprovals.class);
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
			criteria.add(Restrictions.eq("approvalDocId", tempSourceDocApprovals.getApprovalDocId()));
			criteria.add(Restrictions.eq("userId", userSession.getUserId()));
			TempSourceDocApprovals temp =  criteria.list().size()>0?((TempSourceDocApprovals) criteria.list().get(0)):null;
			
			if(isPartPay.equals("N") && tempSourceDocApprovals.getAppliedAmount()!=approvalDocument.getApprovedFullAmount()) {
				throw new BaseException("error.partpayNotallow");
			}
				
			if(temp!=null) {
				temp.setAppliedAmount(tempSourceDocApprovals.getAppliedAmount());
				//tempSourceDocApprovals.setAppliedAmount(tempSourceDocApprovals.getAppliedAmount());
				update(temp);
			}else {
				tempSourceDocApprovals.setApprovalRefNo(approvalDocument.getReferenceNo());
				tempSourceDocApprovals.setUserId(userSession.getUserId());
				save(tempSourceDocApprovals);
			}
	
		} catch (BaseException e) {
			if(e.getMessage().equals("errors.uniqueConstraintError")) { //ERROR_UNIQUE_CONSTRAINT
				throw new BaseException("error.sourceApprovalDuplicate");
			}else
				throw new BaseException(e);
		}catch(Exception e) {
			throw new BaseException(e.getMessage());
		}
	}
	
	@Override
	@Transactional
	public void deleteTempSourceDocApprovals(String userId) throws BaseException {
		Criteria criteria = getSession().createCriteria(TempSourceDocApprovals.class);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		criteria.add(Restrictions.eq("userId", userId));
		List list = (List<TempSourceDocApprovals>) criteria.list();
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
	public void createSourceDocument(SourceDocument sourceDocument, UserSession userSession, String[] removeTempApprovals, DocumentUpload documentUpload) throws BaseException {
		Session session = openSession();
		Transaction tx = null;
		double approvalTotal = 0.0;
		
		try {
			tx = (Transaction) session.beginTransaction();
			
			//user deleted temp Ids
			/*if(removeTempApprovals!=null) {
				for(String id : removeTempApprovals) {
					TempSourceDocApprovals temp = (TempSourceDocApprovals) load(TempSourceDocApprovals.class, Integer.parseInt(id));
					session.delete(temp);
					session.flush();
				}
			}*/
			
			//save source document
			sourceDocument.setLastUpdatedBy(userSession.getUserId());
			sourceDocument.setCreatedBy(userSession.getUserId());
			sourceDocument.setCreatedDate(new Date());
			sourceDocument.setLastUpdatedDate(new Date());
			sourceDocument.setStatus("A");
			sourceDocument.setIsPRUsed("N");
			sourceDocument.setDepartmentId(userSession.getDepartmentId());
			session.save(sourceDocument);
			
			if(documentUpload!=null) {
//				documentUpload.setSourceDocId(sourceDocument.getSourceDocumentId());
//				documentUpload.setReferenceNo(sourceDocument.getReferenceNo());
//				documentUpload.setCreatedDate(new Date());
//				documentUpload.setCreatedBy(userSession.getUserId());
//				documentUpload.setAttachmentType("SOURCE_DOC");
//				documentUpload.setSequenceNo(1);
//				documentUpload.setDepartmentId(userSession.getDepartmentId());
//				session.save(documentUpload);
//				
//				/*upload doc transfer from temp*/
//				copyFile(documentUpload.getTempDocPath(),documentUpload.getDocPath());
			}
			
			List<TempSourceDocApprovals> tempApprovals = getTempSourceDocApprovals(userSession.getUserId());
			for(TempSourceDocApprovals temp : tempApprovals) {
				SourceDocApprovals sourceDocApprovals = new SourceDocApprovals();
				sourceDocApprovals.setApprovalType(temp.getApprovalType());
				sourceDocApprovals.setApprovalDocId(temp.getApprovalDocId());
				sourceDocApprovals.setApprovalRefNo(temp.getApprovalRefNo());
				sourceDocApprovals.setAppliedAmount(temp.getAppliedAmount());
				sourceDocApprovals.setSourceDocumentId(sourceDocument.getSourceDocumentId());
				//sourceDocApprovals.setSourceDocument(sourceDocument);
				session.save(sourceDocApprovals);
				approvalTotal = approvalTotal+temp.getAppliedAmount();
				//approvals.add(sourceDocApprovals);
				
				/*update approval document without commit*/
				updateApprovalDocumentBalance(temp.getApprovalDocId(),temp.getAppliedAmount(),false,userSession,session);
			}
			
			if(approvalTotal!=sourceDocument.getNetAmount()) {
				throw new BaseException("error.approvalTotNotenough");
			}
			
			tx.commit();
			//sourceDocument.setApprovalsList(approvals);
			//save(sourceDocument);
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
		
	}
	
//	public void copyFile(String fromPath, String toPath) throws BaseException {
//		try {
//		   Path src = Paths.get(fromPath);
//		   Path dest = Paths.get(toPath);
//		   
//		   Stream<Path> files = Files.walk(src);
//		   
//		    // copy all files and folders from `src` to `dest`
//		    files.forEach(file -> {
//		        try {
//		            Files.copy(file, dest.resolve(src.relativize(file)),
//		                    StandardCopyOption.REPLACE_EXISTING);
//		        } catch (IOException e) {
//		        	//throw (e.getMessage());
//		        }
//		    });
//
//		    // close the stream
//		    files.close();
//		}catch (IOException ex) {
//		    throw new BaseException(ex.getMessage());
//		}
//	}

	@Override
	@Transactional
	public void updateSourceDocument(SourceDocument sourceDocument, UserSession userSession, String[] removeTempApprovals, DocumentUpload documentUpload) throws BaseException {
		Session session = openSession();
		Transaction tx = null;
		double approvalTotal = 0.0;
		//List deleteApprovals = new ArrayList<String>();
		List<ApprovalDocument> updatedApprovals= null;
		
		try {
			tx = (Transaction) session.beginTransaction();
			
			//user deleted temp Ids
			/*if(removeTempApprovals!=null) {
				for(String id : removeTempApprovals) {
					deleteApprovals.add(id);
					TempSourceDocApprovals temp = (TempSourceDocApprovals) load(TempSourceDocApprovals.class, Integer.parseInt(id));
					session.delete(temp);
					session.flush();
				}
			}*/
			
			//save source document
			sourceDocument.setLastUpdatedBy(userSession.getUserId());
			sourceDocument.setCreatedBy(userSession.getUserId());
			sourceDocument.setCreatedDate(new Date());
			sourceDocument.setLastUpdatedDate(new Date());
			sourceDocument.setIsPRUsed("N");
			sourceDocument.setDepartmentId(userSession.getDepartmentId());
			session.update(sourceDocument);
			
			if(documentUpload!=null) {
//				documentUpload.setSourceDocId(sourceDocument.getSourceDocumentId());
//				documentUpload.setReferenceNo(sourceDocument.getReferenceNo());
//				documentUpload.setCreatedDate(new Date());
//				documentUpload.setCreatedBy(userSession.getUserId());
//				documentUpload.setAttachmentType("SOURCE_DOC");
//				documentUpload.setSequenceNo(1);
//				documentUpload.setDepartmentId(userSession.getDepartmentId());
//				session.save(documentUpload);
//				
//				/*upload doc transfer from temp*/
//				copyFile(documentUpload.getTempDocPath(),documentUpload.getDocPath());
			}
			
			//delete existing SourceDocApprovals without commit and update Source doc approval with commit(otherwise error occur)
			updatedApprovals = deleteSourceDocApprovals(session,sourceDocument.getSourceDocumentId(),userSession);
			
			List<TempSourceDocApprovals> tempApprovals = getTempSourceDocApprovals(userSession.getUserId());
			for(TempSourceDocApprovals temp : tempApprovals) {
				//if(deleteApprovals.contains(temp.getTempSourceDocAppId()+""))
					//continue;//deleted ones no need to add
				SourceDocApprovals sourceDocApprovals = new SourceDocApprovals();
				sourceDocApprovals.setApprovalType(temp.getApprovalType());
				sourceDocApprovals.setApprovalDocId(temp.getApprovalDocId());
				sourceDocApprovals.setApprovalRefNo(temp.getApprovalRefNo());
				sourceDocApprovals.setAppliedAmount(temp.getAppliedAmount());
				sourceDocApprovals.setSourceDocumentId(sourceDocument.getSourceDocumentId());
				session.save(sourceDocApprovals);
				approvalTotal = approvalTotal+temp.getAppliedAmount();
			
				boolean exist = false;
				ApprovalDocument approvalDocument = new ApprovalDocument();
				
				for(ApprovalDocument o:updatedApprovals) {
					if(o.getApprovalDocId()==temp.getApprovalDocId()) {
						exist = true;
						boolean release = true;//alreday in the list contains removal approved docs
						double adjustAMount = 0.0;
						if(o.getAdjustAmount()>temp.getAppliedAmount()){
							release = false;//add+
							adjustAMount = temp.getAppliedAmount() - o.getAdjustAmount();
						}else {
							release = true;
							adjustAMount = o.getAdjustAmount() - temp.getAppliedAmount();
						}
						o.setAdjustAmount(adjustAMount);
						o.setRelease(release);
					}
				}
				if(!exist) {
					approvalDocument.setApprovalDocId(temp.getApprovalDocId());
					approvalDocument.setAdjustAmount(temp.getAppliedAmount());
					approvalDocument.setRelease(false);
					updatedApprovals.add(approvalDocument);
				}
			}
			
			if(approvalTotal!=sourceDocument.getNetAmount()) {
				throw new BaseException("error.approvalTotNotenough");
			}
			for(ApprovalDocument o : updatedApprovals) {
				updateApprovalDocumentBalance(o.getApprovalDocId(),o.getAdjustAmount(),o.isRelease(),userSession,session);//this will automaticcaly commit want to check
				//session.update(approvalDocument);
			}
//			if(updatedApprovals.size()>1)
//				throw new BaseException("12");
			tx.commit();
	
		} catch (Exception ex) {
			rollbackTransaction(tx);	
			//approval document should roll back if error is from aproval doc update
            
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
	
	public List<SourceDocApprovals> getSourceDocApprovals(int sourceDocumentId) throws BaseException {
		Criteria criteria = getSession().createCriteria(SourceDocApprovals.class);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		criteria.add(Restrictions.eq("sourceDocumentId", sourceDocumentId));
		List<SourceDocApprovals> list = (List<SourceDocApprovals>) criteria.list();
		return list;
	}
	
	public List<ApprovalDocument> deleteSourceDocApprovals(Session session, int sourceDocumentId, UserSession userSession)  throws BaseException {
		List<SourceDocApprovals> approvals = getSourceDocApprovals(sourceDocumentId);
		List<ApprovalDocument> updatedApprovals = new ArrayList<ApprovalDocument>();
		for(SourceDocApprovals sourceDocApprovals:approvals) {
			//ApprovalDocument o = getApprovalDocumentDAO().updateApprovalDocumentBalance(sourceDocApprovals.getApprovalDocId(), sourceDocApprovals.getAppliedAmount(), true, userSession, session);
			//update(o);//need to set for temporary table
			session.delete(sourceDocApprovals);
			session.flush();
			
			ApprovalDocument approvalDocument = new ApprovalDocument();
			approvalDocument.setApprovalDocId(sourceDocApprovals.getApprovalDocId());
			approvalDocument.setAdjustAmount(sourceDocApprovals.getAppliedAmount());
			approvalDocument.setRelease(true);
			updatedApprovals.add(approvalDocument);
		}
		return updatedApprovals;
	}

	@Override
	@Transactional
	public void deleteSourceDocument(int sourceDocumentId, UserSession userSession) throws BaseException {
		Session session = openSession();
		Transaction tx = null;
		
			try {
				tx = (Transaction) session.beginTransaction();
				
				SourceDocument sourceDocument = (SourceDocument) session.load(SourceDocument.class, sourceDocumentId);
				if(sourceDocument != null){
					
					//update approvals
					List<SourceDocApprovals> approvals = getSourceDocApprovals(sourceDocumentId);
					List<ApprovalDocument> updatedApprovals = new ArrayList<ApprovalDocument>();
					for(SourceDocApprovals sourceDocApprovals:approvals) {
						ApprovalDocument approvalDocument = (ApprovalDocument) load(ApprovalDocument.class, sourceDocApprovals.getApprovalDocId());
						double newBalance = approvalDocument.getBalanceAmount()+sourceDocApprovals.getAppliedAmount();
						double newUtilized = approvalDocument.getUtilizedAmount() - sourceDocApprovals.getAppliedAmount();
						approvalDocument.setBalanceAmount(newBalance);
						approvalDocument.setUtilizedAmount(newUtilized);
						approvalDocument.setLastUpdatedBy(userSession.getUserId());
						approvalDocument.setLastUpdatedDate(new Date());
						if(newUtilized<=0)
							approvalDocument.setIsUsed("N");
						updatedApprovals.add(approvalDocument);
					}
					
					session.delete(sourceDocument);
					
					for(ApprovalDocument a : updatedApprovals) {
						session.update(a);
					}
					tx.commit();
				}
			}
			catch (Exception ex) {
				rollbackTransaction(tx);
	            
				if (ex instanceof ConstraintViolationException){
	        		throw new BaseException("errors.sourceDocDeletion");
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
	
	
	public void deleteSourceDocumentOld(int sourceDocumentId, UserSession userSession) throws BaseException {
		SourceDocument sourceDocument = (SourceDocument) load(SourceDocument.class, sourceDocumentId);

		if(sourceDocument != null){
			try {
				//update approvals
				List<SourceDocApprovals> approvals = getSourceDocApprovals(sourceDocumentId);
				List<ApprovalDocument> updatedApprovals = new ArrayList<ApprovalDocument>();
				for(SourceDocApprovals sourceDocApprovals:approvals) {
					ApprovalDocument approvalDocument = (ApprovalDocument) load(ApprovalDocument.class, sourceDocApprovals.getApprovalDocId());
					double newBalance = approvalDocument.getBalanceAmount()+sourceDocApprovals.getAppliedAmount();
					double newUtilized = approvalDocument.getUtilizedAmount() - sourceDocApprovals.getAppliedAmount();
					approvalDocument.setBalanceAmount(newBalance);
					approvalDocument.setUtilizedAmount(newUtilized);
					approvalDocument.setLastUpdatedBy(userSession.getUserId());
					approvalDocument.setLastUpdatedDate(new Date());
					if(newUtilized<=0)
						approvalDocument.setIsUsed("N");
					updatedApprovals.add(approvalDocument);
				}
				
				delete(sourceDocument);
				
				for(ApprovalDocument a : updatedApprovals) {
					update(a);
				}
			} catch (BaseException e) {
				throw new BaseException(e);

			} catch (ConstraintException e) {
				throw new BaseException("error.sourceDocDeletion");
			}catch (Exception e) {
				throw new BaseException(e);
			}
		}
		
	}

	@Override
	@Transactional
	public void deleteTempSourceDocAppById(int tempSourceDocAppId, UserSession userSession) throws BaseException {
		TempSourceDocApprovals temp = (TempSourceDocApprovals) load(TempSourceDocApprovals.class, tempSourceDocAppId);

		if(temp != null){
			try {
				delete(temp);
			} catch (BaseException e) {
				throw new BaseException(e);

			} catch (ConstraintException e) {
				throw new BaseException(e);
				//throw new BaseException("error.budgetDeletion");
			}
		}
		
	}

	@Override
	@Transactional
	public List<SourceDocumentDTO> getAllSourceDocumentDTO(UserSession userSession) throws BaseException {
		List<SourceDocument> list = null;
		List<SourceDocumentDTO> listDTO = new ArrayList<SourceDocumentDTO>();
		Format formatter = new SimpleDateFormat("yyyy-MM-dd");

		try {
			//list = getSession().createCriteria(SourceDocument.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
			Criteria criteria = getSession().createCriteria(SourceDocument.class);
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
			criteria.add(Restrictions.eq("departmentId", userSession.getDepartmentId()));
			list = (List<SourceDocument>) criteria.list();
			
			for(SourceDocument sourceDocument : list) {
				SourceDocumentDTO sourceDocumentDTO = new SourceDocumentDTO();
				sourceDocumentDTO.setSourceDocumentId(sourceDocument.getSourceDocumentId());
				
				SourceDocTypeEnum sourceDocTypeEnum = SourceDocTypeEnum.getEnumBySourceDocType(sourceDocument.getDocumentType());
				sourceDocumentDTO.setDocumentType(sourceDocTypeEnum.getDescription());
				
				sourceDocumentDTO.setReferenceNo(sourceDocument.getReferenceNo());
				sourceDocumentDTO.setDocumentDateStr(formatter.format(sourceDocument.getDocumentDate()));
				sourceDocumentDTO.setNetAmount(new BigDecimal(sourceDocument.getNetAmount()));
				sourceDocumentDTO.setVatAmount(new BigDecimal(sourceDocument.getVatAmount()));
				sourceDocumentDTO.setGrossAmount(new BigDecimal(sourceDocument.getGrossAmount()));
				Stakeholder stakeholder = (Stakeholder) load(Stakeholder.class, sourceDocument.getStakeholderId());
				sourceDocumentDTO.setStakeholderName(stakeholder.getFullName());
				sourceDocumentDTO.setIsPRUsed(sourceDocument.getIsPRUsed().equals("Y")?"Yes":"No");
				sourceDocumentDTO.setStatus(sourceDocument.getStatus().equals("A")?"Active":"Inactive");
				listDTO.add(sourceDocumentDTO);
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
	public SourceDocument getSourceDocumentById(int sourceDocumentId) throws BaseException {
		SourceDocument sourceDocument = (SourceDocument) getSession().load(SourceDocument.class, sourceDocumentId);
		return sourceDocument;
	}
	
	@Override
	@Transactional
	public SourceDocument getSourceDocumentAllDataById(int sourceDocumentId, UserSession userSession) throws BaseException {
		SourceDocument sourceDocument = (SourceDocument) getSession().load(SourceDocument.class, sourceDocumentId);
		
		//get approvals
		Criteria criteria = getSession().createCriteria(SourceDocApprovals.class);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		criteria.add(Restrictions.eq("sourceDocumentId", sourceDocument.getSourceDocumentId()));
		List<SourceDocApprovals> list = (List<SourceDocApprovals>) criteria.list();

		//save to temp approvals
		List tempList = new ArrayList<TempSourceDocApprovals>();
		for(SourceDocApprovals s :list) {
			TempSourceDocApprovals temp = new TempSourceDocApprovals();
			temp.setSourceDocumentId(s.getSourceDocumentId());
			temp.setApprovalType(s.getApprovalType());
			temp.setApprovalDocId(s.getApprovalDocId());
			temp.setApprovalRefNo(s.getApprovalRefNo());
			temp.setUserId(userSession.getUserId());
			temp.setAppliedAmount(s.getAppliedAmount());
			tempList.add(temp);
			
		}
		saveAll(tempList);
		
		//get DocumentUpload
		DocumentUpload documentUpload = null;
		criteria = getSession().createCriteria(DocumentUpload.class);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		criteria.add(Restrictions.eq("sourceDocId", sourceDocumentId));
		if(criteria.list().size()!=0)
			documentUpload = (DocumentUpload) criteria.list().get(0);
		sourceDocument.setDocumentUpload(documentUpload);
		return sourceDocument;
	}

	@Override
	@Transactional
	public List<SourceDocumentDTO> getSourceDocByStakeholderId(int stakeholderId) throws BaseException {
		Criteria criteria = getSession().createCriteria(SourceDocument.class);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		criteria.add(Restrictions.eq("stakeholderId", stakeholderId));
		criteria.add(Restrictions.eq("status", "A"));
		criteria.add(Restrictions.eq("isPRUsed", "N"));
		List<SourceDocument> list = criteria.list();
		
		List<SourceDocumentDTO> listDTO = new ArrayList<SourceDocumentDTO>();
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
			//if(sourceDocument.getSourceDocumentId()==53)
				//sourceDocumentDTO.setSelect("checked"); // testing
			listDTO.add(sourceDocumentDTO);
		}
		return listDTO;
	}

	@Override
	public SourceDocument getSourceDocForPR(int sourceDocumentId) throws BaseException {
		Criteria criteria = getSession().createCriteria(SourceDocument.class);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		criteria.add(Restrictions.eq("sourceDocumentId", sourceDocumentId));
		criteria.add(Restrictions.eq("status", "A"));
		criteria.add(Restrictions.eq("isPRUsed", "N"));
		SourceDocument sourceDocument = (SourceDocument) criteria.uniqueResult();
		return sourceDocument;
	}
	
	//this method automatically update approval doc and commit(want to check)
	public ApprovalDocument updateApprovalDocumentBalance(int approvalDocId, double usedAmount, boolean isRelease, UserSession userSession, Session session) throws BaseException {
		ApprovalDocument approvalDocument = (ApprovalDocument) session.load(ApprovalDocument.class, approvalDocId);
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
		approvalDocument.setLastUpdatedDate(new Date());
		if(session==null)
			update(approvalDocument);
		else
			session.update(approvalDocument);//commit will happend in parent method
		return approvalDocument;
			
	}

}
