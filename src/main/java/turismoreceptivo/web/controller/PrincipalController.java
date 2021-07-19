package turismoreceptivo.web.controller;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import turismoreceptivo.web.entity.Agencia;
import turismoreceptivo.web.entity.Usuario;
import turismoreceptivo.web.service.ReservasService;

@Controller
@RequestMapping("/index")
public class PrincipalController {
	
	@Autowired
	private ReservasService reservaService;

    @GetMapping
    public ModelAndView inicio() {
        return new ModelAndView("index");
    }

    @GetMapping("/error-403")
    public ModelAndView error() {
        return new ModelAndView("error");
    }

	@GetMapping("ver-perfil")
	public ModelAndView verPerfil(HttpSession session){
		String idSession = (String) session.getAttribute("id");
		ModelAndView mav;
		if (reservaService.objetoAgencia(idSession) != null) {
            Agencia agencia = reservaService.objetoAgencia(idSession);
			mav = new ModelAndView("ver-perfil-agencia");
            mav.addObject("reservas", agencia);
        } else {
            Usuario usuario = reservaService.objetoUsuario(idSession);
			mav = new ModelAndView("ver-perfil-usuario");
            mav.addObject("reservas", usuario);
        }
        return mav;
	}
}
