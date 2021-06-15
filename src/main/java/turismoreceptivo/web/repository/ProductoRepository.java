package turismoreceptivo.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import turismoreceptivo.web.entity.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, String>{
	
}
