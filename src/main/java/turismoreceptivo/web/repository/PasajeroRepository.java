package turismoreceptivo.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import turismoreceptivo.web.entity.Pasajero;

@Repository
public interface PasajeroRepository extends JpaRepository<Pasajero, Integer>{
	
}
