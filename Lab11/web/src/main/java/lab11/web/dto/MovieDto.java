package lab11.web.dto;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class MovieDto extends BaseDto{
    private String serialNumber;
    private String name;
    private int year;
    private int rating;
}