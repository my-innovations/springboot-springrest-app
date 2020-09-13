package com.springboot.dao;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.stereotype.Repository;

import com.springboot.exception.EmiailAlreadyExistsException;
import com.springboot.exception.NoRecordsFoundException;
import com.springboot.exception.UserNotFoundException;
import com.springboot.model.User;

@Repository
public class UserDaoImpl implements UserDao {

	private List<User> usersList;
	private Map<Integer, User> usersMap;
	private static Integer id = 7;
	
	//using consumer
	static Consumer<User> consumer1 = (user) -> System.out.println(user);
	static Consumer<User> consumer2 = (user) -> System.out.println(user.getFirstname().toUpperCase());
	static Consumer<User> consumer3 = (user) -> System.out.println(user.getEmail());
	
	//using BiConsumer
	static BiConsumer<String,String> biconsumer1 = (firstname,lastname) -> System.out.println(firstname+lastname);
	static BiConsumer<String,String> biconsumer2 = (firstname,email) -> System.out.println(firstname+email);
	
	//using Predicate
	static Predicate<User> predicate1 = user -> user.getLastname().equals("nayak");
	static Predicate<User> predicate2 = user -> user.getEmail().equals("punya@gmail.com");
	
	static Function<String,Integer> fun1 = firstname -> firstname.length();
	static Function<List<User>, Map<String,String>> fun2 =  usersMap ->{
		Map<String,String> map = new HashMap<String,String>();
		usersMap.forEach(user -> {
			//if(predicate1.and(predicate2).test(user)) //OK
			map.put(user.getFirstname(), user.getEmail());
		});
		return map;
	};
	
	public UserDaoImpl() {
		this.usersList = new ArrayList<>();
		// this.usersMap = new HashMap<>();
		this.usersMap = new ConcurrentHashMap<>();
	}

	@PostConstruct
	public void init() {
		
		usersList.add(new User(1, "Punyasmruti", "Nayak", "punya@gmail.com"));
		usersList.add(new User(2, "Parthasarathi", "Beuria", "partha@gmail.com"));
		
		usersMap.put(1, new User(1, "Punyasmruti", "Nayak", "punya@gmail.com"));
		usersMap.put(2, new User(2, "Parthasarathi", "Beuria", "partha@gmail.com"));
		usersMap.put(3, new User(3, "Pankaj kumar", "Prajapati", "pankaj@gmail.com"));
		usersMap.put(4, new User(4, "Sandeep", "Gupta", "sandeep@gmail.com"));
		usersMap.put(5, new User(5, "Neeraj Kant", "Pandey", "neeraj@gmail.com"));
		usersMap.put(6, new User(6, "Omkar", "Nayak", "omkar@gmail.com"));
	}

	@Override
	public User saveUser(User user) {
		if (null != user && null != user.getEmail()) {
			User u = getUserByEmail(user.getEmail());
			if (null != u) {
				throw new EmiailAlreadyExistsException("user aleady exists with email : " + user.getEmail());
			} else {
				user.setUserId(id++);
				usersMap.put(user.getUserId(), user);
			}
		}
		return usersMap.get(user.getUserId());
	}

	@Override
	public User getUserByUserId(Integer userId) {
		User user = null;
		if (null != userId) {
			user = usersMap.get(userId);
			if (null == user)
				throw new UserNotFoundException("No User found with userId:" + userId);
		}
		
		//using consumer functional interface to print user info in console.
		//consumer1.accept(user);
		//consumer2.accept(user);
		//consumer3.accept(user);
		//OR
		//consumer1.andThen(consumer2).andThen(consumer3).accept(user);
	
		return user;
	}
	
	public Map<Integer,String> getUserWithUserIdAndEmail(){
		return usersList.stream().collect(Collectors.toMap(User :: getUserId, User:: getEmail));
	}
	
	public Optional<User> getMaxSalUser() {
		return usersList.stream().reduce((x,y) -> x.getSal() > y.getSal() ? x : y);
	}
	
	@Override
	public List<User> getAllUsersByFirstname(String firstname) {
		List<User> usersAllByFirstname = null;
		if (null != firstname) {
			usersAllByFirstname = new ArrayList<>();
			Set<Integer> userIdSet = usersMap.keySet();
			for (Integer userId : userIdSet) {
				User user = usersMap.get(userId);
				if (user.getFirstname().equalsIgnoreCase(firstname)) {
					usersAllByFirstname.add(user);
					if (usersAllByFirstname.size() == 0)
						throw new NoRecordsFoundException("No Records found");
				}
			}
		}
		return usersAllByFirstname;
	}
	
	//@Override
	public List<User> getAllUsersByFirstnameWithSorting(String firstname) {
		return usersList.stream().sorted(Comparator.comparing(User :: getFirstname).reversed()).collect(Collectors.toList());
	}

	@Override
	public List<User> getAllUsersByLastname(String lastname) {
		List<User> usersAllByLastname = null;
		if (null != lastname) {
			usersAllByLastname = new ArrayList<>();
			Set<Integer> userIdSet = usersMap.keySet();
			for (Integer userId : userIdSet) {
				User user = usersMap.get(userId);
				if (user != null && user.getLastname().equalsIgnoreCase(lastname)) {
					usersAllByLastname.add(user);
					if (usersAllByLastname.size() == 0)
						throw new NoRecordsFoundException("No Records found");
				}
			}
		}
		return usersAllByLastname;
	}
	
