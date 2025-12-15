package api.kata.cervezas.web;

import api.kata.cervezas.dto.BeerDto;
import api.kata.cervezas.exception.ValidationException;
import api.kata.cervezas.service.BeerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Tag(name = "Beer Management", description = "APIs for managing beers")
@RestController
@RequestMapping("/api/v1")
public class BeerController {

    @Autowired
    private BeerService beerService;

    @Operation(summary = "Get all beers with pagination", description = "Returns a paginated list of all beers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "400", description = "Invalid pagination parameters")
    })
    @GetMapping("/beers")
    public ResponseEntity<Page<BeerDto>> getAllBeers(
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<BeerDto> beers = beerService.findAll(pageable);
        return ResponseEntity.ok(beers);
    }

    @Operation(summary = "Count total beers", description = "Returns the total count of beers in X-Total-Count header")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved count")
    })
    @RequestMapping(value = "/beers", method = RequestMethod.HEAD)
    public ResponseEntity<Void> countBeers() {
        long count = beerService.count();
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(count))
                .build();
    }

    @Operation(summary = "Get beer by ID", description = "Returns a single beer by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved beer",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BeerDto.class))),
            @ApiResponse(responseCode = "404", description = "Beer not found")
    })
    @GetMapping("/beer/{id}")
    public ResponseEntity<BeerDto> getBeerById(@PathVariable Integer id) {
        BeerDto beer = beerService.findById(id);
        return ResponseEntity.ok(beer);
    }

    @Operation(summary = "Search and filter beers", description = "Search beers by name, style, ABV range, and IBU range")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved filtered list",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class)))
    })
    @GetMapping("/beers/search")
    public ResponseEntity<Page<BeerDto>> searchBeers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer styleId,
            @RequestParam(required = false) Float minAbv,
            @RequestParam(required = false) Float maxAbv,
            @RequestParam(required = false) Float minIbu,
            @RequestParam(required = false) Float maxIbu,
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<BeerDto> beers = beerService.search(name, styleId, minAbv, maxAbv, minIbu, maxIbu, pageable);
        return ResponseEntity.ok(beers);
    }

    @Operation(summary = "Create new beer", description = "Creates a new beer with brewery, category, and style relationships")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Beer created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BeerDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input or missing required relationships"),
            @ApiResponse(responseCode = "404", description = "Brewery, Category, or Style not found")
    })
    @PostMapping("/beer")
    public ResponseEntity<BeerDto> createBeer(@Valid @RequestBody BeerDto beerDto) {
        BeerDto createdBeer = beerService.create(beerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBeer);
    }

    @Operation(summary = "Update beer (full)", description = "Updates all fields of an existing beer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Beer updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BeerDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Beer not found")
    })
    @PutMapping("/beer/{id}")
    public ResponseEntity<BeerDto> updateBeer(@PathVariable Integer id, @Valid @RequestBody BeerDto beerDto) {
        BeerDto updatedBeer = beerService.update(id, beerDto);
        return ResponseEntity.ok(updatedBeer);
    }

    @Operation(summary = "Update beer (partial)", description = "Updates only specified fields of an existing beer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Beer updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BeerDto.class))),
            @ApiResponse(responseCode = "404", description = "Beer not found")
    })
    @PatchMapping("/beer/{id}")
    public ResponseEntity<BeerDto> partialUpdateBeer(@PathVariable Integer id, @RequestBody BeerDto beerDto) {
        BeerDto partialUpdatedBeer = beerService.partialUpdate(id, beerDto);
        return ResponseEntity.ok(partialUpdatedBeer);
    }

    @Operation(summary = "Upload beer image", description = "Uploads an image for a specific beer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Image uploaded successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BeerDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid file or file type"),
            @ApiResponse(responseCode = "404", description = "Beer not found")
    })
    @PostMapping("/beer/{id}/image")
    public ResponseEntity<BeerDto> uploadBeerImage(
            @PathVariable Integer id,
            @RequestParam("file") MultipartFile file) throws IOException {

        // Validate file
        if (file.isEmpty()) {
            throw new ValidationException("File is empty");
        }

        // Validate file type
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new ValidationException("File must be an image");
        }

        BeerDto updatedBeer = beerService.uploadImage(id, file);
        return ResponseEntity.ok(updatedBeer);
    }

    @Operation(summary = "Delete beer", description = "Deletes a beer by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Beer deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Beer not found")
    })
    @DeleteMapping("/beer/{id}")
    public ResponseEntity<Void> deleteBeer(@PathVariable Integer id) {
        beerService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/echo/{echo}")
    public String echo(@PathVariable("echo") String echo) {
        return echo;
    }

}
