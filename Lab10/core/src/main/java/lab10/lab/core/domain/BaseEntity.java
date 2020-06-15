package lab10.lab.core.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

import lombok.*;

@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BaseEntity<ID extends Serializable> implements Serializable {

    @GeneratedValue
    @Id
    private ID id;

}
