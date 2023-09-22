package rick.and.morty.app.service;

import java.util.List;
import org.springframework.data.domain.PageRequest;
import rick.and.morty.app.model.MovieCharacter;

public interface MovieCharacterService {
    void syncExternalCharacters();

    MovieCharacter findRandomCharacter();

    List<MovieCharacter> findAllByNameContains(String namePart, PageRequest pageRequest);
}
