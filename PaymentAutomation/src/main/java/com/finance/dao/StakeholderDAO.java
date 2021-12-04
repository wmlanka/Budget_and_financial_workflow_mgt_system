package com.finance.dao;

import java.util.List;

import com.finance.domain.Stakeholder;
import com.finance.domain.UserSession;
import com.finance.util.BaseException;

public interface StakeholderDAO {
	List<Stakeholder> getAllStakeholder() throws BaseException;
	Stakeholder getStakeholderById(int stakeholderId) throws BaseException;
	void deleteStakeholder(int stakeholderId, UserSession userSession) throws BaseException;
	void createStakeholder(Stakeholder stakeholder,UserSession userSession) throws BaseException;
	void updateStakeholder(Stakeholder stakeholder, UserSession userSession) throws BaseException;
	public List<Stakeholder> getAllActiveStakeholder() throws BaseException;
}
