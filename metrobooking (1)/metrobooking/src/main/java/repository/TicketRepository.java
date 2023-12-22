package repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import Metro.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
	Optional<Ticket> findByGeneratedId(String generatedId);

}
