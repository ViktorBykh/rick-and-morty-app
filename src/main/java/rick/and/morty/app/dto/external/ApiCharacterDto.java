package rick.and.morty.app.dto.external;

import lombok.Data;
import rick.and.morty.app.model.Gender;
import rick.and.morty.app.model.Status;

@Data
public class ApiCharacterDto {
    private Long id;
    private String name;
    private Status status;
    private Gender gender;
}
