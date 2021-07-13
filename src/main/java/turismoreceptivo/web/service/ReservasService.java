
package turismoreceptivo.web.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import turismoreceptivo.web.entity.Agencia;
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
    private ProductoRepository productoRepository;
    @Autowired
    private AgenciaRepository agenciaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Transactional
    public void crearReserva(Integer personas, Date fechahorario, String idProducto, String idSession){
        Reserva rva=new Reserva();
        rva.setPersonas(personas);
        rva.setFechayhorario(fechahorario);
        rva.setProducto(productoRepository.getById(idProducto));
        if (agenciaRepository.findById(idSession).isPresent()) {
            Agencia agencia = agenciaRepository.findById(idSession).get();
            rva.setAgencia(agencia);
        }else{
            Integer dni = Integer.parseInt(idSession);
            Usuario usuario = usuarioRepository.findById(dni).get();
            rva.setUsuario(usuario);
        }
        reservaRepository.save(rva);   
    }
    
    @Transactional
    public void modificarReserva(String id, Integer personas, Date fechahorario){
        reservaRepository.modificar(id, personas, fechahorario);
    }
    
    @Transactional (readOnly= true)
    public List<Reserva> listarReservas(){
       return reservaRepository.findAll(); 
    }
	@Transactional(readOnly = true)
	public List<Integer> cantidad(){
		List<Integer> cantidadPersonas = new ArrayList<>();
		cantidadPersonas.add(1);
		cantidadPersonas.add(2);
		cantidadPersonas.add(3);
		cantidadPersonas.add(4);
		cantidadPersonas.add(5);
		cantidadPersonas.add(6);
		return cantidadPersonas;
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
    public List<Reserva> buscarPorFecha(Date fechahorario){
        return reservaRepository.buscarPorFecha(fechahorario);
    }
    
    @Transactional (readOnly= true)
    public List<Reserva> buscarPorAgencia (String legajo){
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
