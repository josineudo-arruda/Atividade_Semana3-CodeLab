package academy.devdojo.springbootessentials.client;

import academy.devdojo.springbootessentials.domain.Anime;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Log4j2
public class SpringClient {
    public static void main(String[] args) {
        Anime object = new RestTemplate().getForObject("http://localhost:8080/animes/{id}", Anime.class, 10);

        log.info(object);

        Anime[] animes = new RestTemplate().getForObject("http://localhost:8080/animes/all", Anime[].class);

        log.info(Arrays.toString(animes));

        ResponseEntity<List<Anime>> exchange = new RestTemplate().exchange("http://localhost:8080/animes/all", HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });

        log.info(exchange.getBody());
    }
}
