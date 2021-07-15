package turismoreceptivo.web.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import turismoreceptivo.web.entity.Rol;
import turismoreceptivo.web.repository.RolRepository;

@Service
public class RolService {

    @Autowired
    private RolRepository rolRepository;
    
    @Transactional
    public void crear(String nombre, String idRol){
        Rol rol = new Rol();
        rol.setNombre(nombre);
        rol.setIdRol(idRol);
        rolRepository.save(rol);
    }
    
    @Transactional(readOnly = true)
    public List<Rol> buscarTodos(){
        return rolRepository.findAll();
    }

}
