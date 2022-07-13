package com.example.demo.config;

import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserService sysUserService;
	@Bean
	public PasswordEncoder passwordEncoder() {

		return  new MyPasswordEncoder();
	}
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.userDetailsService(sysUserService)  	// Set what to use to find users, and what objects to use to find users
			.passwordEncoder(passwordEncoder()); // Set what to use to encrypt the password, instantiate a password generator
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
			// security token
		 	http.csrf().disable();
		 	http.headers().frameOptions().disable();    //Solve the compatibility problem of iframe and security device
//		 	http.logout()
//		 		.logoutSuccessUrl("/logout2")     //Specify Safe Exit Jump Pages
//		 		.invalidateHttpSession(false);		//Safe logout does not delete the session,
			http.formLogin()
					//Login request blocked
					.loginPage("/login").permitAll()
					//Set the default login success jump page
					.successForwardUrl("/index")
	                .failureUrl("/login?error").usernameParameter("username").passwordParameter("password");

	        http.authorizeRequests().antMatchers("/static/**","/register","/login").permitAll().antMatchers(
					HttpMethod.GET,
					"/*.html",
					"/**/*.html",
					"/**/*.css",
					"/**/*.png",
					"/**/*.jpg",
					"/**/*.js"
			).permitAll();  //Everything under the file is accessible
	        http.logout().logoutUrl("/logout").permitAll();     //exit
	        http.authorizeRequests().anyRequest().authenticated();    //Other than that, you must pass the request authentication to access


		//Close csrf, otherwise the page cannot be accessed
		http.csrf().disable();
	}
	
}
