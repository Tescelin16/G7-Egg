package turismoreceptivo.web.entity;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
public class Reserva {
	
	@Id
	@GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	
	private int personas;
	
	//@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime fechayhorario;
	
	private Producto producto;
	
	@OneToMany
	private List<Pasajero> pasajeros;
	
	@ManyToOne
	private Usuario usuario;
	
	@ManyToOne
	private Agencia agencia;
	
}
