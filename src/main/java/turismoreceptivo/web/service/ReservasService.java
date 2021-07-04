
package turismoreceptivo.web.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import turismoreceptivo.web.entity.Agencia;
import turismoreceptivo.web.entity.Pasajero;
import turismoreceptivo.web.entity.Producto;
import turismoreceptivo.web.entity.Reserva;
import turismoreceptivo.web.entity.Usuario;
import turismoreceptivo.web.error.ErrorService;
import turismoreceptivo.web.repository.AgenciaRepository;
import turismoreceptivo.web.repository.ProductoRepository;
import turismoreceptivo.web.repository.ReservaRepository;
import turismoreceptivo.web.repository.UsuarioRepository;



@Service
public class ReservasService {
     
    @Autowired
    private ReservaRepository reservaRepository;
    @Autowired
    private AgenciaRepository agenciaRepository;
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Transactional
    public void crearReserva(String id,int personas, LocalDateTime fechahorario, String idProducto, List<Pasajero> pasajeros,
    Integer dni, String legajo){
        Reserva rva=new Reserva();
        rva.setId(id);
        rva.setPersonas(personas);
        rva.setFechayhorario(fechahorario);
        rva.setProducto(productoRepository.getById(idProducto));
        rva.setPasajeros(pasajeros);
        rva.setUsuario(usuarioRepository.getById(dni));
        rva.setAgencia(agenciaRepository.buscarPorLegajo(legajo));
        reservaRepository.save(rva);   
    }
    
    @Transactional
    public void modificarReserva(String id,int personas, LocalDateTime fechahorario){
        reservaRepository.modificar(id, personas, fechahorario);
    }
    
    @Transactional (readOnly= true)
    public List<Reserva> listarReservas(){
       return reservaRepository.findAll(); 
    }
    
    @Transactional
    public void eliminarReserva (String id) throws ErrorService{
        Optional<Reserva> resp=reservaRepository.findById(id);
        if (resp.isPresent()){
            reservaRepository.deleteById(id);
        }else{
           throw new ErrorService("La reserva no fue encontrada");
        }
    }
    @Transactional (readOnly= true)
    public Reserva buscarPorId(String id){
       return reservaRepository.findById(id).orElse(null); 
    }
    @Transactional (readOnly= true)
    public List<Reserva> buscarPorFecha(LocalDateTime fechahorario){
        return reservaRepository.buscarPorFecha(fechahorario);
    }
    
    @Transactional (readOnly= true)
    public List<Reserva> buscarPorAgencia (Integer legajo){
        Optional<Agencia> resp=agenciaRepository.findById(legajo);
        if(resp.isPresent()){
            Agencia agencia=agenciaRepository.getById(legajo);
            return reservaRepository.buscarPorAgencia(agencia);
        }
    return null;
}
    @Transactional (readOnly= true)
    public List<Reserva> buscarPorAgenciaId (String legajo){
        return reservaRepository.buscarPorAgenciaId(legajo);
    }
}
