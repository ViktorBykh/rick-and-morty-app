package rick.and.morty.app.controller;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import java.util.Collections;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import rick.and.morty.app.model.MovieCharacter;
import rick.and.morty.app.service.MovieCharacterService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class MovieCharacterControllerTest {
    private MovieCharacter movieCharacter;
    @MockBean
    private MovieCharacterService movieCharacterService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
        movieCharacter = new MovieCharacter();
    }

    @Test
    public void shouldReturnRandomMovieCharacter() {
        movieCharacter.setName("Rick");
        Mockito.when(movieCharacterService.findRandomCharacter())
                .thenReturn(movieCharacter);

        RestAssuredMockMvc.given()
                .when()
                .get("/movie-characters/random")
                .then()
                .statusCode(200)
                .body("name", Matchers.equalTo("Rick"));
    }

    @Test
    public void shouldReturnMovieCharacterByName() {
        movieCharacter.setName("Morty");
        Mockito.when(movieCharacterService.findAllByNameContains(
                        ArgumentMatchers.anyString(), ArgumentMatchers.any()))
                .thenReturn(Collections.singletonList(movieCharacter));

        RestAssuredMockMvc.given()
                .param("name", "Morty")
                .when()
                .get("movie-characters/by-name")
                .then()
                .statusCode(200)
                .body("[0].name", Matchers.equalTo("Morty"));
    }
}
