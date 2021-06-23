
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
import turismoreceptivo.web.entity.Agencia;
import turismoreceptivo.web.entity.Pasajero;
import turismoreceptivo.web.entity.Producto;
import turismoreceptivo.web.entity.Reserva;
import turismoreceptivo.web.entity.Usuario;
import turismoreceptivo.web.error.ErrorService;
import turismoreceptivo.web.service.AgenciaService;
import turismoreceptivo.web.service.PasajeroService;
import turismoreceptivo.web.service.ReservasService;

@Controller
@RequestMapping("/reservas")
public class ReservasController {
  
    @Autowired 
    private AgenciaService agenciaService; 
    @Autowired
    private ReservasService reservasService;
    @Autowired
    private PasajeroService pasajeroService;
    
    @GetMapping
    public ModelAndView buscarReserva(@RequestParam Integer legajo){
        ModelAndView mav = new ModelAndView("reservasAgencia");
        mav.addObject("reservas", reservasService.buscarPorAgenciaId(legajo));
        return mav;
    }
    @GetMapping
    public ModelAndView buscarPasajeros(@RequestParam String id){
        ModelAndView mav = new ModelAndView("pasajerosReserva");
        mav.addObject("pasajeros", pasajeroService.buscarPorReserva(id));
        return mav;
    }
    @GetMapping("/crear")
    public ModelAndView crearReserva(){
        ModelAndView mav = new ModelAndView("reserva-formulario");
        mav.addObject("agencia", new Reserva());
        mav.addObject("title", "Crear Reserva");
        mav.addObject("action", "guardar");
        return mav;
    }
    @GetMapping("/editar/{id}")
    public ModelAndView editarReserva(@PathVariable String id){
        ModelAndView mav = new ModelAndView("reserva-formulario");
        mav.addObject("reserva", reservasService.buscarPorId(id));
        mav.addObject("title", "Editar Agencia");
        mav.addObject("action", "modificar");
        return mav;
    }
    
    @PostMapping("/guardar")
    public RedirectView guardar(@RequestParam String id,@RequestParam int personas,@RequestParam LocalDateTime fechahorario,
    @RequestParam String idProducto,@RequestParam List<Pasajero> pasajeros,@RequestParam
    Integer dni,@RequestParam Integer legajo){
        reservasService.crearReserva(id,personas,fechahorario,idProducto,pasajeros,dni,legajo);
        return new RedirectView("/reserva");
    }
    
    @PostMapping("/modificar")
    public RedirectView modificar(@RequestParam String id,@RequestParam int personas,@RequestParam LocalDateTime fechahorario){
        reservasService.modificarReserva(id,personas,fechahorario);
        return new RedirectView("/reserva");
    }
    
    @PostMapping("/eliminar/{id}")
    public RedirectView eliminar(@PathVariable String id)throws ErrorService{
        reservasService.eliminarReserva(id);
        return new RedirectView("/reserva");
    }
}
