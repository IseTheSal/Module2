package com.epam.esm.controller;

import com.epam.esm.controller.hateaos.Hateoas;
import com.epam.esm.model.entity.Tag;
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
 * Used to interact with {@link Tag}.
 * <p>URI: <code>/api/v1/tags/</code></p>
 *
 * @author Illia Aheyeu
 */
@RestController
@RequestMapping(value = "/api/v1/tags", produces = MediaType.APPLICATION_JSON_VALUE)
public class TagController {
    /**
     * {@link Tag} service layer
     */
    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    /**
     * Method used to find {@link Tag} by its id.
     *
     * @param id {@link Tag} <code>id</code>
     * @return ResponseEntity with {@link Tag}
     */
    @GetMapping(value = "/{id:^[1-9]\\d{0,18}$}")
    public ResponseEntity<Tag> findTagById(@PathVariable long id) {
        Tag tag = tagService.findById(id);
        return new ResponseEntity<>(createTagHateoas(tag), HttpStatus.OK);
    }

    /**
     * Method used to find all {@link Tag}.
     *
     * @return ResponseEntity with <code>List</code> of {@link Tag}
     */
    @GetMapping("/all")
    public ResponseEntity<List<Tag>> findAllTags(@RequestParam(required = false, defaultValue = "1") int page,
                                                 @RequestParam(required = false, defaultValue = "10") int amount) {
        List<Tag> tagList = tagService.findAll(amount, page);
        tagList.forEach(Hateoas::createTagHateoas);
        return new ResponseEntity<>(tagList, HttpStatus.OK);
    }

    /**
     * Method used to delete {@link Tag} by its id.
     *
     * @param id {@link Tag} <code>id</code>
     * @return ResponseEntity with {@link Tag} id
     */
    @DeleteMapping(value = "/{id:^[1-9]\\d{0,18}$}")
    public ResponseEntity<Long> deleteTagById(@PathVariable long id) {
        return new ResponseEntity<>(tagService.delete(id), HttpStatus.OK);
    }

    /**
     * Method used to create {@link Tag}.
     *
     * @param tag {@link Tag}
     * @return ResponseEntity with {@link Tag} object
     */
    @PostMapping
    public ResponseEntity<Tag> createTag(@RequestBody Tag tag) {
        Tag createdTag = tagService.create(tag);
        return new ResponseEntity<>(createTagHateoas(createdTag), HttpStatus.OK);
    }

    /**
     * @return The most widely used {@link Tag} of a user with the highest cost of all orders.
     */
    @GetMapping("/popular")
    public ResponseEntity<Tag> findMostWidelyUsedTagByMaxUserPrice() {
        Tag tag = tagService.findMostWidelyUsedTag();
        return new ResponseEntity<>(createTagHateoas(tag), HttpStatus.OK);
    }
}
