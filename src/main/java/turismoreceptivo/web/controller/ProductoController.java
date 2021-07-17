
package turismoreceptivo.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import turismoreceptivo.web.entity.Producto;
import turismoreceptivo.web.error.ErrorService;
import turismoreceptivo.web.service.ProductoService;


@Controller
@RequestMapping("/Producto")
public class ProductoController {
    
    @Autowired
    private ProductoService productoService;
    
    @GetMapping
    public ModelAndView mostrarTodosProductos(){
        ModelAndView mav = new ModelAndView("productos");
        mav.addObject("productos", productoService.buscarTodo());
        return mav;
    }
    
    @GetMapping("/mostrar-producto/{idProducto}")
    public ModelAndView mostrarProducto(@PathVariable String idProducto){
        ModelAndView mav = new ModelAndView("producto-individual");
        mav.addObject("producto", productoService.buscarPorId(idProducto));
        return mav;
    }
    
    @GetMapping("/crear")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView crearProducto(){
        ModelAndView mav = new ModelAndView("producto-formulario");
        mav.addObject("producto", new Producto());
        mav.addObject("title", "Crear Producto");
        mav.addObject("action", "guardar");
        return mav;
    }
    
    @PostMapping("/guardar")
    public RedirectView guardar(@RequestParam String descripcion, @RequestParam String titulo, @RequestParam String horario, @RequestParam String dias, @RequestParam Double duracion, @RequestParam Integer precio) throws ErrorService{
        productoService.crearProduto(descripcion, titulo, horario, dias, duracion, precio);
        return new RedirectView("/Producto");
    }
    
    @GetMapping("/editar/{idProducto}")
    public ModelAndView editarProducto(@PathVariable String idProducto){
        ModelAndView mav = new ModelAndView("producto-formulario");
        mav.addObject("producto", productoService.buscarPorId(idProducto));
        mav.addObject("title", "Editar Producto");
        mav.addObject("action", "modificar");
        return mav;
    }
    
    @PostMapping("/modificar")
    public RedirectView modificar(@RequestParam String idProducto, @RequestParam String descripcion, @RequestParam String titulo, @RequestParam String horario, @RequestParam String dias, @RequestParam Double duracion, @RequestParam Integer precio) throws ErrorService{
        productoService.modificarProducto(idProducto, descripcion, titulo, horario, dias, duracion, precio);
        return new RedirectView("/Producto");
    }
    
    @PostMapping("/eliminar/{idProducto}")
    public RedirectView eliminar(@PathVariable String idProducto) throws ErrorService{
        productoService.eliminar(idProducto);
        return new RedirectView("/Producto");
    }
    
}
