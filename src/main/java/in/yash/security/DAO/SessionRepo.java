package in.yash.security.DAO;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.yash.security.Entity.Session;
import in.yash.security.Entity.User;

@Repository
public interface SessionRepo extends JpaRepository<Session, Long> {

	List<Session> findByUser(User user);

	Optional<Session> findByRefreshToken(String refreshToken);
	

}
