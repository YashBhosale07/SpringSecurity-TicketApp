package in.yash.security.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import in.yash.security.DTO.LoginRequest;
import in.yash.security.DTO.LoginResponse;
import in.yash.security.DTO.SignUpRequest;
import in.yash.security.DTO.SignUpResponse;
import in.yash.security.service.impl.UserServices;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class UserController {
	
	@Autowired
	private UserServices services;
	
	@PostMapping("/signUp")
	public ResponseEntity<?>signUp(@RequestBody SignUpRequest request){
		SignUpResponse response=services.registerNewUser(request);
		return new ResponseEntity<SignUpResponse>(response,HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/login")
	public ResponseEntity<?>login(@RequestBody LoginRequest request,HttpServletRequest servletRequest, HttpServletResponse servletResponse){
		LoginResponse response=services.login(request);
		 Cookie cookie=new Cookie("refreshToken",response.getRefreshToken());
	        cookie.setHttpOnly(true);
	        servletResponse.addCookie(cookie);
		return new ResponseEntity<LoginResponse>(response,HttpStatus.FOUND);
	}
	
	@PostMapping("/refreshToken")
	public ResponseEntity<?>refreshToken(HttpServletRequest httpServletRequest){
		 Cookie[] cookies = httpServletRequest.getCookies();
		 if (cookies == null || cookies.length == 0) {
	            return new ResponseEntity<>("No cookies found", HttpStatus.BAD_REQUEST);
	        }
		 StringBuilder token = new StringBuilder();
		 for (Cookie cookie : cookies) {
			 if ("refreshToken".equals(cookie.getName()) && cookie.getValue() != null && !cookie.getValue().isEmpty()) {
	                token.append(cookie.getValue());
	                break;
	            }
		}
		 if (token.length() == 0) {
	            return new ResponseEntity<>("Refresh token is missing or invalid", HttpStatus.BAD_REQUEST);
	        }
		LoginResponse response=services.createNewAccessToken(token.toString());
		return new ResponseEntity<LoginResponse>(response,HttpStatus.FOUND);
	}
	
	

}
