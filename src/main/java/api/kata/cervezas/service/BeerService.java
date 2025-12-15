package api.kata.cervezas.service;

import api.kata.cervezas.dto.BeerDto;
import api.kata.cervezas.exception.ResourceNotFoundException;
import api.kata.cervezas.mapper.BeerMapper;
import api.kata.cervezas.model.Beer;
import api.kata.cervezas.model.Brewery;
import api.kata.cervezas.model.Category;
import api.kata.cervezas.model.Style;
import api.kata.cervezas.repository.BeerRepository;
import api.kata.cervezas.repository.BreweryRepository;
import api.kata.cervezas.repository.CategoryRepository;
import api.kata.cervezas.repository.StyleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BeerService {

    @Autowired
    private BeerRepository beerRepository;

    @Autowired
    private BeerMapper beerMapper;

    @Autowired
    private BreweryRepository breweryRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private StyleRepository styleRepository;

    @Autowired
    private FileStorageService fileStorageService;

    public List<BeerDto> findAll() {
        return beerRepository.findAll()
                .stream()
                .map(beerMapper::toDto)
                .collect(Collectors.toList());
    }

    public Page<BeerDto> findAll(Pageable pageable) {
        return beerRepository.findAll(pageable)
                .map(beerMapper::toDto);
    }

    public long count() {
        return beerRepository.count();
    }

    public BeerDto findById(Integer id) {
        return beerRepository.findById(id)
                .map(beerMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Beer", "id", id));
    }

    public BeerDto create(BeerDto beerDto) {
        // Validate and fetch relationships
        Brewery brewery = breweryRepository.findById(beerDto.getBreweryId())
                .orElseThrow(() -> new ResourceNotFoundException("Brewery", "id", beerDto.getBreweryId()));

        Category category = categoryRepository.findById(beerDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", beerDto.getCategoryId()));

        Style style = styleRepository.findById(beerDto.getStyleId())
                .orElseThrow(() -> new ResourceNotFoundException("Style", "id", beerDto.getStyleId()));

        // Map DTO to entity
        Beer beer = beerMapper.toEntity(beerDto);

        // Set relationships
        beer.setBrewery(brewery);
        beer.setCat(category);
        beer.setStyle(style);

        // Set metadata
        beer.setLastMod(LocalDateTime.now());
        beer.setAddUser(0);

        Beer savedBeer = beerRepository.save(beer);
        return beerMapper.toDto(savedBeer);
    }

    public BeerDto update(Integer id, BeerDto beerDto) {
        Beer existingBeer = beerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Beer", "id", id));

        // Update basic fields
        existingBeer.setName(beerDto.getName());
        existingBeer.setAbv(beerDto.getAbv());
        existingBeer.setIbu(beerDto.getIbu());
        existingBeer.setSrm(beerDto.getSrm());
        existingBeer.setUpc(beerDto.getUpc());
        existingBeer.setFilepath(beerDto.getFilepath());
        existingBeer.setDescript(beerDto.getDescript());

        // Update relationships if provided
        if (beerDto.getBreweryId() != null) {
            Brewery brewery = breweryRepository.findById(beerDto.getBreweryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Brewery", "id", beerDto.getBreweryId()));
            existingBeer.setBrewery(brewery);
        }

        if (beerDto.getCategoryId() != null) {
            Category category = categoryRepository.findById(beerDto.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category", "id", beerDto.getCategoryId()));
            existingBeer.setCat(category);
        }

        if (beerDto.getStyleId() != null) {
            Style style = styleRepository.findById(beerDto.getStyleId())
                    .orElseThrow(() -> new ResourceNotFoundException("Style", "id", beerDto.getStyleId()));
            existingBeer.setStyle(style);
        }

        existingBeer.setLastMod(LocalDateTime.now());
        Beer updatedBeer = beerRepository.save(existingBeer);
        return beerMapper.toDto(updatedBeer);
    }

    public BeerDto partialUpdate(Integer id, BeerDto beerDto) {
        Beer existingBeer = beerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Beer", "id", id));

        // Update fields only if provided
        if (beerDto.getName() != null) existingBeer.setName(beerDto.getName());
        if (beerDto.getAbv() != null) existingBeer.setAbv(beerDto.getAbv());
        if (beerDto.getIbu() != null) existingBeer.setIbu(beerDto.getIbu());
        if (beerDto.getSrm() != null) existingBeer.setSrm(beerDto.getSrm());
        if (beerDto.getUpc() != null) existingBeer.setUpc(beerDto.getUpc());
        if (beerDto.getFilepath() != null) existingBeer.setFilepath(beerDto.getFilepath());
        if (beerDto.getDescript() != null) existingBeer.setDescript(beerDto.getDescript());

        // Update relationships if provided
        if (beerDto.getBreweryId() != null) {
            Brewery brewery = breweryRepository.findById(beerDto.getBreweryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Brewery", "id", beerDto.getBreweryId()));
            existingBeer.setBrewery(brewery);
        }

        if (beerDto.getCategoryId() != null) {
            Category category = categoryRepository.findById(beerDto.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category", "id", beerDto.getCategoryId()));
            existingBeer.setCat(category);
        }

        if (beerDto.getStyleId() != null) {
            Style style = styleRepository.findById(beerDto.getStyleId())
                    .orElseThrow(() -> new ResourceNotFoundException("Style", "id", beerDto.getStyleId()));
            existingBeer.setStyle(style);
        }

        existingBeer.setLastMod(LocalDateTime.now());
        Beer updatedBeer = beerRepository.save(existingBeer);
        return beerMapper.toDto(updatedBeer);
    }

    public void delete(Integer id) {
        Beer beer = beerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Beer", "id", id));
        beerRepository.delete(beer);
    }

    public Page<BeerDto> search(String name, Integer styleId, Float minAbv, Float maxAbv,
                                Float minIbu, Float maxIbu, Pageable pageable) {
        Specification<Beer> spec = api.kata.cervezas.specification.BeerSpecification.withFilters(
                name, styleId, minAbv, maxAbv, minIbu, maxIbu
        );
        return beerRepository.findAll(spec, pageable)
                .map(beerMapper::toDto);
    }

    public BeerDto uploadImage(Integer id, MultipartFile file) throws IOException {
        Beer beer = beerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Beer", "id", id));

        // Delete old image if exists
        if (beer.getFilepath() != null && !beer.getFilepath().isEmpty()) {
            try {
                fileStorageService.deleteFile(beer.getFilepath());
            } catch (IOException e) {
                // Log but don't fail
            }
        }

        // Store new image
        String filepath = fileStorageService.storeFile(file);
        beer.setFilepath(filepath);
        beer.setLastMod(LocalDateTime.now());

        Beer updatedBeer = beerRepository.save(beer);
        return beerMapper.toDto(updatedBeer);
    }

}
