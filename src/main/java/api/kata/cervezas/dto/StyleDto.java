package api.kata.cervezas.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class StyleDto {

    private Integer id;

    @NotBlank(message = "Style name is required")
    private String name;

    private Integer categoryId;

}
