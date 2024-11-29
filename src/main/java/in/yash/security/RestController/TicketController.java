package in.yash.security.RestController;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import in.yash.security.DTO.TicketRequest;
import in.yash.security.DTO.TicketResponse;
import in.yash.security.service.TicketService;

@RestController
public class TicketController {
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private TicketService service;
	
	@PostMapping("/generateTicket")
	public ResponseEntity<TicketResponse>generateTicket(@RequestBody TicketRequest request ){
		TicketResponse generatedTicket=service.generateTicket(request);
		return new ResponseEntity<TicketResponse>(generatedTicket,HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/getTicket/{id}")
	public ResponseEntity<?>getTicket(@PathVariable int id){
		Optional<TicketResponse> response=service.getTicket(id);
		if(response.isPresent()) {
			return new ResponseEntity<TicketResponse>(response.get(),HttpStatus.FOUND);
		}
		return new ResponseEntity<String>("Ticket is not present with id:"+id,HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/getAllTickets")
	public ResponseEntity<?>getAllTicket(){
		return new ResponseEntity<String>("Ticket is not present with id:",HttpStatus.BAD_REQUEST);
	}
	
}
