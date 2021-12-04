package com.finance.daoImpl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;

import com.finance.dao.BaseDAOSupport;
import com.finance.dao.DashboardDAO;
import com.finance.domain.Location;
import com.finance.domain.PRStatus;
import com.finance.domain.SourceDocument;
import com.finance.domain.UserSession;
import com.finance.dto.BudgetDTO;
import com.finance.dto.PaymentRequestDTO;
import com.finance.enumeration.BudgetTypeEnum;
import com.finance.util.BaseException;

public class DashboardDAOImpl extends BaseDAOSupport implements DashboardDAO{
	@Override
	@Transactional
	public int getApprovedPaymentsToday(UserSession userSession) throws BaseException{//Today approved payments on user department
		try {
			//String SQL_QUERY =" from PR as ps where ps.statusa='APPROVED' and ps.createdDate=curdate()";
		
			return 0;
		} catch (Exception e) {
			throw new BaseException(e.getMessage());
		}	
	}
	
	@Override
	@Transactional
	public int getRejectedPaymentsToday(UserSession userSession) throws BaseException{//Total Rejected payments in the department
		try {
			//String SQL_QUERY =" from PRStatus as ps where ps.status='REJECTED' and ps.createdDate=curdate()";
			String SQL_QUERY =" from PaymentRequest as pr where "
					+ " pr.prfStatus='REJECTED' ";
			Query query = getSession().createQuery(SQL_QUERY);
			List<PRStatus> list = query.list();
			return list.size();
		} catch (Exception e) {
			throw new BaseException(e.getMessage());
		}	
	}
	
	@Override
	@Transactional
	public int getTotalPendingPayments(UserSession userSession) throws BaseException{
		try {
//			String SQL_QUERY =" from PRStatus as ps where ps.status='PENDING' and "+

			
			String SQL_QUERY =" from PaymentRequest as pr where "
					+ " pr.prfStatus='PENDING' ";
			
			Query query = getSession().createQuery(SQL_QUERY);
			List<PRStatus> list = query.list();
			return list.size();
		} catch (Exception e) {
			throw new BaseException(e.getMessage());
		}	
	}
	
	@Override
	@Transactional
	public int getTotalPendingSourceDocs(UserSession userSession) throws BaseException{
		try {
			String SQL_QUERY =" from SourceDocument as sd where sd.status='A' and sd.isPRUsed='N' ";
			Query query = getSession().createQuery(SQL_QUERY);
			List<SourceDocument> list = query.list();
			return list.size();
		} catch (Exception e) {
			throw new BaseException(e.getMessage());
		}	
	}
	
	@Override
	@Transactional
	public Double getRequestedPaymentsToday(UserSession userSession) throws BaseException{
		try {
			//String SQL_QUERY2 ="select sum(pr.netAmount) from PaymentRequest pr where pr.prfStatus!='CANCELED' and pr.prDate=curdate()";
			/*String sql ="select pr.prNumber, current_date from PaymentRequest pr where pr.prfStatus!='CANCELED' and pr.prDate=current_date";
			Query queryy = getSession().createQuery(sql);
			List<Object[]> list = queryy.list();
			for(Object[] o :list) {
				System.out.print(o[0]+"-"+o[1]);
			}*/
			
			String SQL_QUERY ="select sum(pr.netAmount) from PaymentRequest pr where pr.prfStatus!='CANCELED' and pr.prfStatus!='DRAFT' and pr.prDate=current_date and pr.departmentId="+userSession.getDepartmentId();

			Query query = getSession().createQuery(SQL_QUERY);
			Double requestedAmt = (Double) query.uniqueResult();
			return requestedAmt;
		} catch (Exception e) {
			throw new BaseException(e.getMessage());
		}	
	}
	
