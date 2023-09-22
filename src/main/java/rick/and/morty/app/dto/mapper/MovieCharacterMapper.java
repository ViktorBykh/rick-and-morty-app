package rick.and.morty.app.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import rick.and.morty.app.config.MapperConfig;
import rick.and.morty.app.dto.external.ApiCharacterDto;
import rick.and.morty.app.dto.internal.MovieCharacterResponseDto;
import rick.and.morty.app.model.MovieCharacter;

@Mapper(config = MapperConfig.class)
public interface MovieCharacterMapper {

    @Mapping(target = "externalId", source = "id")
    MovieCharacter mapToDtoFromApiCharacterDto(ApiCharacterDto apiCharacterDto);

    MovieCharacterResponseDto mapToDtoFromMovieCharacter(MovieCharacter movieCharacter);
}
