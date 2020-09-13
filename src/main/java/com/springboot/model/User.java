package com.springboot.model;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
//@XmlRootElement
public class User implements Serializable {

	private static final long serialVersionUID = 5318515045401387064L;

	// @XmlElement
	private Integer userId;

	@ApiModelProperty(value = "User firstname", required = true)
	@JsonProperty(value = "firstName")
	// @XmlElement
	@NotEmpty(message = "{NotEmpty.user.firstname}")
	// @NotEmpty(message = "please provide firstname") //OK
	private String firstname;

	// @NotEmpty(message = "please provide lastname")//OK
	@ApiModelProperty(value = "User lastname")
	@JsonProperty(value = "lastName")
	// @XmlElement
	@NotEmpty(message = "{NotEmpty.user.lastname}")
	private String lastname;

	@ApiModelProperty(value = "User Email", required = true)
	@JsonProperty(value = "email")
	// @XmlElement
	@Email(message = "{Invalid.user.email}")
	// @Email(message = "please provide valid email address")//OK
	private String email;

	private List<String> contactNos;

	private Long sal;

	/**
	 * ####################################################################################
	 * Constructors
	 * ####################################################################################
	 */

	public User() {
	}

	public User(Integer userId, @NotEmpty(message = "{NotEmpty.user.firstname}") String firstname,
			@NotEmpty(message = "{NotEmpty.user.lastname}") String lastname,
			@Email(message = "{Invalid.user.email}") String email) {
		super();
		this.userId = userId;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
	}

	public User(@NotEmpty(message = "{NotEmpty.user.firstname}") String firstname,
			@NotEmpty(message = "{NotEmpty.user.lastname}") String lastname,
			@Email(message = "{Invalid.user.email}") String email) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
	}

	/**
	 * ####################################################################################
	 * getters and setters
	 * ####################################################################################
	 */
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<String> getContactNos() {
		return contactNos;
	}

	public void setContactNos(List<String> contactNos) {
		this.contactNos = contactNos;
	}

	public Long getSal() {
		return sal;
	}

	public void setSal(Long sal) {
		this.sal = sal;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", firstname=" + firstname + ", lastname=" + lastname + ", email=" + email
				+ "]";
	}
}
