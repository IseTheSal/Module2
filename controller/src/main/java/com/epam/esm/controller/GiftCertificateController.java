package com.epam.esm.controller;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Rest Controller which connected with service layer and provide data in JSON.
 * Used to interact with {@link GiftCertificate}.
 * URI <p>/certificate/</p>
 *
 * @author Illia Aheyeu
 */
@RestController
@RequestMapping(value = "/certificate/", produces = MediaType.APPLICATION_JSON_VALUE)
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
    @PostMapping("/create")
    public ResponseEntity<GiftCertificate> create(@RequestBody GiftCertificate giftCertificate) {
        return new ResponseEntity<>(certificateService.create(giftCertificate), HttpStatus.OK);
    }

    /**
     * Method used to delete {@link GiftCertificate} by its id.
     *
     * @param id {@link GiftCertificate} <code>id</code>
     * @return ResponseEntity with {@link GiftCertificate} id
     */
    @DeleteMapping("/delete")
    public ResponseEntity<Long> delete(@RequestParam String id) {
        return new ResponseEntity<>(certificateService.delete(id), HttpStatus.OK);
    }

    /**
     * Method used to find {@link GiftCertificate} by its id.
     *
     * @param id {@link GiftCertificate} <code>id</code>
     * @return ResponseEntity with {@link GiftCertificate}
     */
    @GetMapping("/find")
    public ResponseEntity<GiftCertificate> findById(@RequestParam String id) {
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
    @PutMapping("/update")
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
     * @param dateSort  <code>DESC</code> or <code>ASC</code> sort {@link GiftCertificate} by date of creation
     * @param nameSort  <code>DESC</code> or <code>ASC</code> sort {@link GiftCertificate} by name
     * @return <code>List</code> of {@link GiftCertificate}
     */
    @GetMapping("/findByAttribute")
    public ResponseEntity<List<GiftCertificate>> findCertificates(@RequestParam(required = false) String tagName,
                                                                  @RequestParam(required = false) String giftValue,
                                                                  @RequestParam(required = false) String dateSort,
                                                                  @RequestParam(required = false) String nameSort) {
        List<GiftCertificate> list = certificateService.findByParameters(tagName, giftValue, dateSort, nameSort);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

}
