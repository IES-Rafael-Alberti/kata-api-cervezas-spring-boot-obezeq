package api.kata.cervezas.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryDto {

    private Integer id;

    @NotBlank(message = "Category name is required")
    private String name;

}
