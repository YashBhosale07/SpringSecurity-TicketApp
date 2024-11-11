package in.yash.security.DTO;

import java.time.LocalDate;
import lombok.Data;

@Data
public class TicketResponse {
	
	private int id;
	private String name;
	private String source;
	private String destination;
	private LocalDate date;
	private String email;

}
