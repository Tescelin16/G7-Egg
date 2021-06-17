package turismoreceptivo.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import turismoreceptivo.web.entity.Agencia;
import turismoreceptivo.web.service.AgenciaService;
import turismoreceptivo.web.service.ReservasService;


@Controller
@RequestMapping("/Agencia")
public class AgenciaController{
    
    
    @Autowired 
    private AgenciaService agenciaService; 
    @Autowired
    private ReservasService reservasService;
    
    @GetMapping
    public ModelAndView buscarReserva(@RequestParam Integer legajo){
        ModelAndView mav = new ModelAndView("Agencia");
        mav.addObject("reservas", reservasService.buscarPorAgenciaId(legajo));
        return mav;
    }
    
    @GetMapping("/crear")
    public ModelAndView crearAgencia(){
        ModelAndView mav = new ModelAndView("agencia-formulario");
        mav.addObject("agencia", new Agencia());
        mav.addObject("title", "Crear Agencia");
        mav.addObject("action", "guardar");
        return mav;
    }
    
    @PostMapping("/guardar")
    public RedirectView guardar(@RequestParam Integer legajo, @RequestParam String nombre, @RequestParam String telefono, @RequestParam String direccion, @RequestParam String email){
        agenciaService.crear(legajo, nombre, telefono, direccion, email);
        return new RedirectView("/Agencia");
    }
    
}