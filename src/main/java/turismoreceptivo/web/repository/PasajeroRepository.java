package turismoreceptivo.web.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import turismoreceptivo.web.entity.Pasajero;

@Repository
public interface PasajeroRepository extends JpaRepository<Pasajero, Integer> {

    @Modifying
    @Query("UPDATE Pasajero p SET p.nombre = :nombre, p.apellido = :apellido, p.telefono = :telefono WHERE p.dni = :dni")
    void modificar(@Param("dni") Integer dni, @Param("nombre") String nombre, @Param("apellido") String apellido,
            @Param("telefono") String telefono);
    @Query("SELECT p FROM Pasajero p WHERE p.reserva.id = :id")
    List<Pasajero> buscarPasajerosReserva(@Param("id") String id);
}
