package turismoreceptivo.web.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import turismoreceptivo.web.entity.Producto;
import turismoreceptivo.web.error.ErrorService;
import turismoreceptivo.web.repository.ProductoRepository;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository pR;

    @Transactional
    public void crearProduto(String descripcion, String titulo, String horario,
             String dias, Double duracion, Integer precio) throws ErrorService {

        validacion(descripcion, titulo, horario, dias, duracion);

        Producto producto = new Producto();

        producto.setDescripcion(descripcion);
        producto.setTitulo(titulo);
        producto.setHorario(horario);
        producto.setDias(dias);
        producto.setDuracion(duracion);
        producto.setPrecio(precio);

        pR.save(producto);
    }

    public void validacion(String descripcion, String titulo, String horario,
             String dias, Double duracion) throws ErrorService {

        if (descripcion == null || descripcion.isEmpty()) {
            throw new ErrorService("La descripcion de la actividad es necesaria");
        }

        if (titulo == null || titulo.isEmpty()) {
            throw new ErrorService("El titulo no puede estar vacio");
        }

        if (horario == null || horario.isEmpty()) {
            throw new ErrorService("La ubicacioón no puede estar vacio");
        }

        if (dias == null || dias.isEmpty()) {
            throw new ErrorService("Debe colocar los días que se realizan la actividad");
        }

        if (duracion == null) {
            throw new ErrorService("Debe ingresar la duración mínima de la actividad");
        }

    }

    @Transactional
    public void modificarProducto(String id, String descripcion, String titulo, String horario,
             String dias, Double duracion, Integer precio) throws ErrorService {

        validacion(descripcion, titulo, horario, dias, duracion);

        pR.modificar(id, descripcion, titulo, horario, dias, precio, duracion);
    }

    @Transactional
    public void eliminar(String id) throws ErrorService {
        Optional<Producto> respuesta = pR.findById(id);

        if (respuesta.isPresent()) {
            pR.deleteById(id);
        } else {
            throw new ErrorService("La actividad no fue encontrada");
        }
    }

    @Transactional(readOnly = true)
    public Producto buscarPorId(String id) {
        return pR.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Producto> buscarTodo() {
        return pR.findAll();
    }

}
