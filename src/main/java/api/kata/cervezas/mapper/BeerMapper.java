package api.kata.cervezas.mapper;

import api.kata.cervezas.dto.BeerDto;
import api.kata.cervezas.model.Beer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BeerMapper {

    @Mapping(target = "breweryId", source = "brewery.id")
    @Mapping(target = "categoryId", source = "cat.id")
    @Mapping(target = "styleId", source = "style.id")
    BeerDto toDto(Beer beer);

    @Mapping(target = "brewery", ignore = true)
    @Mapping(target = "cat", ignore = true)
    @Mapping(target = "style", ignore = true)
    @Mapping(target = "addUser", ignore = true)
    @Mapping(target = "lastMod", ignore = true)
    Beer toEntity(BeerDto beerDto);

}
