package in.yash.security.service;

import java.util.List;
import java.util.Optional;

import in.yash.security.DTO.TicketRequest;
import in.yash.security.DTO.TicketResponse;

public interface TicketService {
	
	TicketResponse generateTicket(TicketRequest request);
	Optional<TicketResponse> getTicket(int id);
	List<TicketResponse>getAllTickets();

}
