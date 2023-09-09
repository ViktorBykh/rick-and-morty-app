package com.example.rickandmortyapp.controller;

import com.example.rickandmortyapp.dto.MovieCharacterResponseDto;
import com.example.rickandmortyapp.dto.mapper.MovieCharacterMapper;
import com.example.rickandmortyapp.service.MovieCharacterService;
import com.example.rickandmortyapp.service.SortingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/movie-characters")
public class MovieCharacterController {
    private final MovieCharacterService movieCharacterService;
    private final MovieCharacterMapper movieCharacterMapper;
    private final SortingService sortingService;

    @GetMapping("/random")
    @Operation(summary = "Get a random character",
            description = "Get a random character in the \"Rick and Morty\" universe")
    public MovieCharacterResponseDto findRandomMovieCharacter() {
        return movieCharacterMapper
                .mapToDtoFromMovieCharacter(movieCharacterService.findRandomCharacter());
    }

    @GetMapping("/by-name")
    @Operation(summary = "Get all the characters",
            description = "Get all the characters in the \"Rick and Morty\" universe by name")
    public List<MovieCharacterResponseDto> findAllMovieCharactersByName(
            @RequestParam("name") String namePart,
            @RequestParam(defaultValue = "10")
            @Parameter(description = "default value is `10`") Integer count,
            @RequestParam(defaultValue = "4")
            @Parameter(description = "default value is `4`") Integer page,
            @RequestParam(defaultValue = "id")
            @Parameter(description = "default value is `id`") String sortBy) {
        PageRequest pageRequest = sortingService.getSortedList(count, page, sortBy);
        return movieCharacterService.findAllByNameContains(namePart, pageRequest)
               .stream()
               .map(movieCharacterMapper::mapToDtoFromMovieCharacter)
               .collect(Collectors.toList());
    }
}
