package in.yash.security.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import in.yash.security.Filter.JwtFilter;

/*
 * default security filter logic=>Either its is generated and printed in console and the Username is user.
 * or
 * we can write it in application.properties
 * issue with default security filter->
 * but there no authorization(i.e can one can access any url) and only authentication can be done 
 */

@Configuration //Spring frameWork consider this a configuration
@EnableWebSecurity //We want to write our own Security Filter with
				   //our own authentication object and authorization object
public class SecurityConfig {
	
	@Autowired
	private JwtFilter filter;

	//Authentication logic
	/*
	 * 			Authentication logic
	 * 1.) Need to create two User -> admin and guest
	 * 2.) UserDetailsService=>framework Authentication object (it will override the Username->user)
	 */
	
//	@Bean
//	public UserDetailsService userDetailsService(PasswordEncoder encoder) {
//		//create admin
//		UserDetails adminUser=User.withUsername("yash").password(encoder.encode("admin")).roles("ADMIN").build();
//		//create guest
//		UserDetails guestUser=User.withUsername("sam").password(encoder.encode("guest")).roles("GUEST").build();
//		
//		//please return the two user in the memory(tomact memory)
//		return new InMemoryUserDetailsManager(adminUser,guestUser);
//
//	}
	
	//Authorization logic
	//admin role can access all tickets
	//guest role can access only individual logic
	//HttpSecurity is a authorization object
	
	@Bean
	public SecurityFilterChain chain(HttpSecurity httpSecurity) throws Exception {
		 return httpSecurity
	                .csrf(csrf -> csrf.disable()) // Disable CSRF for testing in Postman
	                .authorizeHttpRequests(auth -> auth
	                    .requestMatchers("/getAllTicket").hasRole("ADMIN") // Access only for ADMIN role
	                    .requestMatchers("/getTicket/**").hasAnyRole("USER") // Access only for GUEST role
	                    .requestMatchers("/signUp").permitAll()
	                    .requestMatchers("/login").permitAll()// Allow access to sign-up endpoint
	                    .anyRequest().authenticated()// All other requests require authentication
	                )
	                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
	                .build();
	}
	
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder(); //this will convert the password into Base64
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}
	
}
