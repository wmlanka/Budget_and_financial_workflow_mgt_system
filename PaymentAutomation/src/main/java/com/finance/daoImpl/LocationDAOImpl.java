package com.finance.daoImpl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.finance.dao.BaseDAOSupport;
import com.finance.dao.LocationDAO;
import com.finance.domain.Location;
import com.finance.domain.UserSession;
import com.finance.enumeration.AccessLevelEnum;
import com.finance.util.BaseException;

public class LocationDAOImpl extends BaseDAOSupport implements LocationDAO{
	@Override
	@Transactional
	public List<Location> getAllLocation(String status) throws BaseException {
		Criteria criteria = getSession().createCriteria(Location.class);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		if(status!=null && !status.equals("")) {
			criteria.add(Restrictions.eq("status", status));
		}
		List<Location> list = (List<Location>) criteria.list();
		return list;		
	}
	
	@Override
	@Transactional
	public Location getLocationById(int locationId) throws BaseException {
		Location location = (Location) getSession().load(Location.class, locationId);
		return location;
	}
	
	@Override
	@Transactional
	public List<Location> getAllLocationByUser(String status, UserSession userSession) throws BaseException {
		Criteria criteria = getSession().createCriteria(Location.class);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		if(status!=null && !status.equals("")) {
			criteria.add(Restrictions.eq("status", status));
		}
		if(isDepartmentFilterReq(userSession)) {
			criteria.add(Restrictions.eq("locationId", userSession.getDepartmentId()));
		}
		List<Location> list = (List<Location>) criteria.list();
		return list;		
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
}
