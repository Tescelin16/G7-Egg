
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
        ModelAndView mav = new ModelAndView("Producto");
        mav.addObject("productos", productoService.buscarTodo());
        return mav;
    }
    
    @GetMapping("/mostrar-producto/{id}")
    public ModelAndView mostrarProducto(@PathVariable String id){
        ModelAndView mav = new ModelAndView("ver-producto");
        mav.addObject("producto", productoService.buscarPorId(id));
        return mav;
    }
    
    @GetMapping("/crear")
    public ModelAndView crearProducto(){
        ModelAndView mav = new ModelAndView("produto-formualario");
        mav.addObject("producto", new Producto());
        mav.addObject("title", "Crear Producto");
        mav.addObject("action", "guardar");
        return mav;
    }
    
    @PostMapping("/guardar")
    public RedirectView guardar(@RequestParam String descripcion, @RequestParam String titulo, @RequestParam String ubicacion, @RequestParam String dias, @RequestParam Double duracion, @RequestParam Integer precio) throws ErrorService{
        productoService.crearProduto(descripcion, titulo, ubicacion, dias, duracion, precio);
        return new RedirectView("/Producto");
    }
    
    @GetMapping("/editar/{id}")
    public ModelAndView editarProducto(@PathVariable String id){
        ModelAndView mav = new ModelAndView("producto-formulario");
        mav.addObject("producto", productoService.buscarPorId(id));
        mav.addObject("title", "Editar Producto");
        mav.addObject("action", "modificar");
        return mav;
    }
    
    @PostMapping("/modificar")
    public RedirectView modificar(@RequestParam String id, @RequestParam String descripcion, @RequestParam String titulo, @RequestParam String ubicacion, @RequestParam String dias, @RequestParam Double duracion) throws ErrorService{
        productoService.modificarProducto(id, descripcion, titulo, ubicacion, dias, duracion);
        return new RedirectView("/Producto");
    }
    
    @PostMapping("/eliminar/{id}")
    public RedirectView eliminar(@PathVariable String id) throws ErrorService{
        productoService.eliminar(id);
        return new RedirectView("/Producto");
    }
    
}
