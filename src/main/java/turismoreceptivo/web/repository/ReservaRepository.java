package turismoreceptivo.web.repository;

import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import turismoreceptivo.web.entity.Reserva;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, String>{
	
	@Modifying
    @Query("UPDATE Reserva r SET r.personas = :personas, r.fechayhorario = :fechayhorario WHERE r.id = :id")
    void modificar(@Param("id") String id, @Param("personas") int personas, @Param("fechayhorario") LocalDateTime fechayhorario);
}
