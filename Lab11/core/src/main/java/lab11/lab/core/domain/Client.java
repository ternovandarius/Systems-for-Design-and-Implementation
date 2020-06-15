package lab11.lab.core.domain;

import lombok.*;
import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class Client extends BaseEntity<Long> {
    private String serialNumber;
    private String name;
    private int age;
}