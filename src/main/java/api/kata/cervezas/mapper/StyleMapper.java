package api.kata.cervezas.mapper;

import api.kata.cervezas.dto.StyleDto;
import api.kata.cervezas.model.Style;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StyleMapper {

    @Mapping(target = "cat", ignore = true)
    @Mapping(target = "lastMod", ignore = true)
    Style toEntity(StyleDto styleDto);

    @Mapping(target = "categoryId", source = "cat.id")
    StyleDto toDto(Style style);

}