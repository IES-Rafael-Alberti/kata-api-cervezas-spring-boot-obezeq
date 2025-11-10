package api.kata.cervezas.service;

import api.kata.cervezas.model.Beer;
import api.kata.cervezas.repository.BeerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BeerService {

    @Autowired
    private BeerRepository beerRepository;

    public List<Beer> findAll() {
        return beerRepository.findAll();
    };

}
