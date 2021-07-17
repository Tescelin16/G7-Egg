package turismoreceptivo.web.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
public class Producto implements Serializable {

	@Id
	@GeneratedValue(generator = "uuid")
        @GenericGenerator(name = "uuid", strategy = "uuid2")
	private String idProducto;
	
	private String descripcion;
	private String titulo;
	private String horario;
	private String dias;
	private double duracion;
	private Integer precio;
        
}
