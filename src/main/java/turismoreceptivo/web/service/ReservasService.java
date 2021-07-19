package turismoreceptivo.web.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import turismoreceptivo.web.entity.Agencia;
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
    private ProductoRepository productoRepository;
    @Autowired
    private AgenciaRepository agenciaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public void crearReserva(Integer personas, String alojamiento, Date fechahorario, Producto producto, String idSession) {
        Reserva rva = new Reserva();
        rva.setPersonas(personas);
        rva.setAlojamiento(alojamiento);
        rva.setFechayhorario(fechahorario);
        rva.setProducto(producto);
        if (agenciaRepository.findById(idSession).isPresent()) {
            Agencia agencia = agenciaRepository.findById(idSession).get();
            rva.setAgencia(agencia);
        } else {
            Integer dni = Integer.parseInt(idSession);
            Usuario usuario = usuarioRepository.findById(dni).get();
            rva.setUsuario(usuario);
        }
        reservaRepository.save(rva);
    }

    @Transactional
    public void modificarReserva(String id, Integer personas,String alojamiento, Date fechahorario) {
        reservaRepository.modificar(id, personas, alojamiento, fechahorario);
    }

    @Transactional(readOnly = true)
    public List<Reserva> listarReservas() {
        return reservaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Integer> cantidadUsuario() {
        List<Integer> cantidadPersonas = new ArrayList<>();
        cantidadPersonas.add(1);
        cantidadPersonas.add(2);
        cantidadPersonas.add(3);
        cantidadPersonas.add(4);
        cantidadPersonas.add(5);
        cantidadPersonas.add(6);
        return cantidadPersonas;
    }

    @Transactional(readOnly = true)
    public List<Integer> cantidadAgencia() {
        List<Integer> cantidadPersonas = new ArrayList<>();
        cantidadPersonas.add(1);
        cantidadPersonas.add(2);
        cantidadPersonas.add(3);
        cantidadPersonas.add(4);
        cantidadPersonas.add(5);
        cantidadPersonas.add(6);
        cantidadPersonas.add(7);
        cantidadPersonas.add(8);
        cantidadPersonas.add(9);
        cantidadPersonas.add(10);
        cantidadPersonas.add(11);
        cantidadPersonas.add(12);
        cantidadPersonas.add(13);
        cantidadPersonas.add(14);
        cantidadPersonas.add(16);
        cantidadPersonas.add(16);
        cantidadPersonas.add(17);
        cantidadPersonas.add(18);
        cantidadPersonas.add(19);
        cantidadPersonas.add(20);
        return cantidadPersonas;
    }

    @Transactional
    public void eliminarReserva(String id) throws ErrorService {
        Optional<Reserva> resp = reservaRepository.findById(id);
        if (resp.isPresent()) {
            reservaRepository.deleteById(id);
        } else {
            throw new ErrorService("La reserva no fue encontrada");
        }
    }

    @Transactional(readOnly = true)
    public Reserva buscarPorId(String id) {
        return reservaRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Reserva> buscarPorFecha(Date fechahorario) {
        return reservaRepository.buscarPorFecha(fechahorario);
    }

    @Transactional(readOnly = true)
    public List<Reserva> buscarPorAgencia(String legajo) {
        Optional<Agencia> resp = agenciaRepository.findById(legajo);
        if (resp.isPresent()) {
            Agencia agencia = agenciaRepository.getById(legajo);
            return reservaRepository.buscarPorAgencia(agencia);
        }
        return null;
    }

    @Transactional(readOnly = true)
    public List<Reserva> buscarPorAgenciaId(String legajo) {
        return reservaRepository.buscarPorAgenciaId(legajo);
    }

    @Transactional(readOnly = true)
    public List<Reserva> buscarPorUsuarioId(Integer dni) {
        return reservaRepository.buscarPorUsuarioId(dni);
    }

    @Transactional(readOnly = true)
    public Agencia objetoAgencia(String idSession) {
        return agenciaRepository.findById(idSession).orElse(null);
    }

    @Transactional(readOnly = true)
    public Usuario objetoUsuario(String idSession) {
        Integer dni = Integer.parseInt(idSession);
        return usuarioRepository.findById(dni).orElse(null);
    }

    @Transactional(readOnly = true)
    public Producto objetoProducto(String idProducto) {
        Producto producto = productoRepository.findById(idProducto).orElse(null);
        return producto;
    }
}
