package in.yash.security.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.yash.security.Entity.User;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
	
	User findByUserName(String userName);

}
