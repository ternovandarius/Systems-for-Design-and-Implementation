package lab11.lab.core.domain;

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
public class Rental extends BaseEntity<Long> {
    private String rentalSerialNumber;
    private long movieID;
    private long clientID;
    private boolean returned;
}
