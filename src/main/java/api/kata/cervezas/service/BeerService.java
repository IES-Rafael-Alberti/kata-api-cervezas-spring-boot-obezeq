package api.kata.cervezas.service;

import api.kata.cervezas.dto.BeerDto;
import api.kata.cervezas.mapper.BeerMapper;
import api.kata.cervezas.repository.BeerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BeerService {

    @Autowired
    private BeerRepository beerRepository;

    @Autowired
    private BeerMapper beerMapper;

    public List<BeerDto> findAll() {
        return beerRepository.findAll()
                .stream()
                .map(beerMapper::toDto)
                .collect(Collectors.toList());
    }

}
