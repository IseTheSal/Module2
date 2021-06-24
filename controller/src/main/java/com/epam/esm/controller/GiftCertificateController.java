package com.epam.esm.controller;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * Rest Controller which connected with service layer and provide data in JSON.
 * Used to interact with {@link GiftCertificate}.
 * <p>URI: <code>/api/v1/certificates/</code></p>
 *
 * @author Illia Aheyeu
 */
@RestController
@RequestMapping(value = "/api/v1/certificates", produces = MediaType.APPLICATION_JSON_VALUE)
public class GiftCertificateController {

    /**
     * {@link GiftCertificate} service layer
     */
    private final GiftCertificateService certificateService;

    @Autowired
    public GiftCertificateController(GiftCertificateService certificateService) {
        this.certificateService = certificateService;
    }

    /**
     * Method used to create {@link GiftCertificate}.
     *
     * @param giftCertificate {@link GiftCertificate}
     * @return ResponseEntity with {@link GiftCertificate} object
     */
    @PostMapping
    public ResponseEntity<GiftCertificate> create(@RequestBody GiftCertificate giftCertificate) {
        return new ResponseEntity<>(certificateService.create(giftCertificate), HttpStatus.CREATED);
    }

    /**
     * Method used to delete {@link GiftCertificate} by its id.
     *
     * @param id {@link GiftCertificate} <code>id</code>
     * @return ResponseEntity with {@link GiftCertificate} id
     */
    @DeleteMapping("/{id:^[1-9]\\d{0,18}$}")
    public ResponseEntity<Long> delete(@PathVariable long id) {
        return new ResponseEntity<>(certificateService.delete(id), HttpStatus.OK);
    }

    /**
     * Method used to find {@link GiftCertificate} by its id.
     *
     * @param id {@link GiftCertificate} <code>id</code>
     * @return ResponseEntity with {@link GiftCertificate}
     */
    @GetMapping("/{id:^[1-9]\\d{0,18}$}")
    public ResponseEntity<GiftCertificate> findById(@PathVariable long id) {
        return new ResponseEntity<>(certificateService.findById(id), HttpStatus.OK);
    }

    /**
     * Method used to find all {@link GiftCertificate}.
     *
     * @return ResponseEntity with <code>List</code> of {@link GiftCertificate}
     */
    @GetMapping("/all")
    public ResponseEntity<List<GiftCertificate>> findAll() {
        return new ResponseEntity<>(certificateService.findAll(), HttpStatus.OK);
    }

    /**
     * Method used to update existing {@link GiftCertificate}.
     *
     * @param giftCertificate {@link GiftCertificate}
     * @return updated {@link GiftCertificate}
     */
    @PutMapping
    public ResponseEntity<GiftCertificate> update(@RequestBody GiftCertificate giftCertificate) {
        return new ResponseEntity<>(certificateService.update(giftCertificate), HttpStatus.OK);
    }

    /**
     * Method used to find all {@link GiftCertificate} by specified parameters.
     *
     * <code><p>All parameters are optional</p></code>
     *
     * @param tagName   <code>Name</code> of {@link com.epam.esm.model.entity.Tag Tag}
     * @param giftValue Part of <code>name</code> or <code>description</code> of {@link GiftCertificate}
     * @param dateOrder <code>DESC</code> or <code>ASC</code> sort {@link GiftCertificate} by date of creation
     * @param nameOrder <code>DESC</code> or <code>ASC</code> sort {@link GiftCertificate} by name
     * @return <code>List</code> of {@link GiftCertificate}
     */
    @GetMapping
    public ResponseEntity<List<GiftCertificate>> findCertificates(@RequestParam(required = false) String tagName,
                                                                  @RequestParam(required = false) String giftValue,
                                                                  @RequestParam(required = false) String dateOrder,
                                                                  @RequestParam(required = false) String nameOrder) {
        List<GiftCertificate> list = certificateService.findByParameters(tagName, giftValue, dateOrder, nameOrder);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * Search for gift certificates by several tags with 'and' condition.
     *
     * @param tagSet <code>Set</code> of {@link Tag}
     * @return <code>List</code> of {@link GiftCertificate}
     */
    @GetMapping("/tags")
    public ResponseEntity<List<GiftCertificate>> findBySeveralTags(@RequestBody Set<Tag> tagSet) {
        return new ResponseEntity<>(certificateService.findBySeveralTags(tagSet), HttpStatus.OK);
    }
}
