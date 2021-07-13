package turismoreceptivo.web.service;

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
    public void crear(String nombre){
        Rol rol = new Rol();
        rol.setNombre(nombre);
        rolRepository.save(rol);
    }

}
