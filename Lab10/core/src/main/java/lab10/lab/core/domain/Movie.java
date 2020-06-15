package lab10.lab.core.domain;

import java.io.Serializable;

import javax.persistence.Entity;

import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class Movie extends BaseEntity<Long> implements Serializable {
    private String serialNumber;
    private String name;
    private int year;
    private int rating; //out of 100

}
