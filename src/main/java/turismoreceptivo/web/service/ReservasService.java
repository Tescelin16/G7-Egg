
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
import turismoreceptivo.web.repository.ReservaRepository;



@Service
public class ReservasService {
     
    @Autowired
    private ReservaRepository reservaRepository;
    
    @Transactional
    public void crearReserva(String id,int personas, LocalDateTime fechahorario, Producto producto, List<Pasajero> pasajeros,
    Usuario usuario, Agencia agencia){
        Reserva rva=new Reserva();
        rva.setId(id);
        rva.setPersonas(personas);
        rva.setFechayhorario(fechahorario);
        rva.setProducto(producto);
        rva.setPasajeros(pasajeros);
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
    public List<Reserva> buscarPorFecha(LocalDateTime fechahorario){
        return reservaRepository.buscarPorFecha(fechahorario);
    }
}
