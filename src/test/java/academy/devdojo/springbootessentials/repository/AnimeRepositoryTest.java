package academy.devdojo.springbootessentials.repository;

import academy.devdojo.springbootessentials.domain.Anime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("Tests for Anime Repository")
class AnimeRepositoryTest {
    @Autowired
    private AnimeRepository animeRepository;
    @Test
    @DisplayName("Save persists anime when Successful")
    void save_PersistAnime_WhenSuccessful() {
        Anime animeToBeSaved = createAnime();
        Anime animeSaved =  this.animeRepository.save(animeToBeSaved);
        Assertions.assertThat(animeSaved).isNotNull(); // não está null
        Assertions.assertThat(animeSaved.getId()).isNotNull(); // id nulo
        Assertions.assertThat(animeSaved.getName()).isEqualTo(animeToBeSaved.getName()); // nome salvo igual o enviado
    }

    private Anime createAnime() {
        return Anime.builder()
                .name("Steven Universe")
                .build();
    }
}