package lab11.lab.core.domain;

import java.io.Serializable;

import javax.persistence.Entity;

import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class Movie extends BaseEntity<Long> implements Serializable {
    private String serialNumber;
    private String name;
    private int year;
    private int rating; //out of 100

}
