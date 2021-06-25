package turismoreceptivo.web.controller;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import turismoreceptivo.web.entity.Usuario;
import turismoreceptivo.web.error.ErrorService;
import turismoreceptivo.web.service.UsuarioService;

@Controller
public class PrincipalController {
    
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ModelAndView inicio() {
        return new ModelAndView("index");
    }

    @GetMapping
    public ModelAndView quienesSomos() {
        return new ModelAndView("quienes-somos");
    }

    @GetMapping("/login")
    public ModelAndView login(@RequestParam(required = false) String error, ModelAndView mav) {
        if (error != null) {
            mav.addObject("errorLogin", "Usuario o Contraseña incorrecto");
        }
        return new ModelAndView("loign");
    }

    @GetMapping("/registro")
    public ModelAndView registro() {
        ModelAndView mav = new ModelAndView("usuario-formulario");
        mav.addObject("usuario", new Usuario());
        mav.addObject("title", "Registrar Usuario");
        mav.addObject("action", "Registar");
        return mav;
    }

    @PostMapping("/guardar")
    public RedirectView guardarUsuario(RedirectAttributes attributes, @RequestParam Integer dni, @RequestParam String nombre, @RequestParam String apellido, @RequestParam String email, @RequestParam String telefono,
            @RequestParam String telefono2, @RequestParam String alojamiento, @RequestParam Date fechaNacimiento, @RequestParam String username, @RequestParam String clave) {
        try{
            usuarioService.crearUsuario(dni, nombre, apellido, email, telefono, telefono2, alojamiento, fechaNacimiento, username, clave);
            attributes.addFlashAttribute("registroExitoso", "El usuario fue creado con Exito");
        }catch(ErrorService e){
            attributes.addFlashAttribute("error", e.getMessage());
            attributes.addFlashAttribute("dni", dni);
            attributes.addFlashAttribute("nombre", nombre);
            attributes.addFlashAttribute("apellido", apellido);
            attributes.addFlashAttribute("email", email);
            attributes.addFlashAttribute("telefono", telefono);
            attributes.addFlashAttribute("telefono2", telefono2);
            attributes.addFlashAttribute("alojamiento", alojamiento);
            attributes.addFlashAttribute("fechaNacimiento", fechaNacimiento);
            attributes.addFlashAttribute("username", username);
            attributes.addFlashAttribute("clave", clave);
            return new RedirectView("/registro");
        }
            return new RedirectView("/login");
    }

}
