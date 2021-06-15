package turismoreceptivo.web.entity;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class Agencia {
	
	@Id
	private Integer legajo;
	
	private String nombre;
	private String telefono;
	private String direccion;
	private String email;
	
	@OneToMany
	private List<Reserva> reservas;
	
}
