package academy.devdojo.springbootessentials.controller;

import academy.devdojo.springbootessentials.domain.Anime;
import academy.devdojo.springbootessentials.requests.AnimePostRequestBody;
import academy.devdojo.springbootessentials.requests.AnimePutRequestBody;
import academy.devdojo.springbootessentials.service.AnimeService;
import academy.devdojo.springbootessentials.util.DateUtil;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<Anime>> list() {
        //log.info(dateUtil.formatLocalTimeToDatabaseStyle(LocalDateTime.now()));
        return new ResponseEntity<>(animeService.listAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Anime> findById(@PathVariable long id) {
        //log.info(dateUtil.formatLocalTimeToDatabaseStyle(LocalDateTime.now()));
        return ResponseEntity.ok(animeService.findByIdOrThrowBadRequestException(id));
    }

    @GetMapping(path = "/find") //find?name=name
    public ResponseEntity<List<Anime>> findByName(@RequestParam(name = "name") String name) {
        return ResponseEntity.ok(animeService.findByName(name));
    }

    @PostMapping
    public ResponseEntity<Anime> save(@RequestBody AnimePostRequestBody animePostRequestBody) {
        return new ResponseEntity<>(animeService.save(animePostRequestBody), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        animeService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody AnimePutRequestBody animePutRequestBody) {
        animeService.replace(animePutRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
