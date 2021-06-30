package turismoreceptivo.web.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import turismoreceptivo.web.entity.Agencia;
import turismoreceptivo.web.repository.AgenciaRepository;

@Service
public class AgenciaService implements UserDetailsService{

    @Autowired
    private AgenciaRepository agenciaRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Transactional
    public void crear(String legajo, String nombre, String telefono, String direccion, String email, String clave) {
        Agencia agencia = new Agencia();
        agencia.setLegajo(legajo);
        agencia.setNombre(nombre);
        agencia.setTelefono(telefono);
        agencia.setDireccion(direccion);
        agencia.setEmail(email);
        agencia.setClave(encoder.encode(clave));

        agenciaRepository.save(agencia);
    }

    @Transactional
    public void modificar(String legajo, String nombre, String telefono, String direccion, String email, String clave) {
        agenciaRepository.modificar(legajo, nombre, telefono, direccion, email, clave);
    }

    @Transactional(readOnly = true)
    public List<Agencia> buscarTodos() {
        return agenciaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Agencia buscarPorLegajo(Integer legajo) {
        Optional<Agencia> agenciaOptional = agenciaRepository.findById(legajo);
        return agenciaOptional.orElse(null);
    }

    @Transactional
    public void eliminar(Integer legajo) {
        agenciaRepository.deleteById(legajo);
    }
    
     @Override
    public UserDetails loadUserByUsername(String legajo) throws UsernameNotFoundException{
        Agencia agencia = agenciaRepository.buscarPorLegajo(legajo);
        
        if (agencia == null) {
            throw new UsernameNotFoundException("No hay ninguna agencia con el legajo: " + legajo);
        }
        
        User user = new User(agencia.getLegajo(), agencia.getClave(), Collections.EMPTY_LIST);
        return user;
    }
}
