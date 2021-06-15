package turismoreceptivo.web.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
public class Producto {
	
	@Id
	@GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	
	private String descripcion;
	private String titulo;
	private String ubicacion;
	private String dias;
	private double duracion;
	private Integer precio;
	
}
