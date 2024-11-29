package in.yash.security.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import in.yash.security.DAO.UserRepo;
import in.yash.security.DTO.LoginRequest;
import in.yash.security.DTO.LoginResponse;
import in.yash.security.DTO.SignUpRequest;
import in.yash.security.DTO.SignUpResponse;
import in.yash.security.Entity.User;
import in.yash.security.JwtService.JwtService;
import in.yash.security.SessionService.SessionService;

@Service
public class UserServices {
	
	@Autowired
	private UserRepo repo;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private SessionService sessionService;
	
	@Autowired
	private JwtService jwtService;
	
	public SignUpResponse registerNewUser(SignUpRequest request) {
		User u=repo.findByUserName(request.getUserName());
		if(u!=null) {
			throw new RuntimeException("User is already present");
		}
		User newUser=mapper.map(request, User.class);
		newUser.setPassword(encoder.encode(request.getPassword()));
		User savedUser=repo.save(newUser);
		SignUpResponse response=new SignUpResponse();
		response.setFirstName(savedUser.getFirstName());
		response.setLastName(savedUser.getLastName());
		response.setUserName(savedUser.getUsername());
		response.setRole(savedUser.getRole().name());
		return response;
	}
	
	public LoginResponse login(LoginRequest request) {
		UsernamePasswordAuthenticationToken authenticationToken=new 
				UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword());
		Authentication authentication=authenticationManager.authenticate(authenticationToken);
		User user= (User) authentication.getPrincipal();
		String token=jwtService.createToken(user);
		String refreshToken=jwtService.createRefreshToken(user);
		sessionService.createNewSession(user,refreshToken);
		LoginResponse response=new LoginResponse();
		response.setAccessToken(token);
		response.setRefreshToken(refreshToken);
		return response;
	}
	
	public LoginResponse createNewAccessToken(String refreshToken) {
		int id=jwtService.extractId(refreshToken);
		User u=repo.findById(id).orElseThrow(()->new UsernameNotFoundException("Invalid refresh token"));
		String newAcessToken=jwtService.createToken(u);
		sessionService.validSession(refreshToken);
		LoginResponse loginResponse=new LoginResponse();
		
		loginResponse.setAccessToken(newAcessToken);
		loginResponse.setRefreshToken(refreshToken);
		return loginResponse;
	}

	
	
}
