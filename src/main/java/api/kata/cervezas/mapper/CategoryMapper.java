package api.kata.cervezas.mapper;

import api.kata.cervezas.dto.CategoryDto;
import api.kata.cervezas.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "lastMod", ignore = true)
    Category toEntity(CategoryDto categoryDto);

    CategoryDto toDto(Category category);

}
