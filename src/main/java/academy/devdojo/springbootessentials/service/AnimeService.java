package academy.devdojo.springbootessentials.service;

import academy.devdojo.springbootessentials.domain.Anime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class AnimeService {
    private static List<Anime> animes = List.of(new Anime(1l, "BNH"), new Anime(2l, "Sailor Moon"));
    static {
        animes = new ArrayList<>(List.of(new Anime(1l, "BNH"), new Anime(2l, "Sailor Moon")));
    }
    //private final AnimeRepository animeRepository;
    public List<Anime> listAll(){
        return animes;
    }

    public Anime findById(long id){
        return animes.stream()
                .filter(anime -> anime.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Anime not Found"));
    }

    public Anime save(Anime anime) {
        anime.setId(ThreadLocalRandom.current().nextLong(3, 10000000));
        animes.add(anime);
        return anime;
    }


    public void delete(long id) {
        animes.remove(findById(id));
    }
}
