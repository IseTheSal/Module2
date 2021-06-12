package com.epam.esm.controller;

import com.epam.esm.model.entity.Tag;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/tag/", produces = MediaType.APPLICATION_JSON_VALUE)
public class TagController {

    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping(value = "/find")
    public ResponseEntity<Tag> findTagById(@RequestParam int id) {
        return new ResponseEntity<>(tagService.findById(String.valueOf(id)), HttpStatus.OK);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<Tag>> findAllTags() {
        return new ResponseEntity<>(tagService.findAll(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<Long> deleteTagById(@RequestParam String id) {
        return new ResponseEntity<>(tagService.delete(id), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Tag> updateTagById(@RequestBody Tag tag) {
        return new ResponseEntity<>(tagService.create(tag), HttpStatus.OK);
    }
}
