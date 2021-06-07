package com.epam.controller;

import com.epam.controller.exception.TagNotFoundException;
import com.epam.model.entity.Tag;
import com.epam.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class TagController {

    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }


    @GetMapping(value = "/findTagById")
    public Tag findTagById(@RequestParam long id) {
        // TODO: 6/7/2021 localize message
        return tagService.findById(id).orElseThrow(() -> new TagNotFoundException("test error message", 40405));
    }
}
