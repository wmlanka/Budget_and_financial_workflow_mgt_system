package com.finance.daoImpl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;

import com.finance.dao.BaseDAOSupport;
import com.finance.dao.BudgetCodeDAO;
import com.finance.dao.UserDAO;
import com.finance.domain.AccessGroup;
import com.finance.domain.ActionCode;
import com.finance.domain.BudgetCode;
import com.finance.domain.Location;
import com.finance.domain.User;
import com.finance.dto.BudgetCodeDTO;
import com.finance.dto.UserDTO;
import com.finance.enumeration.AccessLevelEnum;
import com.finance.enumeration.BudgetTypeEnum;
import com.finance.util.BaseException;

public class UserDAOImpl extends BaseDAOSupport implements UserDAO{
	
	@Override
	@Transactional
	public List<UserDTO> getAllUserDTO() throws BaseException {
		List<User> list = null;
		List<UserDTO> listDTO = new ArrayList<UserDTO>();
		try {
			list = getSession().createCriteria(User.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
			for(User user : list) {
				UserDTO userDTO = new UserDTO();
				userDTO.setUserId(user.getUserId());
				userDTO.setUserName(user.getUserName());
				userDTO.setUserId(user.getUserId());
				userDTO.setStatus(user.getStatus());
				
				Location location = (Location) getSession().load(Location.class, user.getDepartmentd());
				userDTO.setCostCenter(location.getLocationName()+"-"+location.getLocationId());
				
				AccessGroup accessGroup = (AccessGroup) getSession().load(AccessGroup.class, user.getAccessGroupId());
				userDTO.setUserTypeCode(AccessLevelEnum.getEnumByCode(accessGroup.getCode()).getDescription());
				userDTO.setUserTypeDescr(accessGroup.getDescription());
	

				listDTO.add(userDTO);
			}
		} catch (HibernateException e) {
			throw new BaseException(e);
		} catch (Exception e) {
			throw new BaseException(e);
		}
		return listDTO;		
	}
}
