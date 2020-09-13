package com.springboot.dao;

import java.util.List;

import com.springboot.model.User;

public interface UserDao {
	
	/**
	 * ################################
	 * INSERT Operation
	 * ################################
	 */
	public User saveUser(User user);
	
	/**
	 * ################################
	 * SELECT Operation
	 * ################################
	 */
	public User getUserByUserId(Integer userId);
	public List<User> getAllUsersByFirstname(String firstname);
	public List<User> getAllUsersByLastname(String lastname);
	public User getUserByEmail(String email);
	public List<User> getAllUsers();
	
	/**
	 * ################################
	 * UPDATE Operation
	 * ################################
	 */
	public User updateUserByUserId(Integer userId,User user);
	public User updateUserByEmailID(String emailID,User user);
	public User updateUser(User user);
	
	/**
	 * ################################
	 * DELETE Operation
	 * ################################
	 */
	public void deleteUserByUserId(Integer userId);
	public void deleteUserByEmailID(String emailID);
	public void deleteAllUsers();

}
