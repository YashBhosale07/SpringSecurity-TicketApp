package in.yash.security.JwtService;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import in.yash.security.DAO.UserRepo;
import in.yash.security.Entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;


@Component
public class JwtService {
	
	@Autowired
	private UserRepo repo;
	
	private final String secretKey="sfjfnjfv2132ndvbadsfefsfsfsfsfgvdfvndgvdkfgjvfuygudrf";
	
	private SecretKey key() {
		return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
	}
	
	public String createToken(User user) {
		return generateToken(user);
	}

	public String createRefreshToken(User user) {
		return generateRefreshToken(user);
	}
	
	private String generateRefreshToken(User user) {
		return Jwts
				.builder()
				.setSubject(String.valueOf(user.getId()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+1000*60*60))
				.signWith(key())
				.compact();
	}
	
	public int extractId(String token) {
		Claims claims=Jwts.parserBuilder()
				.setSigningKey(key())
				.build()
				.parseClaimsJws(token)
				.getBody();
		String id=claims.getSubject();
		return Integer.parseInt(id);
	}

	private String generateToken(User user) {
		return Jwts
				.builder()
				.claim("role", user.getAuthorities())
				.setSubject(user.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+1000*60))
				.signWith(key())
				.compact();
	}
	
	public String extractUserName(String token) {
		Claims claims = Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
	}
	
	public User getUserByUsername(String userName) {
		  return repo.findByUserName(userName);
	}

	

}
