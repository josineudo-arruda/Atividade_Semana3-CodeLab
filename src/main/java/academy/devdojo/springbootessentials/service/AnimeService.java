package academy.devdojo.springbootessentials.service;

import academy.devdojo.springbootessentials.domain.Anime;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimeService {
    //private final AnimeRepository animeRepository;
    public List<Anime> listAll(){
        return List.of(new Anime(1l, "BNH"), new Anime(2l, "Sailor Moon"));
    }
}
