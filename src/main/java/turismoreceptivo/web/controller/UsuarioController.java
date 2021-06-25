package turismoreceptivo.web.controller;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import turismoreceptivo.web.entity.Usuario;
import turismoreceptivo.web.service.UsuarioService;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ModelAndView mostrarUsuarios() {
        ModelAndView mav = new ModelAndView("usuarios");
        mav.addObject("usuarios", usuarioService.buscarTodos());
        return mav;
    }

    @GetMapping("/editar/{dni}")
    public ModelAndView editarUsuario(@PathVariable Integer dni) {
        ModelAndView mav = new ModelAndView("usuario-formulario");
        mav.addObject("usuario", usuarioService.buscarPorDni(dni));
        mav.addObject("title", "Editar Usuario");
        mav.addObject("action", "modificar");
        return mav;
    }


    @PostMapping("/modificar")
    public RedirectView modificarUsuario(@RequestParam Integer dni, @RequestParam String nombre, @RequestParam String apellido, @RequestParam String email, @RequestParam String telefono,
            @RequestParam String telefono2, @RequestParam String alojamiento, @RequestParam Date fechaNacimiento) {
        usuarioService.modificarUsuario(dni, nombre, apellido, email, telefono, telefono2, alojamiento, fechaNacimiento);
        return new RedirectView("/usuarios");
    }
    
    @PostMapping("/eliminar/{dni}")
    public RedirectView eliminarUsuario(@PathVariable Integer dni) {
        usuarioService.eliminar(dni);
        return new RedirectView("/usuarios");
    }
    
    @GetMapping
	public ModelAndView iniciarSesion(){
		ModelAndView mav = new ModelAndView("");
		//mav.addObject("agencias", agenciaService.iniciarSesion());
		mav.addObject("title", "Iniciar Sesion");
		mav.addObject("action", "login");
		return mav;
	}
        
    @PostMapping("/login")
	public RedirectView loguear(@RequestParam String mailUser, @RequestParam String contrasenia){
		//agenciaService.iniciarSesion(mailUser, contrasenia);
		return new RedirectView("/agencia");
	}
}
