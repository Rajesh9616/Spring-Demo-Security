package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import static com.example.demo.security.ApplicationUserRole.*;
import static com.example.demo.security.ApplicationUserPermission.*;
import com.example.demo.student.Student;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			// csrf is enable by-default. 
			.csrf().disable() 
			
			.authorizeRequests()
			// Permit root,index.html,css,js
			.antMatchers("/","/index","/css/*","/js/*").permitAll()
			// Permit api Rolewise Access
			// Specificed user (Student) can accsee the resource. Admin Is not allowed to access the resource
			.antMatchers("/api/**").hasRole(STUDENT.name())
			
			/* URI : /management/api/** Is accessed by Admin(rajesh): can perform all operation  and AdminTrainee(ashish) : can perform only read operation  	 */
			/* ---
			 	Below order is important 
			 	These role and permission is replaced with @Preauthrity Annotation in Controller class ("StudentManagementController.java")
			   	and annotate this class with @EnableGlobalMethodSecurity(prePostEnabled = true)
			.antMatchers(HttpMethod.DELETE,"/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
			.antMatchers(HttpMethod.POST,"/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
			.antMatchers(HttpMethod.PUT,"/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
			.antMatchers(HttpMethod.GET,"/management/api/**").hasAnyRole(ADMIN.name(),ADMINTRAINEE.name())
			
			*/
			
			.anyRequest()
			.authenticated()
			.and()
			.httpBasic();
	}
	
	//1 .Creating Dummy User, password and Role
	//2 .Current user is user and password is generated randomly
	//Overriding point 2
	// This method is responsible for get User Information From Database
	
	@Bean
	protected UserDetailsService userDetailsService() {
		// Creating Multiple User
		UserDetails sharmaUser = User.builder()
					.username("sharma")
					/* 1.  Password Must be Encoded
					       Else Error passweordEncoder is not mapped to Avoide this error
					   2.  Autowire PasswordEncode */
					.password(this.passwordEncoder.encode("password")) 		
					// .roles("STUDENT") 	// ROLE_ADMIN
					//.roles(STUDENT.name()) 	// ROLE_STUDENT
					.authorities(STUDENT.getGrantedAuthorities())
					.build();
		
		UserDetails rajeshUser = User.builder()
				.username("rajesh")
				/* 1.  Password Must be Encoded
				       Else Error passweordEncoder is not mapped to Avoide this error
				   2.  Autowire PasswordEncode */
				.password(this.passwordEncoder.encode("password")) 										  
				//.roles("ADMIN") 	// ROLE_ADMIN
				//.roles(ADMIN.name())
				.authorities(ADMIN.getGrantedAuthorities())
				.build();
		
		
		UserDetails ashishUser = User.builder()
				.username("ashish")
				/* 1.  Password Must be Encoded
				       Else Error passweordEncoder is not mapped to Avoide this error
				   2.  Autowire PasswordEncode */
				.password(this.passwordEncoder.encode("password")) 										  
				// ADMINTRAINEE
				//.roles(ADMINTRAINEE.name())
				.authorities(ADMINTRAINEE.getGrantedAuthorities())
				.build();
		
		return new InMemoryUserDetailsManager(
				sharmaUser ,rajeshUser , ashishUser
				);
		
	}
	

}
