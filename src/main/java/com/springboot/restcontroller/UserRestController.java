package com.springboot.restcontroller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.model.User;
import com.springboot.service.UserService;

@RestController
@RequestMapping("/api/rest")//OK
public class UserRestController {

	//@Autowired //OK
	//@Inject //OK
	//@Resource //OK
	private UserService userService;

	// constructor autowiring (preferable)
	@Autowired
	//@Resource //OK
	//@Inject //OK
	public UserRestController(UserService userService) {
		this.userService = userService;
	}

	/**
	 * ###########################################################################################################################################################################################
	 * POST Operation 
	 * ###########################################################################################################################################################################################
	 * 
	 */
	//http://localhost:8081/api/rest/user
	//Accept:application/json
	//Content-Type:application/json
	//sample json payload:
	/*{
	    "firstName": "punyasmruti2",
	    "lastName": "nayak",
	    "email": "punyasmruti2@gmail.com"
	}*/

	@ResponseStatus(HttpStatus.CREATED)
	// @ResponseBody //OK , optional
	@PostMapping(value = "/user", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE) //OK
	// @PostMapping(value = "/user") //OK
	//@RequestMapping(value = "/user", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE) //OK
	public User saveUser1(@Valid @RequestBody User user) {
		return userService.saveUser(user);
	}//http://localhost:8081/api/rest/user

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/user2", consumes = MediaType.APPLICATION_JSON_VALUE,produces = { MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	//@RequestMapping(value = "/user2", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE) //OK
	public ResponseEntity<User> saveUser2(@Valid @RequestBody User user) {
		User u = userService.saveUser(user);
		//return new ResponseEntity<>(u, HttpStatus.CREATED); //OK
		return new ResponseEntity<>(u, new HttpHeaders(), HttpStatus.CREATED);
	}//http://localhost:8081/api/rest/user2

