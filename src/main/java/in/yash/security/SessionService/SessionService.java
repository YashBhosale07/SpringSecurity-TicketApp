package in.yash.security.SessionService;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import org.hibernate.SessionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import in.yash.security.DAO.SessionRepo;
import in.yash.security.Entity.Session;
import in.yash.security.Entity.User;

@Service
public class SessionService {
	
	@Autowired
	private SessionRepo repo;
	
	private final int sessionLimit=2;

	public void createNewSession(User user, String refreshToken) {
		List<Session>sessions=repo.findByUser(user);
		if(sessions.size()==sessionLimit) {
			sessions.sort(Comparator.comparing(Session::getLastDate));
			Session latestSession=sessions.get(0);
			repo.delete(latestSession);
		}
		Session session=Session.builder()
				.refreshToken(refreshToken)
				.user(user)
				.build();
		repo.save(session);
	}
	
	public void validSession(String refreshToken) {
		Session session=repo.findByRefreshToken(refreshToken).
				orElseThrow(()->new SessionException("Invalid refresh token"));
		session.setLastDate(LocalDateTime.now());
		repo.save(session);
	}

}
