package rick.and.morty.app.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import rick.and.morty.app.dto.external.ApiCharacterDto;
import rick.and.morty.app.dto.mapper.MovieCharacterMapper;
import rick.and.morty.app.model.MovieCharacter;
import rick.and.morty.app.repository.MovieCharacterRepository;
import rick.and.morty.app.service.HttpClient;
import rick.and.morty.app.dto.external.ApiInfoDto;
import rick.and.morty.app.dto.external.ApiResponseDto;

@SpringBootTest
class MovieCharacterServiceImplTest {
    private ApiResponseDto apiResponseDto;

    @Mock
    private HttpClient httpClient;

    @Mock
    private MovieCharacterRepository movieCharacterRepository;

    @Mock
    private MovieCharacterMapper movieCharacterMapper;

    @InjectMocks
    private MovieCharacterServiceImpl movieCharacterService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        movieCharacterService = new MovieCharacterServiceImpl(httpClient,
                movieCharacterRepository, movieCharacterMapper);
        apiResponseDto =
                createApiResponseDto("https://rickandmortyapi.com/api/character?page=1");
    }

    @Test
    public void testExternalCharacters() {
        ApiResponseDto apiResponseDto1 = createApiResponseDto(null);
        when(httpClient.get(eq("https://rickandmortyapi.com/api/character"),
                eq(ApiResponseDto.class))).thenReturn(apiResponseDto);
        when(httpClient.get(eq("https://rickandmortyapi.com/api/character?page=1"),
                eq(ApiResponseDto.class))).thenReturn(apiResponseDto1);

        movieCharacterService.syncExternalCharacters();
        verify(httpClient, times(2)).get(anyString(), eq(ApiResponseDto.class));
        verify(movieCharacterRepository, times(2)).saveAll(anyList());
    }

    @Test
    public void saveDtosToDb() {
        Set<Long> externalIds = Set.of(1L, 2L);
        List<MovieCharacter> existingMovieCharacters =
                Arrays.asList(new MovieCharacter(), new MovieCharacter());
        when(movieCharacterRepository.findAllByExternalIdIn(externalIds))
                .thenReturn(existingMovieCharacters);
        when(movieCharacterMapper.mapToDtoFromApiCharacterDto(any(ApiCharacterDto.class)))
                .thenReturn(new MovieCharacter());

        movieCharacterService.saveDtosToDb(apiResponseDto);

        verify(movieCharacterRepository).saveAll(anyList());
    }

    private ApiResponseDto createApiResponseDto(String next) {
        ApiResponseDto apiResponseDto = new ApiResponseDto();
        ApiInfoDto apiInfoDto = new ApiInfoDto();
        apiInfoDto.setNext(next);
        apiResponseDto.setInfo(apiInfoDto);
        apiResponseDto.setResults(new ApiCharacterDto[]{new ApiCharacterDto()});
        return apiResponseDto;
    }
}
