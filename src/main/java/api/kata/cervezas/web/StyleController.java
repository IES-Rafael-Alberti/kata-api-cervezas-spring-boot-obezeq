package api.kata.cervezas.web;

import api.kata.cervezas.dto.StyleDto;
import api.kata.cervezas.model.Style;
import api.kata.cervezas.service.StyleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class StyleController {

    @Autowired
    private StyleService styleService;

    @GetMapping("/styles")
    public ResponseEntity<List<StyleDto>> getAllStyles() {
        List<StyleDto> styles = styleService.getAllStyles();
        return ResponseEntity.ok(styles);
    }

    @GetMapping("/style/{id}")
    public ResponseEntity<StyleDto> getStyleById(@PathVariable Integer id) {
        StyleDto style = styleService.getStyleById(id);
        if (style == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(style);
    }
}
