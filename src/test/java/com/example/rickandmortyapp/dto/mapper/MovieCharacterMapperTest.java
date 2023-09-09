package com.example.rickandmortyapp.dto.mapper;

import com.example.rickandmortyapp.dto.MovieCharacterResponseDto;
import com.example.rickandmortyapp.dto.external.ApiCharacterDto;
import com.example.rickandmortyapp.model.Gender;
import com.example.rickandmortyapp.model.MovieCharacter;
import com.example.rickandmortyapp.model.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MovieCharacterMapperTest {

    @Mock
    private ApiCharacterDto apiCharacterDto;

    @InjectMocks
    private MovieCharacterMapper movieCharacterMapper;

    @Test
    public void mapToDtoFromApiCharacterDto() {
        apiCharacterDto = new ApiCharacterDto();
        apiCharacterDto.setId(1L);
        apiCharacterDto.setName("Rick");
        apiCharacterDto.setGender("Male");
        apiCharacterDto.setStatus("Alive");

        MovieCharacter result = movieCharacterMapper.mapToDtoFromApiCharacterDto(apiCharacterDto);

        Assertions.assertEquals(apiCharacterDto.getId(), result.getExternalId());
        Assertions.assertEquals(apiCharacterDto.getName(), result.getName());
        Assertions.assertEquals(Gender.MALE, result.getGender());
        Assertions.assertEquals(Status.ALIVE, result.getStatus());
    }

    @Test
    public void mapToDtoFromMovieCharacter() {
        MovieCharacter movieCharacter = new MovieCharacter();
        movieCharacter.setId(2L);
        movieCharacter.setExternalId(100L);
        movieCharacter.setName("Morty");
        movieCharacter.setGender(Gender.MALE);
        movieCharacter.setStatus(Status.ALIVE);

        MovieCharacterResponseDto result =
                movieCharacterMapper.mapToDtoFromMovieCharacter(movieCharacter);

        Assertions.assertEquals(movieCharacter.getId(), result.getId());
        Assertions.assertEquals(movieCharacter.getExternalId(), result.getExternalId());
        Assertions.assertEquals(movieCharacter.getName(), result.getName());
        Assertions.assertEquals(movieCharacter.getGender(), result.getGender());
        Assertions.assertEquals(movieCharacter.getStatus(), result.getStatus());
    }
}
