package turismoreceptivo.web.service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import turismoreceptivo.web.entity.Usuario;
import turismoreceptivo.web.error.ErrorService;
import turismoreceptivo.web.repository.UsuarioRepository;

@Service
public class UsuarioService implements UserDetailsService{

    @Autowired
    private UsuarioRepository uR;

    @Autowired
    private BCryptPasswordEncoder encoder;
    
    @Transactional
    public void crearUsuario(Integer dni, String nombre, String apellido, String email, String telefono,
            String telefono2, String alojamiento, Date fechaNacimiento, String username, String clave) throws ErrorService{
        validacion(dni, nombre, apellido, email, telefono, telefono2, alojamiento, fechaNacimiento, username, clave);
        
        Usuario usuario = new Usuario();
        usuario.setDni(dni);
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setEmail(email);
        usuario.setTelefono(telefono);
        usuario.setTelefono2(telefono2);
        usuario.setAlojamiento(alojamiento);
        usuario.setFechaNacimiento(fechaNacimiento);
        usuario.setUsername(username);
        usuario.setClave(encoder.encode(clave));

        uR.save(usuario);
    }
    
    public void validacion(Integer dni, String nombre, String apellido, String email, String telefono,
            String telefono2, String alojamiento, Date fechaNacimiento, String username, String clave) throws ErrorService{
        
        if (dni == null) {
            throw new ErrorService("El dni no puede estar vacio");
        }
        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorService("El nombre no puede estar vacio");
        }
        if (apellido == null || apellido.isEmpty()) {
            throw new ErrorService("El apellido no puede estar vacio");
        }
        if (email == null || email.isEmpty()) {
            throw new ErrorService("El email no puede estar vacio");
        }
        if (telefono == null || telefono.isEmpty()) {
            throw new ErrorService("El telefono no puede estar vacio");
        }
        if (telefono2 == null || telefono2.isEmpty()) {
            throw new ErrorService("El segundo telefono no puede estar vacio");
        }
        if (alojamiento == null || alojamiento.isEmpty()) {
            throw new ErrorService("El alojamiento no puede estar vacio");
        }
        if (fechaNacimiento == null) {
            throw new ErrorService("La fecha de nacimiento no puede estar vacio");
        }
        if (username == null || username.isEmpty()) {
            throw new ErrorService("El nombre de usuario no puede estar vacio");
        }
        if (clave == null || clave.isEmpty()) {
            throw new ErrorService("La clave no puede estar vacia");
        }
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        Usuario usuario = uR.buscarPorNombreDeUsuario(username);
        
        if (usuario == null) {
            throw new UsernameNotFoundException("No hay ningun usuario con el username " + username);
        }
        
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attributes.getRequest().getSession(true);
        
        session.setAttribute("username", usuario.getUsername());
        session.setAttribute("id", usuario.getDni());
        
        User user = new User(usuario.getUsername(), usuario.getClave(), Collections.EMPTY_LIST);
        return user;
    }

    
    
}
