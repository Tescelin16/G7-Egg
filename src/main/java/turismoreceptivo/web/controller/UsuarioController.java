package turismoreceptivo.web.controller;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import turismoreceptivo.web.entity.Usuario;
import turismoreceptivo.web.error.ErrorService;
import turismoreceptivo.web.service.UsuarioService;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/mostrar-usuario")
    public ModelAndView mostrarUsuarios() {
        ModelAndView mav = new ModelAndView("usuarios");
        mav.addObject("usuarios", usuarioService.buscarTodos());
        return mav;
    }

    @GetMapping("/registro")
    public ModelAndView registro() {
        ModelAndView mav = new ModelAndView("registro-usuario");
        mav.addObject("usuario", new Usuario());
        mav.addObject("title", "Registrar Usuario");
        mav.addObject("action", "guardar");
        return mav;
    }

    @GetMapping("/editar/{dni}")
    public ModelAndView editarUsuario(@PathVariable Integer dni) {
        ModelAndView mav = new ModelAndView("registro-usuario");
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

    @GetMapping("login-usuario")
    public ModelAndView iniciarSesion() {
        ModelAndView mav = new ModelAndView("");
        mav.addObject("title", "Iniciar Sesion");
        mav.addObject("action", "login");
        return mav;
    }

    @PostMapping("/login")
    public RedirectView loguear(@RequestParam String mailUser, @RequestParam String contrasenia) {
        return new RedirectView("/agencia");
    }

    @PostMapping("/guardar")
    public RedirectView guardarUsuario(RedirectAttributes attributes, @RequestParam Integer dni, @RequestParam String nombre, @RequestParam String apellido, @RequestParam String email, @RequestParam String telefono,
            @RequestParam String telefono2, @RequestParam String alojamiento, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaNacimiento, @RequestParam String username, @RequestParam String clave) {
        try {
            usuarioService.crearUsuario(dni, nombre, apellido, email, telefono, telefono2, alojamiento, fechaNacimiento, username, clave);
            attributes.addFlashAttribute("registroExitoso", "El usuario fue creado con Exito");
        } catch (ErrorService e) {
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
            return new RedirectView("/registro-usuario");
        }
        return new RedirectView("/index");
    }
}
