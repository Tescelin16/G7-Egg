package turismoreceptivo.web.repository;

import java.util.Date;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import turismoreceptivo.web.entity.Rol;
import turismoreceptivo.web.entity.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    @Modifying
    @Query("UPDATE Usuario u SET u.nombre = :nombre, u.apellido = :apellido, u.email = :email, u.telefono = :telefono, u.telefono2 = :telefono2,"
            + "  u.fechaNacimiento = :fechaNacimiento, u.rol = :rol WHERE u.dni = :dni")
    void modificar(@Param("dni") Integer dni, @Param("nombre") String nombre, @Param("apellido") String apellido,
            @Param("email") String email, @Param("telefono") String telefono, @Param("telefono2") String telefono2,
             @Param("fechaNacimiento") Date fechaNacimiento, @Param("rol") Rol rol);
    
    @Query("SELECT u FROM Usuario u WHERE u.username = :username")
    Usuario buscarPorNombreDeUsuario(@Param("username") String username);
    
}
