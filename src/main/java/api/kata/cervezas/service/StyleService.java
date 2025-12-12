package api.kata.cervezas.service;

import api.kata.cervezas.dto.StyleDto;
import api.kata.cervezas.mapper.StyleMapper;
import api.kata.cervezas.model.Style;
import api.kata.cervezas.repository.StyleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StyleService {

    @Autowired
    private StyleRepository styleRepository;

    @Autowired
    private StyleMapper styleMapper;

    public List<StyleDto> findAll() {
        return styleRepository.findAll()
                .stream()
                .map(styleMapper::toDto)
                .collect(Collectors.toList());
    }

    public StyleDto findById(Integer id) {
        return styleRepository.findById(id)
                .map(styleMapper::toDto)
                .orElse(null);
    }

}
