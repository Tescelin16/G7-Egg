package turismoreceptivo.web.repository;

import java.util.Date;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import turismoreceptivo.web.entity.Agencia;

@Repository
public interface AgenciaRepository extends JpaRepository<Agencia, Integer>{
	
	@Modifying
    @Query("UPDATE Agencia a SET a.nombre = :nombre, a.telefono = :telefono, a.email = :email, a.direccion = :direccion WHERE a.legajo = :legajo")
    void modificar(@Param("legajo") Integer legajo, @Param("nombre") String nombre, @Param("telefono") String telefono, 
			@Param("direccion") String direccion, @Param("email") String email);
}
