package turismoreceptivo.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import turismoreceptivo.web.entity.Reserva;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, String>{
	
}
