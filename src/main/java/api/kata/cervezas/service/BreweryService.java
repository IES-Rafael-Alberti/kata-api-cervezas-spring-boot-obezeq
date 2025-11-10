package api.kata.cervezas.service;

import api.kata.cervezas.dto.BreweryDto;
import api.kata.cervezas.mapper.BreweryMapper;
import api.kata.cervezas.model.Brewery;
import api.kata.cervezas.repository.BreweryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BreweryService {

    @Autowired
    private BreweryRepository breweryRepository;

    @Autowired
    private BreweryMapper breweryMapper;

    public List<BreweryDto> findAll() {
        return breweryRepository.findAll()
                .stream()
                .map(breweryMapper::toDto)
                .collect(Collectors.toList());
    }

    public BreweryDto findById(Integer id) {
        return breweryRepository.findById(id)
                .map(breweryMapper::toDto)
                .orElse(null);
    }

}
