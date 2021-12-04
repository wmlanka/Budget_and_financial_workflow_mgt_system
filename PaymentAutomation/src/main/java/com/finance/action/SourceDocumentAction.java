package com.finance.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import com.finance.dao.ApprovalDocumentDAO;
import com.finance.dao.SourceDocumentDAO;
import com.finance.dao.StakeholderDAO;
import com.finance.domain.ActionCode;
import com.finance.domain.ApprovalDocument;
import com.finance.domain.BudgetCode;
import com.finance.domain.DocumentUpload;
import com.finance.domain.SourceDocApprovals;
import com.finance.domain.SourceDocument;
import com.finance.domain.Stakeholder;
import com.finance.domain.TempSourceDocApprovals;
import com.finance.domain.UserSession;
import com.finance.dto.SourceDocumentDTO;
import com.finance.enumeration.ApprovalTypeEnum;
import com.finance.enumeration.SourceDocTypeEnum;
import com.finance.util.BaseException;
import com.finance.util.SessionUtil;
import com.opensymphony.xwork2.config.entities.Parameterizable;

public class SourceDocumentAction extends BaseAction implements Parameterizable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String command;
	private UserSession userSession;
	private SourceDocument sourceDocument;
	private List<SourceDocumentDTO> listDTO;

	private List<SourceDocTypeEnum> sourceDocTypeList = new ArrayList();
	private List<Stakeholder> stakeholderList =  new ArrayList();
	private Map<String, String> stakeholderMap = new LinkedHashMap<String, String>();
	
	private int stakeholderId;
	private String referenceNo;
	private String description;
	private String sourceDocType;//BudgetTypeEnum
	
	private int sourceDocumentId;
	private Date documentDate;
	private String remark;
	private double netAmount=0.00;
	private double grossAmount=0.00;//netAmount+vatAmount+otherTaxAmount
	private double vatAmount=0.00;
	private double vatPercentage;
	private double otherTaxAmount=0.00;
	private String status;
	private int departmentId;
	private String isPRUsed;
	
	private int tempSourceDocAppId;
	private String approvalType;
	private List<ApprovalTypeEnum> approvalTypeList;
	private Integer approvalDocId;
	private List<ApprovalDocument> approvalDocumentList = new ArrayList<ApprovalDocument>();
	private double fullAmount = 0.00;
	private double balanceAmount = 0.00;
	private double appliedAmount = 0.00;
	
	private List<TempSourceDocApprovals> tempSourceApprovalList =  new ArrayList<TempSourceDocApprovals>();
	private SourceDocumentDAO sourceDocumentDAO;
	
	private StakeholderDAO stakeholderDAO;
	private ApprovalDocumentDAO approvalDocumentDAO;
	
	private String[] checksId;
	
	private File fileUpload;
	private String fileUploadContentType;
	private String fileUploadFileName;
	private String attachmentDirecory = "D:\\eclipse_workspace\\pr_attachments\\upload files\\";
	private String tempDirecory = "D:\\eclipse_workspace\\pr_attachments\\temp files\\";
	private String downloadFileName;
	private String newUploadFileName;
	private String downloadPath;
	
	private InputStream fileInputStream;
    
    public InputStream getFileInputStream() {
        return fileInputStream;
    }
	
	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public List<SourceDocTypeEnum> getSourceDocTypeList() {
		return sourceDocTypeList;
	}

	public void setSourceDocTypeList(List<SourceDocTypeEnum> sourceDocTypeList) {
		this.sourceDocTypeList = sourceDocTypeList;
	}

	public String getSourceDocType() {
		return sourceDocType;
	}

	public void setSourceDocType(String sourceDocType) {
		this.sourceDocType = sourceDocType;
	}

	public List<Stakeholder> getStakeholderList() {
		return stakeholderList;
	}

	public void setStakeholderList(List<Stakeholder> stakeholderList) {
		this.stakeholderList = stakeholderList;
	}

	public StakeholderDAO getStakeholderDAO() {
		return stakeholderDAO;
	}

	public void setStakeholderDAO(StakeholderDAO stakeholderDAO) {
		this.stakeholderDAO = stakeholderDAO;
	}

	public Map<String, String> getStakeholderMap() {
		return stakeholderMap;
	}

	public void setStakeholderMap(Map<String, String> stakeholderMap) {
		this.stakeholderMap = stakeholderMap;
	}

	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}

	public int getStakeholderId() {
		return stakeholderId;
	}

	public void setStakeholderId(int stakeholderId) {
		this.stakeholderId = stakeholderId;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getSourceDocumentId() {
		return sourceDocumentId;
	}

	public void setSourceDocumentId(int sourceDocumentId) {
		this.sourceDocumentId = sourceDocumentId;
	}

	public Date getDocumentDate() {
		return documentDate;
	}

	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public double getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(String netAmount) {
		this.netAmount =  Double.parseDouble(netAmount.replace(",", ""));
	}

	public double getGrossAmount() {
		return grossAmount;
	}

	public void setGrossAmount(String grossAmount) {
		this.grossAmount = Double.parseDouble(grossAmount.replace(",", ""));
	}

	public double getVatAmount() {
		return vatAmount;
	}

	public void setVatAmount(String vatAmount) {
		this.vatAmount = Double.parseDouble(vatAmount.replace(",", ""));
	}

	public double getVatPercentage() {
		return vatPercentage;
	}

	public void setVatPercentage(double vatPercentage) {
		this.vatPercentage = vatPercentage;
	}

	public double getOtherTaxAmount() {
		return otherTaxAmount;
	}

	public void setOtherTaxAmount(String otherTaxAmount) {
		this.otherTaxAmount = Double.parseDouble(otherTaxAmount.replace(",", ""));
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	public String getIsPRUsed() {
		return isPRUsed;
	}

	public void setIsPRUsed(String isPRUsed) {
		this.isPRUsed = isPRUsed;
	}

	public String getApprovalType() {
		return approvalType;
	}

	public void setApprovalType(String approvalType) {
		this.approvalType = approvalType;
	}

	public List<ApprovalTypeEnum> getApprovalTypeList() {
		return approvalTypeList;
	}

	public void setApprovalTypeList(List<ApprovalTypeEnum> approvalTypeList) {
		this.approvalTypeList = approvalTypeList;
	}

	public Integer getApprovalDocId() {
		return approvalDocId;
	}

	public void setApprovalDocId(Integer approvalDocId) {
		this.approvalDocId = approvalDocId;
	}

	public List<ApprovalDocument> getApprovalDocumentList() {
		return approvalDocumentList;
	}

	public void setApprovalDocumentList(List<ApprovalDocument> approvalDocumentList) {
		this.approvalDocumentList = approvalDocumentList;
	}

	public ApprovalDocumentDAO getApprovalDocumentDAO() {
		return approvalDocumentDAO;
	}

	public void setApprovalDocumentDAO(ApprovalDocumentDAO approvalDocumentDAO) {
		this.approvalDocumentDAO = approvalDocumentDAO;
	}

	public double getFullAmount() {
		return fullAmount;
	}

	public void setFullAmount(double fullAmount) {
		this.fullAmount = fullAmount;
	}

	public double getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(double balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	public double getAppliedAmount() {
		return appliedAmount;
	}

	public void setAppliedAmount(double appliedAmount) {
		this.appliedAmount = appliedAmount;
	}

//	public List<SourceDocApprovals> getSourceApprovalList() {
//		return sourceApprovalList;
//	}
//
//	public void setSourceApprovalList(List<SourceDocApprovals> sourceApprovalList) {
//		this.sourceApprovalList = sourceApprovalList;
//	}

	public SourceDocumentDAO getSourceDocumentDAO() {
		return sourceDocumentDAO;
	}

	public void setSourceDocumentDAO(SourceDocumentDAO sourceDocumentDAO) {
		this.sourceDocumentDAO = sourceDocumentDAO;
	}

	public List<TempSourceDocApprovals> getTempSourceApprovalList() {
		return tempSourceApprovalList;
	}

	public void setTempSourceApprovalList(List<TempSourceDocApprovals> tempSourceApprovalList) {
		this.tempSourceApprovalList = tempSourceApprovalList;
	}

	public int getTempSourceDocAppId() {
		return tempSourceDocAppId;
	}

	public void setTempSourceDocAppId(int tempSourceDocAppId) {
		this.tempSourceDocAppId = tempSourceDocAppId;
	}

	public List<SourceDocumentDTO> getListDTO() {
		return listDTO;
	}

	public void setListDTO(List<SourceDocumentDTO> listDTO) {
		this.listDTO = listDTO;
	}	

	public String[] getChecksId() {
		return checksId;
	}

	public void setChecksId(String[] checksId) {
		this.checksId = checksId;
	}

	public File getFileUpload() {
		return fileUpload;
	}

	public void setFileUpload(File fileUpload) {
		this.fileUpload = fileUpload;
	}

	public String getFileUploadContentType() {
		return fileUploadContentType;
	}

	public void setFileUploadContentType(String fileUploadContentType) {
		this.fileUploadContentType = fileUploadContentType;
	}

	public String getFileUploadFileName() {
		return fileUploadFileName;
	}

	public void setFileUploadFileName(String fileUploadFileName) {
		this.fileUploadFileName = fileUploadFileName;
	}

	public String getDownloadFileName() {
		return downloadFileName;
	}

	public void setDownloadFileName(String downloadFileName) {
		this.downloadFileName = downloadFileName;
	}

	public String getNewUploadFileName() {
		return newUploadFileName;
	}

	public void setNewUploadFileName(String newUploadFileName) {
		this.newUploadFileName = newUploadFileName;
	}

	public String getDownloadPath() {
		return downloadPath;
	}

	public void setDownloadPath(String downloadPath) {
		this.downloadPath = downloadPath;
	}

	public UserSession getUserSession() {
		return userSession;
	}

	public void setUserSession(UserSession userSession) {
		this.userSession = userSession;
	}

	@Override
	public String execute() throws Exception {
		try {
			
			if (!SessionUtil.validateSession(getHttpServletRequest())){
				addActionError(getText("errors.sessionExpired"));
	            return ERROR;
	        }
			//clearErrorsAndMessages(); don't clear otherwise, success msgs are cleared
			userSession = (UserSession) getHttpServletRequest().getSession().getAttribute("LOGIN_USER");
			getSourceDocumentDAO().deleteTempSourceDocApprovals(userSession.getUserId());
			listDTO = getSourceDocumentDAO().getAllSourceDocumentDTO(userSession);
			sourceDocument = new SourceDocument();
			return SUCCESS;
		} catch (Exception e) {
			addActionMessage(e.getMessage());
			return INPUT;
		}
	}

	public String create() {
		if (getCommand().equals("add")) {
			clearFields();
			sourceDocument = new SourceDocument();
			sourceDocTypeList = SourceDocTypeEnum.getAllSourceDocTypes();
			approvalTypeList = ApprovalTypeEnum.getAllApprovalTypes();
			tempSourceApprovalList =  new ArrayList<TempSourceDocApprovals>();
			
			try {
				stakeholderList = getStakeholderDAO().getAllStakeholder();
				for(Stakeholder st : stakeholderList) {
					stakeholderMap.put(st.getStakeholderId()+"", st.getFullName());
				}
			} catch (BaseException e) {
				e.printStackTrace();
			}
			return INPUT;
		} else {			
			/*if (fileUpload != null && fileUploadFileName!=null){
			  DateFormat df = new SimpleDateFormat("yyyyMMddhhmmss"); // add S if you need milliseconds
			  newFilename = fileUploadFileName + df.format(new Date());
			  File destinationFile = new File(attachmentDirecory + newFilename);
              try{
              	FileUtils.copyFile(fileUpload, destinationFile);
              }
              catch(IOException e){
            	setCommand("add");
  				addActionMessage(getText(e.getMessage()));
  				return INPUT;
              }
			}else{
				setCommand("add");
  				addActionMessage("Invalid File.");
  				return INPUT;
            }*/
			
			sourceDocument = new SourceDocument();
			sourceDocument.setDocumentType(getSourceDocType());
			sourceDocument.setReferenceNo(getReferenceNo());
			sourceDocument.setDocumentDate(getDocumentDate());
			sourceDocument.setStakeholderId(getStakeholderId());
			sourceDocument.setDescription(getDescription());
			sourceDocument.setRemark(getRemark());
			sourceDocument.setNetAmount(getNetAmount());
			sourceDocument.setVatAmount(getVatAmount());
			sourceDocument.setVatPercentage(getVatPercentage());
			sourceDocument.setOtherTaxAmount(getOtherTaxAmount());
			sourceDocument.setGrossAmount(getNetAmount()+getVatAmount()+getOtherTaxAmount());
			
			DocumentUpload documentUpload = null;
//			if(newUploadFileName!=null) {
//				documentUpload = new DocumentUpload();
//				documentUpload.setFileName(fileUploadFileName);
//				documentUpload.setRenameFileName(newUploadFileName);
//				documentUpload.setDocPath(attachmentDirecory + newUploadFileName);
//				documentUpload.setTempDocPath(tempDirecory+newUploadFileName);
//				documentUpload.setContentType(getFileUploadContentType());
//			}
			clearErrorsAndMessages();
			try {
				getSourceDocumentDAO().createSourceDocument(sourceDocument, userSession, getChecksId(),documentUpload);
			} catch (BaseException e) {
				setCommand("add");
				//addActionMessage(getText(e.getMessage()==null?e.toString():""));
				addActionMessage(getText(e.getMessage()));
				return INPUT;
			}
			addActionMessage(getText("message.addSuccess"));
			setCommand(null);//**
			return SUCCESS;
		}

	}
	
	public String uploadToTemp() throws BaseException {
		String newFilename = null;
//		if (fileUpload != null && fileUploadFileName!=null){
//			  DateFormat df = new SimpleDateFormat("yyyyMMddhhmmss"); // add S if you need milliseconds
//			  //newFilename = fileUploadFileName + df.format(new Date());
//			  newFilename = fileUploadFileName.subSequence(0, fileUploadFileName.lastIndexOf("."))+df.format(new Date())+fileUploadFileName.substring(fileUploadFileName.lastIndexOf("."));
//			  
//			  File destinationFile = new File(tempDirecory + newFilename);
//              try{
//					/*
//					 * if (destinationFile.exists()){ int revisionCount =
//					 * reportProvider.getReportTemplate(reportFileFileName).getRevisionCount(); File
//					 * versionedFile = new File(directoryProvider.getReportDirectory() +
//					 * reportFileFileName + "." + revisionCount);
//					 * FileUtils.copyFile(destinationFile, versionedFile); }
//					 */
//            	FileUtils.copyFile(fileUpload, destinationFile);
//            	setDownloadFileName(fileUploadFileName);
//            	setDownloadPath(tempDirecory + newFilename);
//            	
//            	//clear upload fields. Otherwise try to upload again
//            	setFileUpload(null);
//        		setFileUploadContentType(null);
//        		//setFileUploadFileName(null); this is not clear. bcoz file name need for save
//             } 
//             catch(IOException e){
//            	 throw new BaseException("Please upload File again.");
//				//throw new BaseException(e.getMessage());
//		     }
//			}else{
//				System.out.println("Invalid file");
//				//throw new BaseException("Invalid File.");
//          }
		return newFilename;
	}

	public String update() {
		try {
			if (getCommand().equals("update") && getSourceDocumentId() > 0) {
				//sourceDocument = getSourceDocumentDAO().getSourceDocumentById(getSourceDocumentId());
				sourceDocument = getSourceDocumentDAO().getSourceDocumentAllDataById(getSourceDocumentId(), userSession);
				sourceDocTypeList = SourceDocTypeEnum.getAllSourceDocTypes();
				approvalTypeList = ApprovalTypeEnum.getAllApprovalTypes();
				tempSourceApprovalList = getSourceDocumentDAO().getTempSourceDocApprovals(userSession.getUserId());
				
				stakeholderList = getStakeholderDAO().getAllStakeholder();
				for(Stakeholder st : stakeholderList) {
					stakeholderMap.put(st.getStakeholderId()+"", st.getFullName());
				}
				checksId = null;
				setSourceDocType(sourceDocument.getDocumentType());
				setReferenceNo(sourceDocument.getReferenceNo());
				setDocumentDate(sourceDocument.getDocumentDate());
				setStakeholderId(sourceDocument.getStakeholderId());
				setDescription(sourceDocument.getDescription());
				setRemark(sourceDocument.getRemark());
				setNetAmount(sourceDocument.getNetAmount()+"");
				setGrossAmount(sourceDocument.getGrossAmount()+"");
				setVatAmount(sourceDocument.getVatAmount()+"");
				setVatPercentage(sourceDocument.getVatPercentage());
				setOtherTaxAmount(sourceDocument.getOtherTaxAmount()+"");
  			    setStatus(sourceDocument.getStatus());
  			    setIsPRUsed(sourceDocument.getIsPRUsed());//new
  			  
  			    if(sourceDocument.getDocumentUpload()!=null) {
  			    	setDownloadFileName(sourceDocument.getDocumentUpload().getFileName());
  			    	setDownloadPath(sourceDocument.getDocumentUpload().getDocPath());
  			    }else {
  			    	setDownloadFileName(null);
  			    	setDownloadPath(null);
  			    }
  			    setAppliedAmount(0.0);//other field are cleared
				return INPUT;
				
			} else if (getCommand().equals("save")) {
				
				sourceDocument.setDocumentType(getSourceDocType());
				sourceDocument.setReferenceNo(getReferenceNo());
				sourceDocument.setDocumentDate(getDocumentDate());
				sourceDocument.setStakeholderId(getStakeholderId());
				sourceDocument.setDescription(getDescription());
				sourceDocument.setRemark(getRemark());
				sourceDocument.setNetAmount(getNetAmount());
				sourceDocument.setVatAmount(getVatAmount());
				sourceDocument.setVatPercentage(getVatPercentage());
				sourceDocument.setOtherTaxAmount(getOtherTaxAmount());
				sourceDocument.setGrossAmount(getNetAmount()+getVatAmount()+getOtherTaxAmount());
				sourceDocument.setStatus(getStatus());
				
				DocumentUpload documentUpload = null;
				if(newUploadFileName!=null) {
					documentUpload = new DocumentUpload();
					documentUpload.setFileName(fileUploadFileName);
					documentUpload.setRenameFileName(newUploadFileName);
					documentUpload.setDocPath(attachmentDirecory + newUploadFileName);
					documentUpload.setTempDocPath(tempDirecory+newUploadFileName);
					documentUpload.setContentType(getFileUploadContentType());
				}
				clearErrorsAndMessages();
				
				try {
					getSourceDocumentDAO().updateSourceDocument(sourceDocument,userSession,getChecksId(),documentUpload);
					addActionMessage(getText("message.updateSuccess"));
					return SUCCESS;
				} catch (Exception e) {
					setCommand("update");
					addActionMessage(getText(e.getMessage()));
					return INPUT;				
				}
				
			}
		} catch (BaseException e) {
			addActionMessage(getText(e.getMessage()));
			return INPUT;
		}
		return SUCCESS;
	}

	public String delete() {
		try {
			clearErrorsAndMessages();
			if (getSourceDocumentId() > 0) {
				getSourceDocumentDAO().deleteSourceDocument(getSourceDocumentId(), userSession);
				addActionMessage(getText("message.deleteSuccess"));
			}
		} catch (BaseException e) {
			addActionMessage(getText(e.getMessage()));
			return INPUT;
		}
		return SUCCESS;
	}

	public void validate() {
		clearFieldErrors();
		clearActionErrors();
		boolean error = false;

		if (getCommand() != null && getCommand().equals("save") && !isClearScreen() && !isDelete() && !isAddTemp() && isCreate()) {			
			String[] arg = new String[1];

			 if (getSourceDocType() == null || getSourceDocType().trim().equals("")) {
		    	arg[0] = getText("label.documentType");
		        addFieldError("sourceDocType", getText("errors.required",arg));
		        error = true;
			}
			if (getReferenceNo() == null || getReferenceNo().trim().equals("")) {
				arg[0] = getText("label.refNo");
				addFieldError("referenceNo", getText("errors.required", arg));
				error = true;
			}
		    if (getStakeholderId()==0) {
		    	arg[0] = getText("label.stakeholder");
		    	addFieldError("stakeholderId", getText("errors.required",arg));
		    	error = true;
		    }
			if (getDescription() == null || getDescription().trim().equals("")) {
				arg[0] = getText("label.description");
				addFieldError("description", getText("errors.required", arg));
				error = true;
			}
			if (getNetAmount()<=0.00) {
				arg[0] = getText("label.netAmount");
				addFieldError("netAmount", getText("errors.double", arg));
				error = true;
			}
			if(tempSourceApprovalList.size()<=0) {
				addFieldError("approvalDetail", getText("errors.approvalsRequired"));
				error = true;
			}
			
			//upload file to temp location
			if(fileUploadFileName!=null) {
				if(!validateUploadFile())
					error = true;
//				 ServletActionContext.getServletContext().getRealPath("/");
//				try {
//					//String extension = Files.getFileExtension(fileUploadFileName);
//					newUploadFileName = uploadToTemp();
//				} catch (BaseException e) {
//					addFieldError("fileUpload", getText(e.getMessage()));
//					e.printStackTrace();
//				}
			}
			
			if (error && getSourceDocumentId() > 0)
				setCommand("update");
			if (error && getSourceDocumentId() <= 0) {
				setCommand("add");
			}
	
		}
	}
	
	public boolean validateUploadFile() {
		ServletActionContext.getServletContext().getRealPath("/");
		if(fileUploadFileName.endsWith(".pdf") || fileUploadFileName.endsWith(".jpg") || fileUploadFileName.endsWith(".jpeg") ||  fileUploadFileName.endsWith(".png")) {
			try {
				newUploadFileName = uploadToTemp();
				return true;
			} catch (BaseException e) {
				addFieldError("fileUpload", getText(e.getMessage()));
				e.printStackTrace();
			}
		}else {
			addFieldError("fileUpload", "Invalid File");
		}
		fileUpload = null;
		fileUploadContentType= null;
		fileUploadFileName= null;
		return false;
	}

	public void clearFields() {
		setSourceDocumentId(0);
		setSourceDocType("0");
		setReferenceNo(null);
		setDocumentDate(new Date());
		setDescription(null);
		setStatus("A");
		setNetAmount("0.00");
		setGrossAmount("0.00");
		setVatAmount("0.00");
		setVatPercentage(0.00);
		setOtherTaxAmount("0.00");
		setRemark(null);
		setChecksId(null);
		setIsPRUsed("N");
		
		//clear upload part
		setFileUpload(null);
		setFileUploadContentType(null);
		setFileUploadFileName(null);
		setDownloadFileName(null);
		setNewUploadFileName(null);
		
		setStakeholderId(0);
		setAppliedAmount(0.00);//others are cleared
		
//		setNetAmount("0.00");
//		setGrossAmount(0.00);
//		setVatAmount(0.00);
//		setVatPercentage(0.00);
	}

	public String reset() {
		clearErrorsAndMessages();
		String out = null;

		if (getSourceDocumentId() > 0) {
			setCommand("update");
			out = update();
		} else {
			setCommand("add");
			out = create();
		}
		return out;
	}
	
	public void loadDropDownData() throws BaseException {
		approvalTypeList = ApprovalTypeEnum.getAllApprovalTypes();
		if(approvalType!=null)
			approvalDocumentList = getApprovalDocumentDAO().getApprovalDocByApprovalType(approvalType, userSession);
	}

	private Map<String, String> params = new HashMap<String, String>();

	@Override
	public void addParam(String name, String value) {
	}

	@Override
	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	@Override
	public Map<String, String> getParams() {
		return null;
	}

	boolean isClearScreen() {
		if (this.params.get("clear") != null && this.params.get("clear").equals("YES")) {
			return true;
		}
		return false;
	}
	
	boolean isDelete() {
		if (this.params.get("delete") != null && this.params.get("delete").equals("YES")) {
			return true;
		}
		return false;
	}
	
	boolean isAddTemp() {
		if (this.params.get("addTemp") != null && this.params.get("addTemp").equals("YES")) {
			return true;
		}
		return false;
	}
	
	boolean isCreate() {
		if (this.params.get("createsrc") != null && this.params.get("createsrc").equals("YES")) {
			return true;
		}
		return false;
	}

	public String addSourceDocApproval(){
		String[] arg = new String[1];
		boolean error = false;
		if (getApprovalType()==null) {
	    	arg[0] = getText("label.approvalType");
	        addFieldError("approvalType", getText("errors.required",arg));
	        error = true;
		}
		if (getApprovalDocId() == null || getApprovalDocId()<=0) {
			arg[0] = getText("label.refNo");
			addFieldError("referenceNo", getText("errors.required", arg));
			error = true;
		}
		if(getAppliedAmount()<=0) {
			arg[0] = getText("label.appliedAmount");
			addFieldError("appliedAmount", getText("errors.double", arg));
		}
		if(!error) {
			TempSourceDocApprovals tempSourceDocApprovals = new TempSourceDocApprovals();
			tempSourceDocApprovals.setApprovalType(getApprovalType());
			tempSourceDocApprovals.setApprovalDocId(getApprovalDocId());
			tempSourceDocApprovals.setAppliedAmount(getAppliedAmount());
			try {
				getSourceDocumentDAO().createTempSourceApprovals(tempSourceDocApprovals, userSession);
				tempSourceApprovalList = getSourceDocumentDAO().getTempSourceDocApprovals(userSession.getUserId());
			} catch (BaseException e) {
				addActionMessage(getText(e.getMessage()));
				//e.printStackTrace();
			}
		}
		if(getSourceDocumentId()>0)
			setCommand("update");
		else
			setCommand("add");
		return INPUT;
	}
	
	public String download(){
		if(getDownloadPath()!=null) {
	        try {
				fileInputStream = new FileInputStream(new File(getDownloadPath()));
			} catch (FileNotFoundException e) {
				addActionMessage("Error : Can't download.");
				e.printStackTrace();
			}
		}
		return SUCCESS;
    }
	
	public String deleteApprovals() {
		try {
			if(checksId!=null) {
				for(String id : checksId) {
					setTempSourceDocAppId(Integer.parseInt(id));
					getSourceDocumentDAO().deleteTempSourceDocAppById(getTempSourceDocAppId(), userSession);
				}
//				setFullAmount(0.00);
//				setBalanceAmount(0.00);
//				setAppliedAmount(0.00);
				tempSourceApprovalList = getSourceDocumentDAO().getTempSourceDocApprovals(userSession.getUserId());
			}
			if(getSourceDocumentId()>0)
				setCommand("update");
			else
				setCommand("add");
		}catch (BaseException e) {
			addActionMessage(getText(e.getMessage()));
			return INPUT;		
		}
		return INPUT;
	}

}

