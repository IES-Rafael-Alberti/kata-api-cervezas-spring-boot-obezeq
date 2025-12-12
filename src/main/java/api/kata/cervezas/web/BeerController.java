package api.kata.cervezas.web;

import api.kata.cervezas.dto.BeerDto;
import api.kata.cervezas.service.BeerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class BeerController {

    @Autowired
    private BeerService beerService;

    @GetMapping("/beers")
    public ResponseEntity<List<BeerDto>> getAllBeers(){
        List<BeerDto> beers = beerService.findAll();
        return ResponseEntity.ok(beers);
    }

    @GetMapping("/beer/{id}")
    public ResponseEntity<BeerDto> getBeerById(@PathVariable Integer id) {
        BeerDto beer = beerService.findById(id);
        if (beer == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(beer);
    }

    @PostMapping("/beer")
    public ResponseEntity<BeerDto> createBeer(@RequestBody BeerDto beerDto) {
        BeerDto createdBeer = beerService.create(beerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBeer);
    }

    @PutMapping("/beer/{id}")
    public ResponseEntity<BeerDto> updateBeer(@PathVariable Integer id, @RequestBody BeerDto beerDto) {
        BeerDto updatedBeer = beerService.update(id, beerDto);
        if (updatedBeer == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedBeer);
    }

    @PatchMapping("/beer/{id}")
    public ResponseEntity<BeerDto> partialUpdateBeer(@PathVariable Integer id, @RequestBody BeerDto beerDto) {
        BeerDto partialUpdatedBeer = beerService.partialUpdate(id, beerDto);
        if (partialUpdatedBeer == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(partialUpdatedBeer);
    }

    @DeleteMapping("/beer/{id}")
    public ResponseEntity<Void> deleteBeer(@PathVariable Integer id) {
        boolean deleted = beerService.delete(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/echo/{echo}")
    public String echo(@PathVariable("echo") String echo) { return echo; }

}
