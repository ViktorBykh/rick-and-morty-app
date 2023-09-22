package rick.and.morty.app.dto.internal;

import lombok.Data;
import rick.and.morty.app.model.Gender;
import rick.and.morty.app.model.Status;

@Data
public class MovieCharacterResponseDto {
    private Long id;
    private Long externalId;
    private String name;
    private Status status;
    private Gender gender;
}