	// @ResponseBody //OK , optional
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/user3",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	//@RequestMapping(value = "/user/save3", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE) //OK
	public String saveUser3(@Valid @RequestBody User user) {
		return "User Ceated with Id: " + userService.saveUser(user).getUserId();
	}//http://localhost:8081/api/rest/user3

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/user4",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	//@RequestMapping(value = "/user4", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> saveUser4(@Valid @RequestBody User user) {
		User u = userService.saveUser(user);
		//return new ResponseEntity<>("User Ceated with Id: " + u.getUserId(), HttpStatus.CREATED);//OK
		return new ResponseEntity<>("User Ceated with Id: " + u.getUserId(), new HttpHeaders(), HttpStatus.CREATED);
	}//http://localhost:8081/api/user4

	/**
	 * ###########################################################################################################################################################################################
	 * GET Operation 
	 * ###########################################################################################################################################################################################
	 */

	//@ResponseBody //OK , optional
	@GetMapping(value = "/user/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	// @GetMapping("/getuser/{userId}") //OK
	// @RequestMapping(value="/getuser/{userid}",method=RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)//OK
	// @RequestMapping("/getuser/{userid}") //OK
	public User getUser1(@PathVariable("userId") Integer userId) {
		return userService.getUserByUserId(userId);
	}//http://localhost:8081/api/rest/user/1
	
	@GetMapping("/user2/{userId}")
	// @RequestMapping(value="/user2/{userid}",method=RequestMethod.GET)//OK
	public ResponseEntity<User> getUserDetails(@PathVariable("userId") Integer userId) {
		User user = userService.getUserByUserId(userId);
		//return new ResponseEntity<>(user, HttpStatus.OK);
		return new ResponseEntity<>(user, new HttpHeaders(), HttpStatus.OK);
	}//http://localhost:8081/api/rest/user2/1
	
	// @RequestMapping("/user/{firstname}") //OK
	// @RequestMapping(value = "/user/{userName}",method = RequestMethod.GET)
	@GetMapping("/userByFirstname/{firstname}")
	public List<User> getUserByFirstname(@PathVariable("firstname") String firstname) {
		return userService.getAllUsersByFirstname(firstname);
	}
	
	// @RequestMapping("/user/{firstname}") //OK
	// @RequestMapping(value = "/user/{userName}",method = RequestMethod.GET)
	@GetMapping("/userByLastname/{lastname}")
	public List<User> getUserByLastname(@PathVariable("lastname") String lastname) {
		return userService.getAllUsersByLastname(lastname);
	}
	
	// @RequestMapping("/userByEmailID/{emailID}") //OK
	// @RequestMapping(value = "/userByEmailID/{emailID}",method = RequestMethod.GET)
	@GetMapping("/userByEmailID/{emailID}")
	public User getUserByEmailID(@PathVariable("emailID") String emailID) {
		return userService.getAllUsersByEmail(emailID);
	}

	//@ResponseBody //OK , optional
	//@GetMapping(value="/users", produces = MediaType.APPLICATION_JSON_VALUE)
	//@GetMapping("/users")
	@RequestMapping(value="/users",method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE) //OK
	// @RequestMapping(value="/users",method=RequestMethod.GET)//OK
	// @RequestMapping(value = "/users") //OK
	// @RequestMapping("/users") //OK
	public List<User> getAllUsers1() {
		return userService.getAllUsers();
		// return Arrays.asList(new User("John", 3000), new User("Kevin", 2000));
	}//http://localhost:8081/api/rest/users

	//@GetMapping(value="/getusers", produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "/users2", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)//OK	
	// @RequestMapping(value = "/getusers", method=RequestMethod.GET) //OK
	// @RequestMapping(value = "/getusers") //OK
	// @RequestMapping("/getusers") //OK
	public ResponseEntity<List<User>> getAllUsers2() {
		List<User> users = userService.getAllUsers();
		return new ResponseEntity<>(users, new HttpHeaders(), HttpStatus.OK);
		// return Arrays.asList(new User("John", 3000), new User("Kevin", 2000));
	}//http://localhost:8081/api/rest/getusers

	 //find range of users
	 @GetMapping(value = "/users3", produces = MediaType.APPLICATION_JSON_VALUE)
	 //@RequestMapping(value = "/getusers2",, method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<User> getUsers(@RequestParam(value="userid1",defaultValue="1",required=false) String id1,@RequestParam(value="userid2",defaultValue="2",required=false) String id2) {
	 //not implemented
	 return null;
	}//http://localhost:8081/api/users3?userid1=1&userid2=5
	 

	/**
	 * ###########################################################################################################################################################################################
	 * PUT Operation 
	 * ###########################################################################################################################################################################################
	 */
	//http://localhost:8081/api/rest/user
	//http://localhost:8081/api/rest/updateuser
	//http://localhost:8081/api/rest/updateuser2
	//http://localhost:8081/api/rest/updateuser3
	//Accept:application/json
	//Content-Type:application/json
	//sample json payload:
		/*{
		    "firstName": "punyasmruti2",
		    "lastName": "nayak",
		    "email": "punyasmruti2@gmail.com"
		}*/
	 
	 @PutMapping(value = "/updateuser1", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
		//@RequestMapping(value="/userupdate2",method=RequestMethod.PUT,consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)//OK
		public String updateUser1(@Valid @RequestBody User user) {
			userService.updateUser(user);
			return "update successful";
		}////http://localhost:8081/api/rest/userupdate2
	 
	 @PutMapping(value = "/updateuser2", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
		// @RequestMapping(value="/userupdate3",method=RequestMethod.PUT,consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)//OK
		public ResponseEntity<String> updateUser2(@Valid @RequestBody User user) {
			userService.updateUser(user);
			return new ResponseEntity<>("update successful", new HttpHeaders(),HttpStatus.OK);
		}//http://localhost:8081/api/rest/userupdate3
	 
	
	@PutMapping(value = "/updateuser3", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	// @RequestMapping(value="/user",method=RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)//OK
	public User updateUser3(@Valid @RequestBody User user) {
		return userService.updateUser(user);
	}//http://localhost:8081/api/rest/user
	
	@PutMapping(value = "/updateuser4", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	// @RequestMapping(value="/userupdate",method=RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)//OK
	public ResponseEntity<User> updateUser5(@Valid @RequestBody User user) {
		User u = userService.updateUser(user);
		//return new ResponseEntity<>(u,HttpStatus.OK);//OK
		return new ResponseEntity<>(u, new HttpHeaders(),HttpStatus.OK);
	}//http://localhost:8081/api/rest/userupdate
	
	@PutMapping(value = "/updateuser5/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	// @RequestMapping(value="/user",method=RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)//OK
	public User updateUser5(@PathVariable("userId") Integer userId, @Valid @RequestBody User user) {
		return userService.updateUserByUserId(userId,user);
	}//http://localhost:8081/api/rest/updateuser6/1
	
	@PutMapping(value = "/updateuser6/{emailID}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	// @RequestMapping(value="/user",method=RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)//OK
	public User updateUser6(@PathVariable("emailID") String emailID, @Valid @RequestBody User user) {
		return userService.updateUserByEmailID(emailID,user);
	}//http://localhost:8081/api/rest/updateuser6/punya@gmail.com
	
	/**
	 * ###########################################################################################################################################################################################
	 * DELETE Operation 
	 * ###########################################################################################################################################################################################
	 */
	
	// @DeleteMapping(value="/user/{userId}")
	@DeleteMapping("/user/{userId}")
	// @RequestMapping(value="/user/{userid}",method=RequestMethod.DELETE)//OK
	public void deleteUserById1(@PathVariable("userId") Integer userId) {
		userService.deleteUserByUserId(userId);
	}// http://localhost:8081/api/rest/user/1

	@DeleteMapping(value = "/userdelete/{userId}")
	// @RequestMapping(value="/userdelete/{userid}",method=RequestMethod.DELETE)//OK
	public ResponseEntity<?> deleteUserById2(@PathVariable("userId") Integer userId) {
		userService.deleteUserByUserId(userId);
		// return new ResponseEntity<>(null, HttpStatus.OK);//OK
		return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.NO_CONTENT);
	}// http://localhost:8081/api/rest/userdelete/1

	@DeleteMapping(value = "/user_delete/{emailID}")
	// @RequestMapping(value="/user_delete/{emailID}",method=RequestMethod.DELETE)//OK
	public ResponseEntity<?> deleteUserByEmailID(@PathVariable("emailID") String emailID) {
		userService.deleteUserByEmailID(emailID);
		// return new ResponseEntity<>(null, HttpStatus.OK);//OK
		return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.NO_CONTENT);
	}// http://localhost:8081/api/rest/userdelete/1

	// @DeleteMapping(value="/user/all")
	@DeleteMapping("/user/all")
	// @RequestMapping(value="/user/all",method=RequestMethod.DELETE)//OK
	public void deleteAll() {
		userService.deleteAllUsers();
	}// http://localhost:8081/api/rest/user/all
	
	/*public ResponseEntity<Object> delete_user(Integer id) {
	return userRepository.findById(id).map(user -> {
		userRepository.delete(user);
		// userRepository.deleteById(id);//OK
		return ResponseEntity.ok().build();
	}).orElseThrow(() -> new UserNotFoundException("User not found with id :" + id));
	// OR
	// return userRepository.findById(id).map(user ->
	// {userRepository.delete(user);return
	// ResponseEntity.ok().build();}).orElse(ResponseEntity.notFound().build());

	}*/

	/**
	 * ################# Exception Handling Operation #################
	 */
	
	// @GetMapping(value="/exceptiondemo") //OK
	@GetMapping("/exceptiondemo")
	// @RequestMapping(value="/exceptiondemo",method=RequestMethod.GET)//OK
	// @RequestMapping("/exceptiondemo") //OK
	public void getException() {
		// throw new NullPointerException();
		throw new ValidationException();
	}// http://localhost:8081/api/rest/exceptiondemo
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ValidationException.class)
	public String exceptionHandler(ValidationException ex) {
		return ex.getMessage();
	}
}
