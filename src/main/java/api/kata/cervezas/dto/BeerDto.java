package api.kata.cervezas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class BeerDto {

    private Integer id;

    @NotBlank(message = "Name is required")
    private String name;

    @PositiveOrZero(message = "ABV must be non-negative")
    private Float abv;

    @PositiveOrZero(message = "IBU must be non-negative")
    private Float ibu;

    @PositiveOrZero(message = "SRM must be non-negative")
    private Float srm;

    private Integer upc;

    private String filepath;

    private String descript;

    private Integer breweryId;

    private Integer categoryId;

    private Integer styleId;



}
