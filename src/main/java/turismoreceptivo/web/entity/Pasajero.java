package turismoreceptivo.web.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Pasajero implements Serializable {

	@Id
	private Integer dni;
	
	private String nombre;
	private String apellido;
	private String telefono;
	
	@ManyToOne
	private Reserva reserva;
	
}
