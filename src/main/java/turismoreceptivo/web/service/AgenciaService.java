package turismoreceptivo.web.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import turismoreceptivo.web.entity.Agencia;
import turismoreceptivo.web.repository.AgenciaRepository;

@Service
public class AgenciaService {

	@Autowired
	private AgenciaRepository agenciaRepository;
	
	@Transactional
	public void crear(Integer legajo, String nombre, String telefono, String direccion, String email){
		Agencia agencia = new Agencia();
		agencia.setLegajo(legajo);
		agencia.setNombre(nombre);
		agencia.setTelefono(telefono);
		agencia.setDireccion(direccion);
		agencia.setEmail(email);
		
		agenciaRepository.save(agencia);
	}
	
	@Transactional
	public void modificar(Integer legajo, String nombre, String telefono, String direccion, String email){
		agenciaRepository.modificar(legajo, nombre, telefono, direccion, email);
	}
	
	@Transactional(readOnly = true)
	public List<Agencia> buscarTodos(){
		return agenciaRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Agencia buscarPorLegajo(Integer legajo){
		Optional<Agencia> agenciaOptional = agenciaRepository.findById(legajo);
		return agenciaOptional.orElse(null);
	}
	
	@Transactional
	public void eliminar(Integer legajo){
		agenciaRepository.deleteById(legajo);
	}
}
