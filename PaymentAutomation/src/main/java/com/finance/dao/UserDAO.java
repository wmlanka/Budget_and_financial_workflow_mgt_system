package com.finance.dao;

import java.util.List;

import com.finance.dto.UserDTO;
import com.finance.util.BaseException;

public interface UserDAO {
	public List<UserDTO> getAllUserDTO() throws BaseException;

}
