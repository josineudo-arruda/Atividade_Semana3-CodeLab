package academy.devdojo.springbootessentials.controller;

import academy.devdojo.springbootessentials.domain.Anime;
import academy.devdojo.springbootessentials.service.AnimeService;
import academy.devdojo.springbootessentials.util.DateUtil;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;



@RestController
@RequestMapping("animes")
@RequiredArgsConstructor
public class AnimeController {
    private final DateUtil dateUtil;
    private final AnimeService animeService;

    //public AnimeController(DateUtil dateUtil) {this.dateUtil = dateUtil;}

    @GetMapping
    public List<Anime> list() {
        //log.info(dateUtil.formatLocalTimeToDatabaseStyle(LocalDateTime.now()));
        return animeService.listAll();
    }

}
