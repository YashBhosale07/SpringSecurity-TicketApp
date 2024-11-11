package in.yash.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import in.yash.security.DAO.UserRepo;
import in.yash.security.Entity.User;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	@Autowired
	private UserRepo repo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User u=repo.findByUserName(username);
		if(u==null) {
			throw new UsernameNotFoundException("User is not present with username: "+username);
		}
		
		return u;
	}

}
