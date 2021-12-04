package com.finance.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.finance.dao.ApprovalDocumentDAO;
import com.finance.domain.ApprovalDocument;
import com.finance.domain.UserSession;
import com.finance.enumeration.ApprovalTypeEnum;
import com.finance.util.BaseException;
import com.finance.util.SessionUtil;
import com.opensymphony.xwork2.config.entities.Parameterizable;

public class ApprovalDocAction extends BaseAction implements Parameterizable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	ApprovalDocument approvalDocument = null;
	private List<ApprovalDocument> list;
	private ApprovalDocumentDAO approvalDocumentDAO;
	private String command;
	private List<ApprovalTypeEnum> approvalTypeList;
	private UserSession userSession;

	private int approvalDocId;
	private String docName;
	private String description;
	private String referenceNo;
	private Date approvedDate;
	// private String isPartPayment;
	private double approvedFullAmount;
	private double balanceAmount;
	private String approvalType;
	private String status;
	private boolean partPayment;
	private double utilizedAmount;

	@Override
	public String execute() throws Exception {
		try {
			
			if (!SessionUtil.validateSession(getHttpServletRequest())){
				addActionError(getText("errors.sessionExpired"));
	            return ERROR;
	        }
			
			clearErrorsAndMessages();
			userSession = (UserSession) getHttpServletRequest().getSession().getAttribute("LOGIN_USER");
//			clearFieldErrors();
			approvalDocument = new ApprovalDocument();
			list = getApprovalDocumentDAO().getAllApprovalDocument(userSession);
			approvalTypeList = ApprovalTypeEnum.getAllApprovalTypes();
			return SUCCESS;
		} catch (BaseException e) {
			//addActionError(e.toString());
			addActionMessage(getText(e.getMessage()));
			return INPUT;
		}
	}

	public String refresh() throws Exception {// return after create,update,delete
		try {
			list = getApprovalDocumentDAO().getAllApprovalDocument(userSession);
			approvalTypeList = ApprovalTypeEnum.getAllApprovalTypes();
			return SUCCESS;
		} catch (BaseException e) {
			addActionMessage(e.toString());
		}
		return SUCCESS;
	}

	public String create() {
		if (getCommand().equals("add")) {
			clearFields();
			approvalDocument = new ApprovalDocument();
			approvalTypeList = ApprovalTypeEnum.getAllApprovalTypes();
			return INPUT;
		} else {
			approvalDocument = new ApprovalDocument();
			approvalDocument.setApprovalType(getApprovalType());
			approvalDocument.setDocName(ApprovalTypeEnum.getEnumByApprovalType(getApprovalType()).getDescription());
			approvalDocument.setDescription(getDescription());
			approvalDocument.setApprovedDate(getApprovedDate());
			approvalDocument.setReferenceNo(getReferenceNo());
			approvalDocument.setBalanceAmount(getApprovedFullAmount());
			approvalDocument.setUtilizedAmount(0.00);
			approvalDocument.setIsPartPayment(isPartPayment() ? "Y" : "N");
			approvalDocument.setApprovedFullAmount(getApprovedFullAmount());
			clearErrorsAndMessages();
			try {
				getApprovalDocumentDAO().createApprovalDocument(approvalDocument,userSession);
			} catch (BaseException e) {
				setCommand("add");
				addActionMessage(getText(e.getMessage()));
				return INPUT;
			}
			addActionMessage(getText("message.addSuccess"));
			return SUCCESS;
		}

	}

	public String update() {
		try {
			if (getCommand().equals("update") && getApprovalDocId() > 0) {
				approvalTypeList = ApprovalTypeEnum.getAllApprovalTypes();
				approvalDocument = getApprovalDocumentDAO().getApprovalDocumentById(getApprovalDocId());
				setApprovalDocId(approvalDocument.getApprovalDocId());
				setApprovalType(approvalDocument.getApprovalType());
				setDocName(approvalDocument.getDocName());
				setDescription(approvalDocument.getDescription());
				setReferenceNo(approvalDocument.getReferenceNo());
				setApprovedDate(approvalDocument.getApprovedDate());
				setApprovedFullAmount(approvalDocument.getApprovedFullAmount()+"");
				setBalanceAmount(approvalDocument.getBalanceAmount());
				setUtilizedAmount(approvalDocument.getUtilizedAmount()+"");
				setPartPayment(approvalDocument.getIsPartPayment().equals("Y") ? true : false);
				setStatus(approvalDocument.getStatus());
				return INPUT;
				
			} else if (getCommand().equals("save")) {
				if (getApprovedFullAmount() < getUtilizedAmount()) {
					addFieldError("utilizedAmount","Approved amount can't be less than utilized amount.");
					setCommand("update");
					return INPUT;
				}
				approvalDocument = getApprovalDocumentDAO().getApprovalDocumentById(getApprovalDocId());
				approvalDocument.setApprovalType(getApprovalType());
				approvalDocument.setDocName(ApprovalTypeEnum.getEnumByApprovalType(getApprovalType()).getDescription());
				approvalDocument.setDescription(getDescription());
				approvalDocument.setReferenceNo(getReferenceNo());
				approvalDocument.setApprovedDate(getApprovedDate());
				approvalDocument.setApprovedFullAmount(getApprovedFullAmount());
				approvalDocument.setBalanceAmount(getApprovedFullAmount()-getUtilizedAmount());
				approvalDocument.setIsPartPayment(isPartPayment() ? "Y" : "N");
				approvalDocument.setStatus(getStatus());
				approvalDocument.setUtilizedAmount(getUtilizedAmount());
				
				try {
					getApprovalDocumentDAO().updateApprovalDocument(approvalDocument, userSession);
					clearErrorsAndMessages();
					addActionMessage(getText("message.updateSuccess"));
					return SUCCESS;
				}catch(BaseException e) {
					setCommand("update");
					addActionMessage(getText(e.getMessage()));
					return INPUT;
				}
			
			}
		} catch (BaseException e) {
			addActionMessage(getText(e.getMessage()));
			//addActionError(e.toString());
			return INPUT;
		}
		return SUCCESS;

	}

	public String delete() {
		try {
			clearErrorsAndMessages();
			if (getApprovalDocId() > 0) {
				getApprovalDocumentDAO().deleteApprovalDocument(getApprovalDocId(),userSession);
				addActionMessage(getText("message.deleteSuccess"));
//				 if((((ActionSupport)ActionContext.getContext().getActionInvocation().getAction()).getActionMessages()).toString().contains("SUCCESS")){
//				   System.out.println(((ActionSupport)ActionContext.getContext().getActionInvocation().getAction()).getActionMessages());
//				 }

			}
		} catch (BaseException e) {
			addActionMessage(getText(e.getMessage()));
			//addActionError(getText(e.getMessage()));
			return INPUT;//input
		}
		return SUCCESS;
	}

	public void validate() {
		clearFieldErrors();
		clearActionErrors();//remove commented pl check
		boolean error = false;

		if (getCommand() != null && getCommand().equals("save") && !isClearScreen()) {			
			String[] arg = new String[1];

			if (getApprovalType().equals("-1")) {
				arg[0] = getText("label.approvalType");
				addFieldError("approvalType", getText("errors.required", arg));
				error = true;
			}
			if (getReferenceNo() == null || getReferenceNo().trim().equals("")) {
				arg[0] = getText("label.refNo");
				addFieldError("referenceNo", getText("errors.required", arg));
				error = true;
			}
			if (getApprovedFullAmount() <= 0.0) {
				arg[0] = getText("label.fullAmount");
				addFieldError("approvedFullAmount", getText("errors.double", arg));
				error = true;
			}			 
			
			if (error && getApprovalDocId() > 0)
				setCommand("update");
			if (error && getApprovalDocId() <= 0)
				setCommand("add");
		}
	}

	public void clearFields() {
		setApprovalDocId(0);
		setDocName(null);
		setDescription(null);
		setStatus("A");
		setApprovedDate(null);
		setReferenceNo(null);
		setApprovedFullAmount("0.0");
		setBalanceAmount(0.0);
		setPartPayment(false);
	}

	public String reset() {
		clearErrorsAndMessages();
		String out = null;

		if (getApprovalDocId() > 0) {
			setCommand("update");
			out = update();
		} else {
			setCommand("add");
			out = create();
		}
		return out;
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

	public List<ApprovalDocument> getList() {
		return list;
	}

	public void setList(List<ApprovalDocument> list) {
		this.list = list;
	}

	public int getApprovalDocId() {
		return approvalDocId;
	}

	public void setApprovalDocId(int approvalDocId) {
		this.approvalDocId = approvalDocId;
	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}

	public Date getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}

