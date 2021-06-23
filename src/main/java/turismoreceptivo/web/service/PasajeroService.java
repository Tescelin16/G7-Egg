package turismoreceptivo.web.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import turismoreceptivo.web.entity.Pasajero;
import turismoreceptivo.web.repository.PasajeroRepository;

@Service
public class PasajeroService {

    @Autowired
    private PasajeroRepository rr;

    @Transactional
    public void crearPasajero(Integer dni, String nombre, String apellido, String telefono) {
        Pasajero pasajero = new Pasajero();
        pasajero.setDni(dni);
        pasajero.setNombre(nombre);
        pasajero.setApellido(apellido);
        pasajero.setTelefono(telefono);
        rr.save(pasajero);
    }

    @Transactional
    public void modificarPasajero(Integer dni, String nombre, String apellido, String telefono) {
        rr.modificar(dni, nombre, apellido, telefono);

    }

    @Transactional(readOnly = true)
    public List<Pasajero> buscarTodos() {
        return rr.findAll();
    }

    @Transactional(readOnly = true)
    public Pasajero buscarPorDni(Integer dni) {
        return rr.findById(dni).orElse(null);
    }
    @Transactional(readOnly = true)
    public List<Pasajero> buscarPorReserva(String id) {
        return rr.buscarPasajerosReserva(id);
    }

    @Transactional
    public void eliminarPasajero(Integer dni) {
        Optional<Pasajero> eliminado = rr.findById(dni);
        if (eliminado.isPresent()) {
            rr.deleteById(dni);
        }
    }
}
