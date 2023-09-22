package rick.and.morty.app.service.impl;

import jakarta.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import rick.and.morty.app.dto.external.ApiCharacterDto;
import rick.and.morty.app.dto.external.ApiResponseDto;
import rick.and.morty.app.dto.mapper.MovieCharacterMapper;
import rick.and.morty.app.model.MovieCharacter;
import rick.and.morty.app.repository.MovieCharacterRepository;
import rick.and.morty.app.service.HttpClient;
import rick.and.morty.app.service.MovieCharacterService;

@Log4j2
@Service
@RequiredArgsConstructor
public class MovieCharacterServiceImpl implements MovieCharacterService {
    private final HttpClient httpClient;
    private final MovieCharacterRepository movieCharacterRepository;
    private final MovieCharacterMapper movieCharacterMapper;

    @PostConstruct
    @Scheduled(cron = "0 8 * * *")
    @Override
    public void syncExternalCharacters() {
        log.info(".syncExternalCharacters() method was invoked at " + LocalDateTime.now());
        ApiResponseDto apiResponseDto = httpClient.get("https://rickandmortyapi.com/api/character",
                ApiResponseDto.class);

        saveDtosToDb(apiResponseDto);

        while (apiResponseDto.getInfo().getNext() != null) {
            apiResponseDto = httpClient.get(apiResponseDto.getInfo().getNext(),
                    ApiResponseDto.class);
            saveDtosToDb(apiResponseDto);
        }
    }

    @Override
    public MovieCharacter findRandomCharacter() {
        long count = movieCharacterRepository.count();
        long randomId = (long) (Math.random() * count);
        return movieCharacterRepository.getReferenceById(randomId);
    }

    @Override
    public List<MovieCharacter> findAllByNameContains(String namePart, PageRequest pageRequest) {
        return movieCharacterRepository.findAllByNameContains(namePart, pageRequest);
    }

    void saveDtosToDb(ApiResponseDto apiResponseDto) {
        Map<Long, ApiCharacterDto> externalDtos = Arrays.stream(apiResponseDto.getResults())
                .collect(Collectors.toMap(ApiCharacterDto::getId, Function.identity()));

        Set<Long> externalIds = externalDtos.keySet();

        List<MovieCharacter> existingMovieCharacters =
                movieCharacterRepository.findAllByExternalIdIn(externalIds);

        for (MovieCharacter existingCharacter : existingMovieCharacters) {
            Long externalId = existingCharacter.getExternalId();
            if (externalDtos.containsKey(externalId)) {
                ApiCharacterDto apiCharacterDto = externalDtos.get(externalId);
                MovieCharacter updatedCharacter =
                        movieCharacterMapper.mapToDtoFromApiCharacterDto(apiCharacterDto);
                existingCharacter.setName(updatedCharacter.getName());
                existingCharacter.setGender(updatedCharacter.getGender());
                existingCharacter.setStatus(updatedCharacter.getStatus());
                movieCharacterRepository.save(existingCharacter);
                externalIds.remove(externalId);
            }
        }

        List<MovieCharacter> charactersToSave = externalIds.stream()
                .map(i -> movieCharacterMapper.mapToDtoFromApiCharacterDto(externalDtos.get(i)))
                .collect(Collectors.toList());

        movieCharacterRepository.saveAll(charactersToSave);
    }
}
