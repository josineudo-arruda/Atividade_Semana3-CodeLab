package academy.devdojo.springboot2.controller;

import academy.devdojo.springboot2.domain.Anime;
import academy.devdojo.springboot2.util.DateUtil;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;



@RestController
@RequestMapping("anime")
@RequiredArgsConstructor
public class AnimeController {
    private DateUtil dateUtil;

    //public AnimeController(DateUtil dateUtil) {this.dateUtil = dateUtil;}

    @GetMapping("list")
    public List<Anime> list() {
        //log.info(dateUtil.formatLocalTimeToDatabaseStyle(LocalDateTime.now()));
        return List.of(new Anime("DB2"), new Anime("Sailor Moon"));
    }
}
