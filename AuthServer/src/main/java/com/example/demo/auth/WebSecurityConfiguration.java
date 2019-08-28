package com.example.demo.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;


/**
 * 
 * @author tapasmondal
 * 
 * This is responsible for added some default user  
 *
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	 @Bean
     @Override
     public UserDetailsService userDetailsService () {
		 
		 UserDetails user = User.builder (). username ("user"). password (passwordEncoder().encode("secret")).
		 roles ("USER"). build ();
		 
		 UserDetails userAdmin = User.builder (). username ("admin"). password (passwordEncoder().encode ("secret")).
		 roles ("ADMIN"). build ();
     
         return new InMemoryUserDetailsManager (user, userAdmin);
     }

	 
	 @Override
	    @Bean
	    public AuthenticationManager authenticationManagerBean() 
	      throws Exception {
	        return super.authenticationManagerBean();
	    }
	 
	    public PasswordEncoder passwordEncoder () {
	         return new BCryptPasswordEncoder ();
	    }


		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests().anyRequest().authenticated()
			.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER);
		}


	 
     
     
     
}
