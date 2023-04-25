package academy.devdojo.springbootessentials.repository;

import academy.devdojo.springbootessentials.domain.Anime;

import java.util.List;

public interface AnimeRepository {
    List<Anime> listAll();
}
