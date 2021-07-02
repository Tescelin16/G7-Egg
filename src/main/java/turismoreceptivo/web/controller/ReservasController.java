
package turismoreceptivo.web.controller;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import turismoreceptivo.web.entity.Pasajero;
import turismoreceptivo.web.entity.Reserva;
import turismoreceptivo.web.error.ErrorService;
import turismoreceptivo.web.service.AgenciaService;
import turismoreceptivo.web.service.PasajeroService;
import turismoreceptivo.web.service.ProductoService;
import turismoreceptivo.web.service.ReservasService;
import turismoreceptivo.web.service.UsuarioService;

@Controller
@RequestMapping("/reservas")
public class ReservasController {
  
    @Autowired
    private ReservasService reservasService;
    @Autowired
    private UsuarioService usuarioService;
	@Autowired
	private AgenciaService agenciaService;
	@Autowired
	private ProductoService productoService;
    
    @GetMapping
    public ModelAndView buscarReserva(@RequestParam Integer legajo){
        ModelAndView mav = new ModelAndView("reservasAgencia");
        mav.addObject("reservas", reservasService.buscarPorAgenciaId(legajo));
        return mav;
    }
	/*
    @GetMapping("/buscar-pasajero")
    public ModelAndView buscarPasajeros(@RequestParam String id){
        ModelAndView mav = new ModelAndView("pasajerosReserva");
        mav.addObject("pasajeros", pasajeroService.buscarPorReserva(id));
        return mav;
    }
	*/
    @GetMapping("/crear/{id}/{legajo}/{idP}")
    public ModelAndView crearReserva(@PathVariable Integer id, @PathVariable String legajo, @PathVariable String idP){
        ModelAndView mav = new ModelAndView("reserva-formulario");
        mav.addObject("reserva", new Reserva());
		mav.addObject("cantPersonas", reservasService.cantidad());
		mav.addObject("usuario", usuarioService.buscarPorDni(id));
		mav.addObject("agencia", agenciaService.buscarPorLegajo(legajo));
		mav.addObject("producto", productoService.buscarPorId(idP));
        mav.addObject("title", "Crear Reserva");
        mav.addObject("action", "guardar");
        return mav;
    }
    @GetMapping("/editar/{id}")
    public ModelAndView editarReserva(@PathVariable String id){
        ModelAndView mav = new ModelAndView("reserva-formulario");
        mav.addObject("reserva", reservasService.buscarPorId(id));		
		mav.addObject("cantPersonas", reservasService.cantidad());
        mav.addObject("title", "Editar Agencia");
        mav.addObject("action", "modificar");
        return mav;
    }
    
    @PostMapping("/guardar")
    public RedirectView guardar(@RequestParam int personas,@RequestParam LocalDateTime fechahorario,
    @RequestParam String idProducto,@RequestParam List<Pasajero> pasajeros,@RequestParam
    Integer dni,@RequestParam String legajo){
        reservasService.crearReserva(personas,fechahorario,idProducto,pasajeros,dni, legajo);
        return new RedirectView("/reserva");
    }
    
    @PostMapping("/modificar")
    public RedirectView modificar(@RequestParam int personas,@RequestParam LocalDateTime fechahorario){
        reservasService.modificarReserva(personas,fechahorario);
        return new RedirectView("/reserva");
    }
    
    @PostMapping("/eliminar/{id}")
    public RedirectView eliminar(@PathVariable String id)throws ErrorService{
        reservasService.eliminarReserva(id);
        return new RedirectView("/reserva");
    }
}
