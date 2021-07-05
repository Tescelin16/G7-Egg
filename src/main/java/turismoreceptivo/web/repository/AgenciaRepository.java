package turismoreceptivo.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import turismoreceptivo.web.entity.Agencia;

@Repository
public interface AgenciaRepository extends JpaRepository<Agencia, Integer> {

    @Modifying
    @Query("UPDATE Agencia a SET a.nombre = :nombre, a.telefono = :telefono, a.email = :email, a.direccion = :direccion, a.clave = :clave WHERE a.legajo = :legajo")
<<<<<<< Updated upstream
    void modificar(@Param("legajo") String legajo, @Param("nombre") String nombre, @Param("telefono") String telefono,
            @Param("direccion") String direccion, @Param("email") String email, @Param("clave") String clave);
=======
    void modificar(@Param("nombre") String nombre, @Param("telefono") String telefono,
            @Param("direccion") String direccion, @Param("email") String email, 
            @Param("legajo") String legajo, @Param("clave") String clave);
>>>>>>> Stashed changes
    
    @Query("SELECT a FROM Agencia a WHERE a.legajo = :legajo")
    Agencia buscarPorLegajo(@Param("legajo") String legajo);
}
