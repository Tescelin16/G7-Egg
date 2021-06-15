package turismoreceptivo.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import turismoreceptivo.web.entity.Agencia;

@Repository
public interface AgenciaRepository extends JpaRepository<Agencia, Integer>{
	
}
