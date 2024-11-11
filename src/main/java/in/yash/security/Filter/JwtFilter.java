package in.yash.security.Filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import in.yash.security.Entity.User;
import in.yash.security.JwtService.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter{
	
	@Autowired
	private JwtService jwtService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String path = request.getServletPath();
        if ("/signUp".equals(path) || "/login".equals(path)) {
            filterChain.doFilter(request, response);
            return;
        }
		String requestToken=request.getHeader("Authorization");
		if(requestToken==null||!requestToken.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		String token=requestToken.split("Bearer")[1];
		String username=jwtService.extractUserName(token);
		if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
			User user=jwtService.getUserByUsername(username);
			UsernamePasswordAuthenticationToken authenticationToken=new
					UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		}
		filterChain.doFilter(request, response);
		
	}

}
