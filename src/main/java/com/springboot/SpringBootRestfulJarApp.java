package com.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//mvn clean compile install
//For .jar packaging
//springboot-springrest-app > java -jar ./target/springboot-springREST-app.jar

//--------------------------
//@Import({Swagger2Config.class}) //optional
//when we are using xml based configuration with spring boot.
// @ImportResource("classpth:app-root-config.xml") 
// @Import({A.class,B.class})
// --------------------------
// @Controller // ok but optional and not recommended to use here.
// OR
//@RestController // ok but optional and not recommended to use here.
/*
@Configuration
@EnableAutoConfiguration
@ComponentScan
//or
@ComponentScan({"com.springboot.restcontroller","com.springboot.service"})
//or
@ComponentScan(basePackages={"com.springboot.restcontroller","com.springboot.service"})
*/
//OR
@SpringBootApplication
public class SpringBootRestfulJarApp {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootRestfulJarApp.class, args);
	}

	// we can expose our rest URI here. but not recommended.so commented
	// case1 - index as a view page, when we are using @Controller at class level. 
	// case2 - index as a string response msg, when we are using @RestController at class level. 
	// @RequestMapping("/")
	// RequestMapping("/homePage") 
	// public String homePage() { 
	// return "index";
	// }//http://localhost:8081/homePage
	
	/*
	 * @Controller 
	 * private static class MyMvcController {
	 * @RequestMapping("/homePage") 
	 * public String homePage() { 
	 * return  "message from my rest controller";
	 *  }// http://localhost:8081/homePage
	 */
	 
	// we can expose our rest URI here. but not recommended.so commented
	// Always index is a view page(index.jsp), weather we are using @Controller or @RestController 
	// because return type is ModelAndView obj.
	// @RequestMapping("/")
	// @RequestMapping("/homePage")
	// @RequestMapping(value={"/","/homePage"})
	// public ModelAndView homePage() {
	// return new ModelAndView("index");
	// }//http://localhost:8081/homePage

	// here we can create spring4 style rest controller class(static inner class).
	// Not recomended to write here.so commented
	
	// @RestController
	// private static class MyRestController {
	// @RequestMapping("/hello123")
	// public String hello() {
	// return "message from my rest controller";
	// }// http://localhost:8081/hello123
	 
}
