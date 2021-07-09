package turismoreceptivo.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import turismoreceptivo.web.entity.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, String>{
	
	@Modifying
    @Query("UPDATE Producto p SET p.descripcion = :descripcion, p.titulo = :titulo, p.ubicacion = :ubicacion, p.dias = :dias, p.duracion = :duracion,"
			+ " p.precio = :precio WHERE p.id = :id")
    void modificar(@Param("id") String id, @Param("descripcion") String descripcion, @Param("titulo") String titulo, 
			@Param("ubicacion") String ubicacion, @Param("dias") String dias, 
                        @Param("precio") Integer precio, @Param("duracion") Double duracion);
}
