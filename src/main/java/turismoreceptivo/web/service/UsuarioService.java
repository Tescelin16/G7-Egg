package turismoreceptivo.web.service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
import turismoreceptivo.web.repository.RolRepository;
import turismoreceptivo.web.repository.UsuarioRepository;

@Service
public class UsuarioService implements UserDetailsService{

    @Autowired
    private UsuarioRepository uR;

    @Autowired
    private BCryptPasswordEncoder encoder;
    
    @Autowired
    private RolRepository rolRepository;
    
    @Transactional
    public void crearUsuario(String nombre, Integer dni, String apellido, String email, String telefono,
            String telefono2, Date fechaNacimiento, String username, 
            String clave, String rolId) throws ErrorService{
        
        validacion(nombre, dni, apellido, email, telefono, telefono2, fechaNacimiento, username, clave);
        
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setDni(dni);
        usuario.setApellido(apellido);
        usuario.setEmail(email);
        usuario.setTelefono(telefono);
        usuario.setTelefono2(telefono2);
        usuario.setFechaNacimiento(fechaNacimiento);
        usuario.setUsername(username);
        usuario.setClave(encoder.encode(clave));
        if (rolId == null) {
            rolId="02";
            usuario.setRol(rolRepository.findById(rolId).orElse(null));
        }else{
            usuario.setRol(rolRepository.findById(rolId).orElse(null));
        }
        
        uR.save(usuario);
    }
    
    public void validacion(String nombre, Integer dni, String apellido, String email, String telefono,
            String telefono2, Date fechaNacimiento, String username, String clave) throws ErrorService{
        
        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorService("El nombre no puede estar vacio");
        }
         if (dni == null) {
            throw new ErrorService("El dni no puede estar vacio");
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
    public void modificarUsuario(Integer dni, String nombre, String apellido, String email, String telefono, 
            String telefono2, Date fechaNacimiento, String rolId) throws ErrorService{
        uR.modificar(dni, nombre, apellido, email, telefono, telefono2, fechaNacimiento, rolRepository.findById(rolId).orElse(null));
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
    public void eliminar(Integer dni) throws ErrorService{
        uR.deleteById(dni);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        String id;
        Usuario usuario = uR.buscarPorNombreDeUsuario(username);
        
        if (usuario == null) {
            throw new UsernameNotFoundException("No hay ningun usuario con el username " + username);
        }
        
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attributes.getRequest().getSession(true);
        
        session.setAttribute("username", usuario.getUsername());
        session.setAttribute("id", String.valueOf(usuario.getDni()));
        
        GrantedAuthority rol = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().getNombre());
        
        User user = new User(usuario.getUsername(), usuario.getClave(), Collections.singletonList(rol));
        return user;
    }

    
    
}
