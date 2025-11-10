package api.kata.cervezas.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BreweryDto {

    private Integer id;

    @NotBlank(message = "Brewery name is required")
    private String name;

    private String address1;

    private String address2;

    private String city;

    private String state;

    private String code;

    private String country;

    private String phone;

    private String website;

    private String filepath;

    private String descript;

}
