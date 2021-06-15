package turismoreceptivo.web.entity;

import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
public class Usuario {
	
	@Id
	private Integer dni;
	
	private String nombre;
	private String apellido;
	private String email;
	private String telefono;
	private String telefono2;
	private String alojamiento;
	
	@Temporal(TemporalType.DATE)
	private Date fechaNacimiento;
	
	@OneToMany
	private List<Reserva> reservas;
}
