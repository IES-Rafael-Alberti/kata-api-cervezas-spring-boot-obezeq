package api.kata.cervezas.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class BeerController {

    @GetMapping("/beers")
    public String beers(){
        return "beers";
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
