package turismoreceptivo.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/index")
public class PrincipalController {

    @GetMapping
    public ModelAndView inicio() {
        return new ModelAndView("index");
    }

    @GetMapping("/error-403")
    public ModelAndView error() {
        return new ModelAndView("error");
    }

}