	//returning userid,email with lastname nayak using stream api.
	//@Override
	public Map<Integer, String> getAllUsersByLastname2(String lastname) {
		Predicate<User> p1 = (user) -> user.getLastname().equalsIgnoreCase("nayak");
		Map<Integer, String> map = usersList.stream()
											.filter(p1)
											//.filter(p2) //ok
											.collect(Collectors.toMap(User::getUserId, User::getEmail));
		return map;
	}
	
	//get all emails using stream api
		public List<String> getAllEmails() {
			return usersList.stream()
					.peek(user -> System.out.println(user.getEmail())) //for debugging
					.map(User::getEmail)
					//.peek(System::out::println)
					.collect(Collectors.toList());
		}
	
	//get all mobile nos using stream api
	public List<String> getAllContactNos() {
		return usersList.stream()
				.map(User::getContactNos)
				.flatMap(List::stream)
				.distinct()
				.collect(Collectors.toList());
	}
	//using flatmap
	public List<String> tansformFirstnameToUppercase() {
		return usersList.stream().map(User::getFirstname).map(String::toUpperCase).collect(Collectors.toList());
	}
	
	//using flatmap
	public long countTotalContactNos() {
		return usersList.stream().map(User :: getContactNos).flatMap(List :: stream).distinct().count();
	}
	
	@Override
	public User getUserByEmail(String emailID) {
		User user = null;
		if (null != emailID) {
			Set<Integer> userIdSet = usersMap.keySet();
			for (Integer userId : userIdSet) {
				User u = usersMap.get(userId);
				if (u != null && u.getEmail().equalsIgnoreCase(emailID)) {
					user = u;
				}
			}
		}
		return user;
	}

	@Override
	public List<User> getAllUsers() {
		List<User> usersAll = new ArrayList<>();
		Set<Integer> set = usersMap.keySet();
		for (Integer userId : set) {
			User user = usersMap.get(userId);
			usersAll.add(user);
		}
		
		//using forEach and consumer
		usersAll.forEach(consumer1);
		usersAll.forEach(consumer1.andThen(consumer2));
		
		usersAll.forEach(user -> {
			if (user.getEmail().equalsIgnoreCase("punya@gmail.com")) {
				consumer2.andThen(consumer1).accept(user);
			}
		});
		
		//using forEach and BiConsumer
		usersAll.forEach(user -> {
			biconsumer1.accept(user.getFirstname(), user.getLastname());
			biconsumer2.accept(user.getFirstname(), user.getEmail());
		});
		
		
		// using Pedicate
		usersAll.stream().filter(predicate1).collect(Collectors.toList());
		usersAll.forEach(user -> {
			if(predicate1.test(user)) {
				System.out.println(user);
			}
		});
		
		//using function
		Map map = fun2.apply(usersAll);
		System.out.println(map);
		

		return usersAll;
	}

	@Override
	public User updateUser(User user) {
		if (null != user && null != user.getUserId()) {
			usersMap.put(user.getUserId(), user);
		} else {
			user.setUserId(id++);
			usersMap.put(user.getUserId(), user);
		}
		return usersMap.get(user.getUserId());
	}

	@Override
	public User updateUserByUserId(Integer userId, User user) {
		if (null != userId) {
			User u = usersMap.get(userId);
			if (null != u && userId == user.getUserId()) {
				u.setFirstname(user.getFirstname());
				u.setLastname(user.getLastname());
				u.setEmail(user.getEmail());
				usersMap.put(userId, u);
			} else {
				user.setUserId(id++);
				usersMap.put(user.getUserId(), user);
			}
		}
		return usersMap.get(userId);
	}

	@Override
	public User updateUserByEmailID(String emailID, User user) {
		Set<Integer> userIdSet = usersMap.keySet();
		for (Integer userId : userIdSet) {
			User u = usersMap.get(userId);
			if (null != u && u.getEmail().equalsIgnoreCase(emailID)) {
				u.setFirstname(user.getFirstname());
				u.setLastname(user.getLastname());
				u.setEmail(user.getEmail());
				usersMap.put(u.getUserId(), u);
			} else {
				user.setUserId(id++);
				usersMap.put(user.getUserId(), user);
			}
		}

		return usersMap.get(user.getUserId());
	}

	@Override
	public void deleteUserByUserId(Integer userId) {
		if (null != userId)
			usersMap.remove(userId);
	}

	@Override
	public void deleteUserByEmailID(String emailID) {
		if (null != emailID) {
			Set<Integer> userIdSet = usersMap.keySet();
			for (Integer userId : userIdSet) {
				User user = usersMap.get(userId);
				if (user != null && user.getEmail().equalsIgnoreCase(emailID)) {
					usersMap.remove(userId);
				}
			}
		}
	}

	@Override
	public void deleteAllUsers() {
		usersMap.clear();
	}

	@PreDestroy
	public void destroy() {
		System.out.println("UserDaoImpl obj destroyed");
	}
}
