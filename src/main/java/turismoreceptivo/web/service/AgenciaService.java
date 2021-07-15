package turismoreceptivo.web.service;

import java.util.Collections;
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
import turismoreceptivo.web.entity.Agencia;
import turismoreceptivo.web.repository.AgenciaRepository;
import turismoreceptivo.web.repository.RolRepository;

@Service
public class AgenciaService implements UserDetailsService{

    @Autowired
    private AgenciaRepository agenciaRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;
    
    @Autowired
    private RolRepository rolRepository;

    @Transactional
    public void crear(String legajo, String nombre, String telefono, String direccion, String email, 
            String clave) {
        Agencia agencia = new Agencia();
        agencia.setLegajo(legajo);
        agencia.setNombre(nombre);
        agencia.setTelefono(telefono);
        agencia.setDireccion(direccion);
        agencia.setEmail(email);
        agencia.setClave(encoder.encode(clave));
        String rolId = "02";
        agencia.setRol(rolRepository.findById(rolId).orElse(null));

        agenciaRepository.save(agencia);
    }

    @Transactional
    public void modificar(String legajo, String nombre, String telefono, String direccion, String email, 
            String clave, String rolId) {
        agenciaRepository.modificar(legajo, nombre, telefono, direccion, email, clave, rolRepository.findById(rolId).orElse(null));
    }

    @Transactional(readOnly = true)
    public List<Agencia> buscarTodos() {
        return agenciaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Agencia buscarPorLegajo(String legajo) {
        Optional<Agencia> agenciaOptional = agenciaRepository.findById(legajo);
        return agenciaOptional.orElse(null);
    }

    @Transactional
    public void eliminar(String legajo) {
        agenciaRepository.deleteById(legajo);
    }
    
     @Override
    public UserDetails loadUserByUsername(String legajo) throws UsernameNotFoundException{
        Agencia agencia = agenciaRepository.buscarPorLegajo(legajo);
        
        if (agencia == null) {
            throw new UsernameNotFoundException("No hay ninguna agencia con el legajo: " + legajo);
        }
        
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attributes.getRequest().getSession(true);
        
        session.setAttribute("id", agencia.getLegajo());
        session.setAttribute("username", agencia.getNombre());
        
        GrantedAuthority rol = new SimpleGrantedAuthority("ROLE_" + agencia.getRol().getNombre());
        
        User user = new User(agencia.getLegajo(), agencia.getClave(), Collections.singletonList(rol));
        return user;
    }
}
