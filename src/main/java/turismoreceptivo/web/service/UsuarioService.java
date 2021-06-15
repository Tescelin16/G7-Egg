package turismoreceptivo.web.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import turismoreceptivo.web.entity.Usuario;
import turismoreceptivo.web.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository uR;

    @Transactional
    public void crearUsuario(Integer dni, String nombre, String apellido, String email, String telefono, String telefono2, String alojamiento, Date fechaNacimiento) {
        Usuario usuario = new Usuario();
        usuario.setDni(dni);
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setEmail(email);
        usuario.setTelefono(telefono);
        usuario.setTelefono2(telefono2);
        usuario.setAlojamiento(alojamiento);
        usuario.setFechaNacimiento(fechaNacimiento);

        uR.save(usuario);
    }

    @Transactional
    public void modificarUsuario(Integer dni, String nombre, String apellido, String email, String telefono, String telefono2, String alojamiento, Date fechaNacimiento) {
        uR.modificar(dni, nombre, apellido, email, telefono, telefono2, alojamiento, fechaNacimiento);
    }

    @Transactional(readOnly = true)
    public List<Usuario> buscarTodos() {
        return uR.findAll();
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorDni(Integer dni) {
        Optional<Usuario> usuarioOptional = uR.findById(dni);
        return usuarioOptional.orElse(null);
    }

    @Transactional
    public void eliminar(Integer dni) {
        uR.deleteById(dni);
    }

}
