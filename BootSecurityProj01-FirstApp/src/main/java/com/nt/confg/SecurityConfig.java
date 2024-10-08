package com.nt.confg;

import org.apache.catalina.authenticator.SpnegoAuthenticator.AuthenticateAction;
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
	

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//inMemory DB as the Authentication info provider
//		auth.inMemoryAuthentication().withUser("raja").password("{noop}rani").roles("CUSTOMER");
//		auth.inMemoryAuthentication().withUser("ramesh").password("{noop}hyd").roles("MANAGER");
//		auth.inMemoryAuthentication().withUser("anil").password("{noop}vizag").roles("CUSTOMER", "MANAGER");
//		auth.inMemoryAuthentication().withUser("mahesh").password("{noop}delhi").roles("VISIT");
		
		auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder()).withUser("raja").password("$2a$10$vioJ5e15DleGrGixh6yBj.QUuO2lFg5Ld17xGfbiot1jyDj2FvwT2").roles("CUSTOMER");
		auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder()).withUser("ramesh").password("$2a$10$ipGxkZGJGiuYlkQdsuqOCOpHj/H3uNW9zhBD3/BrEKeMDLoLQj7fu").roles("MANAGER");
		auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder()).withUser("anil").password("$2a$10$m0cjIsD04Mwf6FANdcXiz.kXE93J9ZMHed3By6JAliyHuqVEYq5ie").roles("CUSTOMER", "MANAGER");
		auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder()).withUser("mahesh").password("$2a$10$8Z45obz2U5wBfA3aGpY0L.VUxmu/KNovOQ1iwYZFoSxsmiReuQ166").roles("VISIT");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//Place the Authentication and Authorization logics for the request URLs
		http.authorizeHttpRequests().antMatchers("/").permitAll() //No Authentication and Authorization
		.antMatchers("/offers").authenticated()  //Only Authentication
		.antMatchers("/balance").hasAnyRole("CUSTOMER", "MANAGER") 
		.antMatchers("/loanApprove").hasRole("MANAGER")
		.anyRequest().authenticated() //Remaining
		//.and().httpBasic()
		.and().formLogin()
		//.and().rememberMe()
		.and().logout()
		.and().exceptionHandling().accessDeniedPage("/denied") //sends request to handler method whose request path is /denied
		.and().sessionManagement().maximumSessions(2).maxSessionsPreventsLogin(true); // should be the last statement of the method chaining
	}
	

}
