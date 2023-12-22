package Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Metro.Ticket;
import repository.TicketRepository;

@Service
public class TicketService {

	@Autowired
	private TicketRepository ticketRepository;

	public Ticket generateTicket() {
		// Implement ticket generation logic here
		// Ensure expiry time, usage count, etc.
		Ticket ticket = new Ticket();
		// Set properties
		return ticketRepository.save(ticket);
	}

	public Optional<Ticket> getTicketById(Long id) {
		return ticketRepository.findById(id);
	}

}
