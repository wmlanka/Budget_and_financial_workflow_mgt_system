package com.finance.dao;

import java.util.List;

import com.finance.domain.Location;
import com.finance.domain.UserSession;
import com.finance.util.BaseException;

public interface LocationDAO {
	public List<Location> getAllLocation(String status) throws BaseException;
	public Location getLocationById(int locationId) throws BaseException;
	public List<Location> getAllLocationByUser(String status, UserSession userSession) throws BaseException;

}
