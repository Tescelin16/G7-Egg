package turismoreceptivo.web.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;

@Data
@Entity
public class Usuario implements Serializable {
	
	@Id
	private Integer dni;
	
	private String nombre;
	private String apellido;
	private String email;
	private String telefono;
	private String telefono2;
	
	
	@Temporal(TemporalType.DATE)
	private Date fechaNacimiento;
	
	@OneToMany
	private List<Reserva> reservas;
        
        @Column(unique = true)
        private String username;
        private String clave;
        
        @ManyToOne
        private Rol rol;
}
