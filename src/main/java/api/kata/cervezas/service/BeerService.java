package api.kata.cervezas.service;

import api.kata.cervezas.dto.BeerDto;
import api.kata.cervezas.mapper.BeerMapper;
import api.kata.cervezas.model.Beer;
import api.kata.cervezas.repository.BeerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    public BeerDto findById(Integer id) {
        return beerRepository.findById(id)
                .map(beerMapper::toDto)
                .orElse(null);
    }

    public BeerDto create(BeerDto beerDto) {
        Beer beer = beerMapper.toEntity(beerDto);
        beer.setLastMod(LocalDateTime.now());
        beer.setAddUser(0);
        Beer savedBeer = beerRepository.save(beer);
        return beerMapper.toDto(savedBeer);
    }

    public BeerDto update(Integer id, BeerDto beerDto) {
        return beerRepository.findById(id)
                .map(existingBeer -> {
                    existingBeer.setName(beerDto.getName());
                    existingBeer.setAbv(beerDto.getAbv());
                    existingBeer.setIbu(beerDto.getIbu());
                    existingBeer.setSrm(beerDto.getSrm());
                    existingBeer.setUpc(beerDto.getUpc());
                    existingBeer.setFilepath(beerDto.getFilepath());
                    existingBeer.setDescript(beerDto.getDescript());
                    existingBeer.setLastMod(LocalDateTime.now());
                    Beer updatedBeer = beerRepository.save(existingBeer);
                    return beerMapper.toDto(updatedBeer);
                })
                .orElse(null);
    }

    public BeerDto partialUpdate(Integer id, BeerDto beerDto) {
        return beerRepository.findById(id)
                .map(existingBeer -> {
                    if (beerDto.getName() != null) existingBeer.setName(beerDto.getName());
                    if (beerDto.getAbv() != null) existingBeer.setAbv(beerDto.getAbv());
                    if (beerDto.getIbu() != null) existingBeer.setIbu(beerDto.getIbu());
                    if (beerDto.getSrm() != null) existingBeer.setSrm(beerDto.getSrm());
                    if (beerDto.getUpc() != null) existingBeer.setUpc(beerDto.getUpc());
                    if (beerDto.getFilepath() != null) existingBeer.setFilepath(beerDto.getFilepath());
                    if (beerDto.getDescript() != null) existingBeer.setDescript(beerDto.getDescript());
                    existingBeer.setLastMod(LocalDateTime.now());
                    Beer updatedBeer = beerRepository.save(existingBeer);
                    return beerMapper.toDto(updatedBeer);
                })
                .orElse(null);
    }

    public boolean delete(Integer id) {
        if (beerRepository.existsById(id)) {
            beerRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
