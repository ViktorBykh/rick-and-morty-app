package com.example.rickandmortyapp.dto.mapper;

import com.example.rickandmortyapp.dto.MovieCharacterResponseDto;
import com.example.rickandmortyapp.dto.external.ApiCharacterDto;
import com.example.rickandmortyapp.model.Gender;
import com.example.rickandmortyapp.model.MovieCharacter;
import com.example.rickandmortyapp.model.Status;
import org.springframework.stereotype.Component;

@Component
public class MovieCharacterMapper {
    public MovieCharacter mapToDtoFromApiCharacterDto(ApiCharacterDto apiCharacterDto) {
        MovieCharacter movieCharacter = new MovieCharacter();
        movieCharacter.setName(apiCharacterDto.getName());
        movieCharacter.setGender(Gender.valueOf(apiCharacterDto.getGender().toUpperCase()));
        movieCharacter.setStatus(Status.valueOf(apiCharacterDto.getStatus().toUpperCase()));
        movieCharacter.setExternalId(apiCharacterDto.getId());
        return movieCharacter;
    }

    public MovieCharacterResponseDto mapToDtoFromMovieCharacter(MovieCharacter movieCharacter) {
        MovieCharacterResponseDto movieCharacterResponseDto = new MovieCharacterResponseDto();
        movieCharacterResponseDto.setId(movieCharacter.getId());
        movieCharacterResponseDto.setExternalId(movieCharacter.getExternalId());
        movieCharacterResponseDto.setName(movieCharacter.getName());
        movieCharacterResponseDto.setStatus(movieCharacter.getStatus());
        movieCharacterResponseDto.setGender(movieCharacter.getGender());
        return movieCharacterResponseDto;
    }
}
