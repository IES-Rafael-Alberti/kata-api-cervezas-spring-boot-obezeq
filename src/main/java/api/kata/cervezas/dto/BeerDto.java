package api.kata.cervezas.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
@Schema(description = "Beer Data Transfer Object")
public class BeerDto {

    @Schema(description = "Beer ID (auto-generated)", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;

    @Schema(description = "Beer name", example = "IPA Suprema", required = true)
    @NotBlank(message = "Name is required")
    private String name;

    @Schema(description = "Alcohol by volume percentage", example = "6.5", minimum = "0")
    @PositiveOrZero(message = "ABV must be non-negative")
    private Float abv;

    @Schema(description = "International Bitterness Units", example = "65.0", minimum = "0")
    @PositiveOrZero(message = "IBU must be non-negative")
    private Float ibu;

    @Schema(description = "Standard Reference Method (color)", example = "12.0", minimum = "0")
    @PositiveOrZero(message = "SRM must be non-negative")
    private Float srm;

    @Schema(description = "Universal Product Code", example = "12345")
    private Integer upc;

    @Schema(description = "Image file path", example = "/images/beer-image.jpg")
    private String filepath;

    @Schema(description = "Beer description", example = "A bold IPA with citrus notes")
    private String descript;

    @Schema(description = "Brewery ID (foreign key)", example = "1", required = true)
    @NotNull(message = "Brewery ID is required")
    @Positive(message = "Brewery ID must be positive")
    private Integer breweryId;

    @Schema(description = "Category ID (foreign key)", example = "1", required = true)
    @NotNull(message = "Category ID is required")
    @Positive(message = "Category ID must be positive")
    private Integer categoryId;

    @Schema(description = "Style ID (foreign key)", example = "10", required = true)
    @NotNull(message = "Style ID is required")
    @Positive(message = "Style ID must be positive")
    private Integer styleId;

}
