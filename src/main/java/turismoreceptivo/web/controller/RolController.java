package turismoreceptivo.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import turismoreceptivo.web.service.RolService;

@Controller
@RequestMapping("/roles")
public class RolController {

    @Autowired
    private RolService rolService;
    
    
    @GetMapping("/crear")
    public ModelAndView crearRol(){
        return new ModelAndView("rol-formulario");
    }
    
    @PostMapping("/guardar")
    public RedirectView guardar(@RequestParam String nombre, @RequestParam String idRol){
        rolService.crear(nombre, idRol);
        return new RedirectView("/index");
    }
    
}