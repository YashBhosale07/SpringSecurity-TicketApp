package in.yash.security.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;

import in.yash.security.DAO.TicketRepo;
import in.yash.security.DTO.TicketRequest;
import in.yash.security.DTO.TicketResponse;
import in.yash.security.Entity.Ticket;
import in.yash.security.service.TicketService;

@Service
public class TickerServiceImpl implements TicketService {
	
	@Autowired
	private TicketRepo repo;
	
	@Autowired
	private ModelMapper mapper;

	@Override
	public TicketResponse generateTicket(TicketRequest request) {
		Ticket ticket=mapper.map(request, Ticket.class);
		repo.save(ticket);
		TicketResponse response=mapper.map(ticket, TicketResponse.class);
		return response;
	}

	@Override
	public Optional<TicketResponse> getTicket(int id) {
	    return repo.findById(id)
	               .map(ticket -> mapper.map(ticket, TicketResponse.class));
	}

	@Override
	public List<TicketResponse> getAllTickets() {
		List<Ticket>tickets=repo.findAll();
		List<TicketResponse>ticketResponses=tickets.stream().map(ticket->mapper.map(ticket, TicketResponse.class))
				.collect(Collectors.toList());
		return ticketResponses;
	}

}
