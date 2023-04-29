package academy.devdojo.springbootessentials.repository;

import academy.devdojo.springbootessentials.domain.Anime;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AnimeRepository extends JpaRepository<Anime, Long> {

}
