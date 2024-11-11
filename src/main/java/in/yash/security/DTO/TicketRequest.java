package in.yash.security.DTO;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Data;

@Data

public class TicketRequest {
	
	private String name;
	private String source;
	private String destination;
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate date;
	private String email;

}
