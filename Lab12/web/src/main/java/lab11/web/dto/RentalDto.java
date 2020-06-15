package lab11.web.dto;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class RentalDto extends BaseDto{
    private String rentalSerialNumber;
    private long movieID;
    private long clientID;
    private boolean returned;
}