	@Override
	@Transactional
	public List<BudgetDTO> getTotalUtilizedOfBudgetType(UserSession userSession) throws BaseException{
		try {
			String SQL_QUERY ="select sum(b.utilizedAmount) as totalAmount, budgetType from Budget b where b.year='"
						+ Calendar.getInstance().get(Calendar.YEAR)+
						"' group by budgetType";
			
			Query query = getSession().createQuery(SQL_QUERY);
			List<Object[]> list = query.list();
			List<BudgetDTO> budgetTypeDataList = new ArrayList<BudgetDTO>();
			boolean isCapitalEx = false;
			boolean isMarkEx = false;
			boolean isITEx = false;
			boolean isRetailEx = false;
			
			for(Object[] o :list) {
				BudgetDTO budgetDTO = new BudgetDTO();
				
				if(o[1].toString().equals(BudgetTypeEnum.CAPITAL_EXPENSES.getBudgetType())) {
					budgetDTO.setBudgetType(BudgetTypeEnum.CAPITAL_EXPENSES.getDescription());
					budgetDTO.setUtilizedAmount(Double.parseDouble(o[0].toString()));
					isCapitalEx = true;
					
				}else if(o[1].toString().equals(BudgetTypeEnum.MARKETING_EXPENSES.getBudgetType())) {
					budgetDTO.setBudgetType("Marketing Exp");//BudgetTypeEnum.MARKETING_EXPENSES.getDescription()
					budgetDTO.setUtilizedAmount(Double.parseDouble(o[0].toString()));
					isMarkEx = true;
					
				}else if(o[1].toString().equals(BudgetTypeEnum.IT_EXPENSES.getBudgetType())) {
					budgetDTO.setBudgetType(BudgetTypeEnum.IT_EXPENSES.getDescription());
					budgetDTO.setUtilizedAmount(Double.parseDouble(o[0].toString()));
					isITEx = true;
					
				}else if(o[1].toString().equals(BudgetTypeEnum.RETAIL_BANKING_EXPENSES.getBudgetType())) {
					budgetDTO.setBudgetType("Retail Banking");
					budgetDTO.setUtilizedAmount(Double.parseDouble(o[0].toString()));
					isRetailEx = true;
					
				}
				budgetTypeDataList.add(budgetDTO);
			}
			 
			if(!isCapitalEx) {
				BudgetDTO budgetDTO = new BudgetDTO();
				budgetDTO.setBudgetType(BudgetTypeEnum.CAPITAL_EXPENSES.getDescription());
				budgetDTO.setUtilizedAmount(0.00);
				budgetTypeDataList.add(budgetDTO);
			}
			if(!isMarkEx) {
				BudgetDTO budgetDTO = new BudgetDTO();
				budgetDTO.setBudgetType("Marketing Exp");//BudgetTypeEnum.MARKETING_EXPENSES.getDescription()
				budgetDTO.setUtilizedAmount(0.00);
				budgetTypeDataList.add(budgetDTO);
			}
			if(!isITEx) {
				BudgetDTO budgetDTO = new BudgetDTO();
				budgetDTO.setBudgetType(BudgetTypeEnum.IT_EXPENSES.getDescription());
				budgetDTO.setUtilizedAmount(0.00);
				budgetTypeDataList.add(budgetDTO);
			}
			if(!isRetailEx) {
				BudgetDTO budgetDTO = new BudgetDTO();
				budgetDTO.setBudgetType("Retail Banking");
				budgetDTO.setUtilizedAmount(0.00);
				budgetTypeDataList.add(budgetDTO);
			}
			
			return budgetTypeDataList;
		} catch (Exception e) {
			throw new BaseException(e.getMessage());
		}	
	}
	
	@Override
	@Transactional
	public List<PaymentRequestDTO> getTotalPRByDepartments(UserSession userSession) throws BaseException{//current month
		try {
			//String SQL_QUERY ="select pr.departmentId, count(*) from PaymentRequest pr where pr.prfStatus!='CANCELED'  and pr.prfStatus!='DRAFT' and pr.prDate=curdate() group by pr.departmentId";
			
			String SQL_QUERY ="select pr.departmentId, count(*) from PaymentRequest pr where  month(pr.prDate)= month(current_date) group by pr.departmentId";

			//and pr.prfStatus!='DRAFT'
			Query query = getSession().createQuery(SQL_QUERY);
			List<Object[]> list = query.list();
			List<PaymentRequestDTO> dtoList = new ArrayList<PaymentRequestDTO>();
			for(Object[] o :list) {
				PaymentRequestDTO paymentRequestDTO = new PaymentRequestDTO();
				Location location = (Location) getSession().load(Location.class, Integer.parseInt(o[0].toString()));
				paymentRequestDTO.setCostCenter(location.getLocationId()+"");
				paymentRequestDTO.setPrCount(Integer.parseInt(o[1].toString()));
				dtoList.add(paymentRequestDTO);
			}
			return dtoList;
		} catch (Exception e) {
			throw new BaseException(e.getMessage());
		}
	}
}
