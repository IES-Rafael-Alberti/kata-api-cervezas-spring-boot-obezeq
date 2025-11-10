package api.kata.cervezas.web;

import api.kata.cervezas.model.Beer;
import api.kata.cervezas.service.BeerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class BeerController {

    @Autowired
    private BeerService beerService;

    @GetMapping("/beers")
    public ResponseEntity<List<Beer>> beers(){
        List<Beer> beers = beerService.findAll();
        return ResponseEntity.ok(beers);
    }

    @GetMapping("/beer")
    public String beer() {
        return "beer";
    }

    @GetMapping("/beer/{id}")
    public String getBeerById(@PathVariable int id) {
        return "beer with id: " + id;
    }

    @GetMapping("/echo/{echo}")
    public String echo(@PathVariable("echo") String echo) { return echo; }

}
