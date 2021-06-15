
package turismoreceptivo.web.service;

import java.time.LocalDateTime;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import turismoreceptivo.web.entity.Agencia;
import turismoreceptivo.web.entity.Pasajero;
import turismoreceptivo.web.entity.Producto;
import turismoreceptivo.web.entity.Reserva;
import turismoreceptivo.web.entity.Usuario;
import turismoreceptivo.web.repository.ReservaRepository;



@Service
public class ReservasService {
     
    @Autowired
    private ReservaRepository rr;
    
    @Transactional
    public void crearReserva(String id,int personas, LocalDateTime fechahorario, Producto producto, List<Pasajero> pasajeros,
    Usuario usuario, Agencia agencia){
        Reserva rva=new Reserva();
        rva.setId(id);
        rva.setPersonas(personas);
        rva.setFechayhorario(fechahorario);
        rva.setProducto(producto);
        rva.setPasajeros(pasajeros);
        rr.save(rva);   
    }
    
    @Transactional
    public void modificarReserva(String id,int personas, LocalDateTime fechahorario){
        rr.modificar(id, personas, fechahorario);
    }
    
    @Transactional
    public List<Reserva> listarReservas(){
       return rr.findAll(); 
    }
    
    @Transactional
    public void eliminarReserva (String id){
        rr.deleteById(id);
    }
    
    @Transactional
    public List<Reserva> buscarPorFecha(LocalDateTime fechahorario){
        return rr.buscarPorFecha(fechahorario);
    }
}
