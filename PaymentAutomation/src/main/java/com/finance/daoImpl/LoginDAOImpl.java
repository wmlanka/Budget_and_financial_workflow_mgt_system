package com.finance.daoImpl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;

import com.finance.dao.BaseDAOSupport;
import com.finance.dao.LoginDAO;
import com.finance.domain.AccessGroup;
import com.finance.domain.User;
import com.finance.domain.UserSession;
import com.finance.util.BaseException;

public class LoginDAOImpl extends BaseDAOSupport implements LoginDAO{

	@Override
	@Transactional
	public UserSession createLogin(User user) throws BaseException {
		
//		String password = user.getPassword();
//		MessageDigest messageDigest = MessageDigest.getInstance("MD5");
//		messageDigest.update(password.getBytes(), 0, password.length());
//		String passwardMd5 = new BigInteger(1 , messageDigest.digest()).toString();
		
		Session session = null;
		UserSession userSession = null;
		
		try {
				userSession = new UserSession();		
				session = getSession();
		
				String SQL_QUERY =" from User as u where u.userId=? and u.password=? and u.status='A'";
				Query query = session.createQuery(SQL_QUERY);
				query.setParameter(0,user.getUserId());
			    query.setParameter(1,user.getPassword());
				List<User> list = query.list();
				//session.close();
				
				if ((list != null) && (list.size() == 1)) {
					User ob = list.get(0);
					AccessGroup accessGroup = (AccessGroup) getSession().load(AccessGroup.class, ob.getAccessGroupId());
					userSession.setUserName((list.get(0)).getUserName());
					userSession.setUserType(accessGroup.getCode());	
					userSession.setDepartmentId((list.get(0)).getDepartmentd());
					userSession.setUserId((list.get(0)).getUserId());
				}else {
					//session.close();
					throw new BaseException("error.invalidUserPassword");
				}
		}catch (Exception e) {			
			//session.close();
			//throw new BaseException("error.loginError");
			throw new BaseException(e);
		}finally{
			//session.close();
		}
		return userSession;
	}
	

}
