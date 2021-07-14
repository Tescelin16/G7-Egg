package turismoreceptivo.web.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class Agencia implements Serializable {
	
	@Id
	private String legajo;
	
	private String nombre;
	private String telefono;
	private String direccion;
	private String email;
        
        private String clave;
	
	@OneToMany
	private List<Reserva> reservas;
        
        @ManyToOne
        private Rol rol;
}
