package api.kata.cervezas.web;

import api.kata.cervezas.dto.BreweryDto;
import api.kata.cervezas.service.BreweryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class BreweryController {

    @Autowired
    private BreweryService breweryService;

    @GetMapping("/breweries")
    public ResponseEntity<List<BreweryDto>> getAllBreweries() {
        List<BreweryDto> breweries = breweryService.findAll();
        return ResponseEntity.ok(breweries);
    }

    @GetMapping("/brewerie/{id}")
    public ResponseEntity<BreweryDto> getBreweryById(@PathVariable Integer id) {
        BreweryDto brewery = breweryService.findById(id);
        if (brewery == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(brewery);
    }

}
