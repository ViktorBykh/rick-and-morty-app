package com.example.rickandmortyapp.service;

import com.example.rickandmortyapp.model.MovieCharacter;
import java.util.List;
import org.springframework.data.domain.PageRequest;

public interface MovieCharacterService {
    void syncExternalCharacters();

    MovieCharacter findRandomCharacter();

    List<MovieCharacter> findAllByNameContains(String namePart, PageRequest pageRequest);
}
