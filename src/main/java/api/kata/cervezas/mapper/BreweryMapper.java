package api.kata.cervezas.mapper;

import api.kata.cervezas.dto.BreweryDto;
import api.kata.cervezas.model.Brewery;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BreweryMapper {

    Brewery toEntity(BreweryDto breweryDto);

    BreweryDto toDto(Brewery brewery);

}
