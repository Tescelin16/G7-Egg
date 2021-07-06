package turismoreceptivo.web.controller;

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
import turismoreceptivo.web.service.AgenciaService;

@Controller
@RequestMapping("/agencia")
public class AgenciaController {

	@Autowired
	private AgenciaService agenciaService;
	
	@GetMapping("/login-agencia")
	public ModelAndView iniciarSesion(){
		ModelAndView mav = new ModelAndView("login");
		mav.addObject("title", "Iniciar Sesion");
		mav.addObject("action", "login");
		return mav;
	}
	
	@PostMapping("/login")
	public RedirectView loguear(){
		return new RedirectView("/index");
	}
	@GetMapping
	public ModelAndView mostrarTodos(){
		ModelAndView mav = new ModelAndView("agencia");
		mav.addObject("agencias", agenciaService.buscarTodos());
		return mav;
	}
	
	@GetMapping("/crear")
	public ModelAndView crear(){
		ModelAndView mav = new ModelAndView("registro-agencia");
		mav.addObject("agencia", new Agencia());
		mav.addObject("title", "Crear Agencia");
		mav.addObject("action", "guardar");
		return mav;
	}
	
	@PostMapping("/guardar")
	public RedirectView guardar(@RequestParam String legajo, @RequestParam String nombre, @RequestParam String telefono, @RequestParam String direccion, @RequestParam String email, @RequestParam String clave){
		agenciaService.crear(legajo, nombre, telefono, direccion, email, clave);
		return new RedirectView("/index");
	}
	
	@GetMapping("/editar/{legajo}")
	public ModelAndView editar(@PathVariable String legajo){
		ModelAndView mav = new ModelAndView("registro-agencia");
		mav.addObject("agencia", agenciaService.buscarPorLegajo(legajo));
		mav.addObject("title", "Editar Agencia");
		mav.addObject("action", "modificar");
		return mav;
	}
	
	@PostMapping("/modificar")
	public RedirectView modificar(@RequestParam String legajo, @RequestParam String nombre, @RequestParam String telefono, @RequestParam String direccion, @RequestParam String email, @RequestParam String clave){
		agenciaService.modificar(legajo, nombre, telefono, direccion, email, clave);
		return new RedirectView("/agencia");
	}
	
	@PostMapping("/eliminar/{legajo}")
	public RedirectView eliminar(@PathVariable String legajo){
		agenciaService.eliminar(legajo);
		return new RedirectView("/agencia");
	}

}
