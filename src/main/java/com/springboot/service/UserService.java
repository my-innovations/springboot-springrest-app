package com.springboot.service;

import java.util.List;

import com.springboot.model.User;

public interface UserService {
	
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
	public User getAllUsersByEmail(String email);
	public List<User> getAllUsers();
	
	
	/**
	 * ################################
	 * UPDATE Operation
	 * ################################
	 */
	public User updateUser(User user);
	public User updateUserByUserId(Integer userId,User user);
	public User updateUserByEmailID(String emailID,User user);
	
	/**
	 * ################################
	 * DELETE Operation
	 * ################################
	 */
	public void deleteUserByUserId(Integer userId);
	public void deleteUserByEmailID(String emailID);
	public void deleteAllUsers();
}
