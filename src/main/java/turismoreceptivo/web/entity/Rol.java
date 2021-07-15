package turismoreceptivo.web.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Rol implements Serializable {
    
@Id
private String idRol;

private String nombre;

}