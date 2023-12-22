package controller;

import org.apache.el.stream.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Metro.Ticket;
import Service.TicketService;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {
	 @Autowired
	    private TicketService ticketService;

	    @PostMapping("/generate")
	    public ResponseEntity<String> generateTicket() {
	        Ticket ticket = ticketService.generateTicket();
	        return ResponseEntity.ok("Ticket generated with ID: " + ticket.getId());
	    }

	    @GetMapping("/{id}")
	    public ResponseEntity<Ticket> getTicket(@PathVariable Long id) {
	        java.util.Optional<Ticket> ticket = ticketService.getTicketById(id);
	        return ticket.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	    }
}
