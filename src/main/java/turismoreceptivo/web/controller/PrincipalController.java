package turismoreceptivo.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/index")
public class PrincipalController {

    @GetMapping
    public ModelAndView inicio() {
        return new ModelAndView("index");
    }

    @GetMapping("/quienes-somos")
    public ModelAndView quienesSomos() {
        return new ModelAndView("quienes-somos");
    }

    @GetMapping("/login")
    public ModelAndView login(@RequestParam(required = false) String error, ModelAndView mav) {
        if (error != null) {
            mav.addObject("errorLogin", "Usuario o Contraseña incorrecto");
        }
        return new ModelAndView("login");
    }

}
