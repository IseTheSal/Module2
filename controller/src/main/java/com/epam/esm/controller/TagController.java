package com.epam.esm.controller;

import com.epam.esm.model.dto.TagDTO;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.epam.esm.controller.hateaos.Hateoas.createTagHateoas;

/**
 * Rest Controller which connected with service layer and provide data in JSON.
 * Used to interact with {@link TagDTO}.
 * <p>URI: <code>/api/v1/tags/</code></p>
 *
 * @author Illia Aheyeu
 */
@RestController
@RequestMapping(value = "/api/v1/tags", produces = MediaType.APPLICATION_JSON_VALUE)
public class TagController {
    /**
     * {@link TagDTO} service layer
     */
    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    /**
     * Method used to find {@link TagDTO} by its id.
     *
     * @param id {@link TagDTO} <code>id</code>
     * @return ResponseEntity with {@link TagDTO}
     */
    @GetMapping(value = "/{id:^[1-9]\\d{0,18}$}")
    public ResponseEntity<TagDTO> findTagById(@PathVariable long id) {
        TagDTO tag = tagService.findById(id);
        return new ResponseEntity<>(createTagHateoas(tag), HttpStatus.OK);
    }

    /**
     * Method used to find all {@link TagDTO}.
     *
     * @return ResponseEntity with <code>List</code> of {@link TagDTO}
     */
    @GetMapping
    public ResponseEntity<List<TagDTO>> findAllTags(@RequestParam(required = false, defaultValue = "1") int page,
                                                    @RequestParam(required = false, defaultValue = "10") int amount) {
        return new ResponseEntity<>(tagService.findAll(amount, page), HttpStatus.OK);
    }

    /**
     * Method used to delete {@link TagDTO} by its id.
     *
     * @param id {@link TagDTO} <code>id</code>
     * @return ResponseEntity with {@link TagDTO} id
     */
    @DeleteMapping(value = "/{id:^[1-9]\\d{0,18}$}")
    public ResponseEntity<Long> deleteTagById(@PathVariable long id) {
        return new ResponseEntity<>(tagService.delete(id), HttpStatus.OK);
    }

    /**
     * Method used to create {@link TagDTO}.
     *
     * @param tag {@link TagDTO}
     * @return ResponseEntity with {@link TagDTO} object
     */
    @PostMapping
    public ResponseEntity<TagDTO> createTag(@RequestBody TagDTO tag) {
        TagDTO createdTag = tagService.create(tag);
        return new ResponseEntity<>(createTagHateoas(createdTag), HttpStatus.OK);
    }

    /**
     * Method used to find most popular {@link TagDTO}.
     *
     * @return The most widely used {@link TagDTO} of a user with the highest cost of all orders.
     */
    @GetMapping("/popular")
    public ResponseEntity<TagDTO> findMostWidelyUsedTagByMaxUserPrice() {
        TagDTO tag = tagService.findMostWidelyUsedTag();
        return new ResponseEntity<>(createTagHateoas(tag), HttpStatus.OK);
    }
}