//	public String getIsPartPayment() {
//		return isPartPayment;
//	}
//	public void setIsPartPayment(String isPartPayment) {
//		this.isPartPayment = isPartPayment;
//	}

	public double getApprovedFullAmount() {
		return approvedFullAmount;
	}

	public void setApprovedFullAmount(String approvedFullAmount) {
		this.approvedFullAmount =  Double.parseDouble(approvedFullAmount.replace(",", ""));
	}

	public double getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(double balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ApprovalDocumentDAO getApprovalDocumentDAO() {
		return approvalDocumentDAO;
	}

	public void setApprovalDocumentDAO(ApprovalDocumentDAO approvalDocumentDAO) {
		this.approvalDocumentDAO = approvalDocumentDAO;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
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

	public boolean isPartPayment() {
		return partPayment;
	}

	public void setPartPayment(boolean partPayment) {
		this.partPayment = partPayment;
	}

	public double getUtilizedAmount() {
		return utilizedAmount;
	}

	public void setUtilizedAmount(String utilizedAmount) {
		this.utilizedAmount =  Double.parseDouble(utilizedAmount.replace(",", ""));
	}

	public UserSession getUserSession() {
		return userSession;
	}

	public void setUserSession(UserSession userSession) {
		this.userSession = userSession;
	}
}
