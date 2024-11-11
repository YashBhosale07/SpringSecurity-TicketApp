package in.yash.security.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.yash.security.Entity.Ticket;

@Repository
public interface TicketRepo extends JpaRepository<Ticket, Integer>{

}
