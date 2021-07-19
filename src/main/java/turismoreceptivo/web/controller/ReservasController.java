package turismoreceptivo.web.controller;

import java.util.Date;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import turismoreceptivo.web.entity.Agencia;
import turismoreceptivo.web.entity.Producto;
import turismoreceptivo.web.entity.Reserva;
import turismoreceptivo.web.entity.Usuario;
import turismoreceptivo.web.error.ErrorService;
import turismoreceptivo.web.service.EmailService;
import turismoreceptivo.web.service.ProductoService;
import turismoreceptivo.web.service.ReservasService;

@Controller
@RequestMapping("/reservas")
public class ReservasController {

    @Autowired
    private ReservasService reservasService;
    @Autowired
    private ProductoService productoService;
    @Autowired
    private EmailService emailService;

    @GetMapping
    public ModelAndView listarReservas() {
        ModelAndView mav = new ModelAndView("reservas");
        mav.addObject("reservas", reservasService.listarReservas());
        return mav;
    }

    @GetMapping("/reservasPropia")
    public ModelAndView buscarReserva(HttpSession session) {
        ModelAndView mav = new ModelAndView("reservas-propia");
        String idSession = (String) session.getAttribute("id");
        if (reservasService.objetoAgencia(idSession) != null) {
            Agencia agencia = reservasService.objetoAgencia(idSession);
            mav.addObject("reservas", reservasService.buscarPorAgenciaId(agencia.getLegajo()));
        } else {
            Usuario usuario = reservasService.objetoUsuario(idSession);
            mav.addObject("reservas", reservasService.buscarPorUsuarioId(usuario.getDni()));
        }

        return mav;
    }

    @GetMapping("/crear/{idProducto}/{id}")
    public ModelAndView crearReserva(@PathVariable String idProducto, @PathVariable String id, HttpSession session) {
        String idSession = (String) session.getAttribute("id");
        ModelAndView mav = new ModelAndView("reserva-formulario");
        Reserva reserva=new Reserva();
        reserva.setProducto(productoService.buscarPorId(idProducto));
        mav.addObject("reserva", reserva);
        if (reservasService.objetoAgencia(idSession) != null) {
            mav.addObject("cantPersonas", reservasService.cantidadAgencia());
        } else {
            mav.addObject("cantPersonas", reservasService.cantidadUsuario());
        }
        mav.addObject("title", "Crear Reserva");
        mav.addObject("action", "guardar");
        return mav;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editarReserva(@PathVariable String id, HttpSession session) {
        String idSession = (String) session.getAttribute("id");
        ModelAndView mav = new ModelAndView("reserva-formulario");
        mav.addObject("reserva", reservasService.buscarPorId(id));
        if (reservasService.objetoAgencia(idSession) != null) {
            mav.addObject("cantPersonas", reservasService.cantidadAgencia());
        } else {
            mav.addObject("cantPersonas", reservasService.cantidadUsuario());
        }
        mav.addObject("title", "Editar Agencia");
        mav.addObject("action", "modificar");
        return mav;
    }

    @PostMapping("/guardar")
    public RedirectView guardar(@RequestParam Integer personas,@RequestParam String alojamiento, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechahorario,
            @RequestParam Producto producto, HttpSession session) {
        String idSession = (String) session.getAttribute("id");
        
        reservasService.crearReserva(personas,alojamiento, fechahorario, producto, idSession);

        if (reservasService.objetoAgencia(idSession) != null) {
            Agencia agencia = reservasService.objetoAgencia(idSession);
            emailService.enviarCorreo(agencia.getEmail(), "Confirmación de reserva realizada", "Usted ha realizado una reserva para el dia "
                    + fechahorario + " a partir de las "+ producto.getHorario()+", para " + personas + " personas."
                    + "/n La reserva es para la excursion " + producto.getTitulo() + " con un valor de: $" + producto.getPrecio());
        } else {
            Usuario usuario = reservasService.objetoUsuario(idSession);
            emailService.enviarCorreo(usuario.getEmail(), "Confirmación de reserva realizada", "Usted ha realizado una reserva para el dia "
                    + fechahorario + "a partir de las "+ producto.getHorario()+", para " +personas + " personas."
                    + "/n La reserva es para la excursion " + producto.getTitulo() + " con un valor de: $" + producto.getPrecio());
        }

        return new RedirectView("/reservas/reservasPropia");
    }

    @PostMapping("/modificar")
    public RedirectView modificar(@RequestParam String id, @RequestParam Integer personas,@RequestParam String alojamiento, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechahorario) {
        reservasService.modificarReserva(id, personas,alojamiento,fechahorario);
        return new RedirectView("/reservas");
    }

    @PostMapping("/eliminar/{id}")
    public RedirectView eliminar(@PathVariable String id) throws ErrorService {
        reservasService.eliminarReserva(id);
        return new RedirectView("/reservas/reservasPropia");
    }
}
