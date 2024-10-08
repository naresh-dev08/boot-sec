package com.nt.confg;

import javax.sql.DataSource;

import org.apache.catalina.authenticator.SpnegoAuthenticator.AuthenticateAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;



@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private DataSource ds;


	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		//configure jdbc as the Authentication info provider
		auth.jdbcAuthentication().dataSource(ds).passwordEncoder(new BCryptPasswordEncoder())
		    .usersByUsernameQuery("SELECT UNAME, PWD, STATUS FROM USERS WHERE UNAME=?")
		    .authoritiesByUsernameQuery("SELECT UNAME, ROLES FROM USERS_ROLES WHERE UNAME=?");
		
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		//Place the Authentication and Authorization logics for the request URLs
		http.authorizeHttpRequests().antMatchers("/").permitAll() //No Authentication and Authorization
		.antMatchers("/offers").authenticated()  //Only Authentication
		.antMatchers("/balance").hasAnyAuthority("CUSTOMER", "MANAGER") 
		.antMatchers("/loanApprove").hasAuthority("MANAGER")
		.anyRequest().authenticated() //Remaining
		//.and().httpBasic()
		.and().formLogin()
		//.and().rememberMe()
		.and().logout()
		.and().exceptionHandling().accessDeniedPage("/denied") //sends request to handler method whose request path is /denied
		.and().sessionManagement().maximumSessions(2).maxSessionsPreventsLogin(true); // should be the last statement of the method chaining
	}
	

}
