package academy.devdojo.springbootessentials.repository;

import academy.devdojo.springbootessentials.domain.Anime;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Log4j2
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

    @Test
    @DisplayName("Save update anime when Successful")
    void save_UpdatesAnime_WhenSuccessful() {
        Anime animeToBeSaved = createAnime();
        Anime animeSaved =  this.animeRepository.save(animeToBeSaved);

        animeSaved.setName("Overlord");
        Anime animeUpdated = this.animeRepository.save(animeSaved);
        Assertions.assertThat(animeUpdated).isNotNull();
        Assertions.assertThat(animeUpdated.getId()).isNotNull();
        Assertions.assertThat(animeUpdated.getName()).isEqualTo(animeSaved.getName());
    }

    @Test
    @DisplayName("Delete removes anime when Successful")
    void delete_RemovesAnime_WhenSuccessful() {
        Anime animeToBeSaved = createAnime();
        Anime animeSaved =  this.animeRepository.save(animeToBeSaved);

        this.animeRepository.delete(animeSaved);

        Optional<Anime> animeOptional = this.animeRepository.findById(animeSaved.getId());

        Assertions.assertThat(animeOptional).isEmpty();
    }





    private Anime createAnime() {
        return Anime.builder()
                .name("Steven Universe")
                .build();
    }
}