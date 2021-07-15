package turismoreceptivo.web.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
public class Reserva implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private Integer personas;
    private String alojamiento;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechayhorario;

    @ManyToOne
    private Producto producto;

    @ManyToOne
    private Agencia agencia;
    
    @ManyToOne
    private Usuario usuario;

}
