package academy.devdojo.springbootessentials.integration;

import academy.devdojo.springbootessentials.domain.AdminUser;
import academy.devdojo.springbootessentials.domain.Anime;
import academy.devdojo.springbootessentials.repository.AdminUserRepository;
import academy.devdojo.springbootessentials.repository.AnimeRepository;
import academy.devdojo.springbootessentials.requests.AnimePostRequestBody;
import academy.devdojo.springbootessentials.util.AnimeCreator;
import academy.devdojo.springbootessentials.util.AnimePostRequestBodyCreator;
import academy.devdojo.springbootessentials.wrapper.PageableResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Lazy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AnimeControllerIT {
    @Autowired
    @Qualifier(value = "testRestTemplateRoleUser")
    private TestRestTemplate testRestTemplateUser;

    @Autowired
    @Qualifier(value = "testRestTemplateRoleAdmin")
    private TestRestTemplate testRestTemplateAdmin;


    @Autowired
    private AnimeRepository animeRepository;
    @Autowired
    private AdminUserRepository adminUserRepository;
    private static final AdminUser USER = AdminUser.builder()
            .name("Admin")
            .password("admin")
            .username("admin")
            .authorities("ROLE_USER")
            .build();

    private static final AdminUser ADMIN = AdminUser.builder()
            .name("Latorre")
            .password("latorre123")
            .username("latorre")
            .authorities("ROLE_USER,ROLE_ADMIN")
            .build();
    @TestConfiguration
    @Lazy
    static class Config {
        @Bean(name = "testRestTemplateRoleUser")
        public TestRestTemplate testRestTemplateRoleUserCreator(@Value("${local.server.port}") int port) {
            RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
                    .rootUri("http://localhost:"+port)
                    .basicAuthentication("admin", "admin");
            return new TestRestTemplate(restTemplateBuilder);
        }
        @Bean(name = "testRestTemplateRoleAdmin")
        public TestRestTemplate testRestTemplateRoleAdminCreator(@Value("${local.server.port}") int port) {
            RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
                    .rootUri("http://localhost:"+port)
                    .basicAuthentication("latorre", "latorre123");
            return new TestRestTemplate(restTemplateBuilder);
        }
    }

    @Test
    @DisplayName("List returns list of animes inside page object when successful")
    void list_ReturnsListOfAnimesInsidePageObject_WhenSuccessful() {
        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
        adminUserRepository.save(USER);
        String expectedName = savedAnime.getName();
        PageableResponse<Anime> animePage = testRestTemplateUser.exchange("/animes", HttpMethod.GET, null
                , new ParameterizedTypeReference<PageableResponse<Anime>>() {
                }).getBody();

        Assertions.assertThat(animePage).isNotNull();
        Assertions.assertThat(animePage.toList())
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedName);
    }


    @Test
    @DisplayName("ListAll returns list of animes when successful")
    void listAll_ReturnsListOfAnimes_WhenSuccessful() {
        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
        adminUserRepository.save(USER);
        String expectedName = savedAnime.getName();
        List<Anime> animes = testRestTemplateUser.exchange("/animes/all", HttpMethod.GET, null
                , new ParameterizedTypeReference<List<Anime>>() {
                }).getBody();
        Assertions.assertThat(animes).isNotNull()
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findById returns anime when successful")
    void findById_ReturnsAnimes_WhenSuccessful() {
        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
        adminUserRepository.save(USER);
        Long expectedId = savedAnime.getId();
        Anime anime = testRestTemplateUser.getForObject("/animes/{id}", Anime.class, expectedId);
        Assertions.assertThat(anime)
                .isNotNull();
        Assertions.assertThat(anime.getId()).isNotNull().isEqualTo(expectedId);
    }


    @Test
    @DisplayName("findByName returns a list of anime when successful")
    void findByName_ReturnsListOfAnimes_WhenSuccessful() {
        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
        adminUserRepository.save(USER);
        String expectedName = savedAnime.getName();
        String url = String.format("/animes/find?name=%s", expectedName);
        List<Anime> animes = testRestTemplateUser.exchange(url, HttpMethod.GET, null
                , new ParameterizedTypeReference<List<Anime>>() {
                }).getBody();
        Assertions.assertThat(animes)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findByName returns an empty list of anime when anime is not found")
    void findByName_ReturnsEmptyListOfAnimes_WhenAnimeIsNotFound() {
        adminUserRepository.save(USER);
        List<Anime> animes = testRestTemplateUser.exchange("/animes/find?name=dbz", HttpMethod.GET,
                , new ParameterizedTypeReference<List<Anime>>() {
                }).getBody();
        Assertions.assertThat(animes)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("save returns anime when successful")
    void save_ReturnsAnimes_WhenSuccessful() {
        adminUserRepository.save(USER);
        AnimePostRequestBody animePostRequestBody = AnimePostRequestBodyCreator.createAnimePostRequestBody();
        ResponseEntity<Anime> animeResponseEntity = testRestTemplateUser.postForEntity("/animes",animePostRequestBody,Anime.class);
        Assertions.assertThat(animeResponseEntity)
                .isNotNull();
        Assertions.assertThat(animeResponseEntity.getStatusCode())
                .isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(animeResponseEntity.getBody())
                .isNotNull();
        Assertions.assertThat(animeResponseEntity.getBody().getId())
                .isNotNull();
    }

    @Test
    @DisplayName("replace updates anime when successful")
    void replace_UpdatesAnime_WhenSuccessful() {
        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
        adminUserRepository.save(USER);
        savedAnime.setName("new name");
        AnimePostRequestBody animePostRequestBody = AnimePostRequestBodyCreator.createAnimePostRequestBody();
        ResponseEntity<Void> animeResponseEntity = testRestTemplateUser.exchange("/animes",HttpMethod.PUT,
                new HttpEntity<>(savedAnime), Void.class);
        Assertions.assertThat(animeResponseEntity)
                .isNotNull();
        Assertions.assertThat(animeResponseEntity.getStatusCode())
                .isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("delete removes anime when successful")
    void delete_RemovesAnime_WhenSuccessful(){
        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
        adminUserRepository.save(USER);
        ResponseEntity<Void> animeResponseEntity = testRestTemplateAdmin.exchange("/animes/admin/{id}",
                HttpMethod.DELETE,null, Void.class, savedAnime.getId());
        Assertions.assertThat(animeResponseEntity).isNotNull();
        Assertions.assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("delete returns 403 when user is not admin")
    void delete_Returns403_WhenUserIsNotAdmin(){
        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
        adminUserRepository.save(USER);
        ResponseEntity<Void> animeResponseEntity = testRestTemplateUser.exchange("/animes/admin/{id}",
                HttpMethod.DELETE,null, Void.class, savedAnime.getId());
        Assertions.assertThat(animeResponseEntity).isNotNull();
        Assertions.assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }
}