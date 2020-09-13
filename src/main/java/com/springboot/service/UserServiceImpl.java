package com.springboot.service;

import java.util.List;
//import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.dao.UserDao;
import com.springboot.exception.NoRecordsFoundException;
import com.springboot.exception.UserNotFoundException;
import com.springboot.model.User;

@Service
// @Transactional
public class UserServiceImpl implements UserService {

	private UserDao userDao = null;

	@Autowired
	public UserServiceImpl(UserDao userDao) {
		this.userDao = userDao;
	}

	/**
	 * ################################ INSERT Operation
	 * ################################
	 */

	@Override
	public User saveUser(User user) {
		// String userId = UUID.randomUUID().toString();
		// user.setUserId(userId);
		// throw new RuntimeException("hi exception raised");
		return userDao.saveUser(user);
	}

	/**
	 * ################################ SELECT Operation
	 * ################################
	 */

	@Override
	public User getUserByUserId(Integer userId) {
		User user = userDao.getUserByUserId(userId);
		if (null == user) {
			throw new UserNotFoundException("user not found with id: " + userId);
		}
		return user;
	}

	@Override
	public List<User> getAllUsersByFirstname(String firstname) {
		List<User> list = userDao.getAllUsersByFirstname(firstname);
		if (null != list && list.size() == 0) {
			throw new NoRecordsFoundException("No Record Found");
		}
		return list;
	}

	@Override
	public List<User> getAllUsersByLastname(String lastname) {
		List<User> list = userDao.getAllUsersByLastname(lastname);
		if (null != list && list.size() == 0) {
			throw new NoRecordsFoundException("No Record Found");
		}
		return list;
	}

	@Override
	public User getAllUsersByEmail(String email) {
		User user = userDao.getUserByEmail(email);
		if (null == user) {
			throw new NoRecordsFoundException("No Record Found");
		}
		return user;
	}

	@Override
	public List<User> getAllUsers() {
		List<User> list = userDao.getAllUsers();
		if (null != list && list.size() == 0) {
			throw new NoRecordsFoundException("No Record Found");
		}
		return list;
	}

	/**
	 * ################################ UPDATE Operation
	 * ################################
	 */

	@Override
	public User updateUser(User user) {
		User u = null;
		if (user.getUserId() != null) {
			u = userDao.getUserByUserId(user.getUserId());
			if (null == u) {
				throw new UserNotFoundException("user not found with id: " + user.getUserId());
			}
		}
		return userDao.updateUser(user);
	}

	@Override
	public User updateUserByUserId(Integer userId, User user) {
		return userDao.updateUserByUserId(userId, user);
	}

	@Override
	public User updateUserByEmailID(String emailID, User user) {
		return userDao.updateUserByEmailID(emailID, user);
	}

	/**
	 * ################################ DELETE Operation
	 * ################################
	 */

	@Override
	public void deleteUserByUserId(Integer userId) {
		User user = null;
		if (userId != null) {
			user = userDao.getUserByUserId(userId);
			if (user == null) {
				throw new UserNotFoundException("User id not found:" + userId);
			}
		}
		userDao.deleteUserByUserId(userId);
	}

	@Override
	public void deleteUserByEmailID(String emailID) {
		userDao.deleteUserByEmailID(emailID);
	}

	@Override
	public void deleteAllUsers() {
		userDao.deleteAllUsers();
	}
}
