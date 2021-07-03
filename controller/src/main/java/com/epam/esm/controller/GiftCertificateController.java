package com.epam.esm.controller;

import com.epam.esm.controller.hateaos.Hateoas;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.epam.esm.controller.hateaos.Hateoas.createCertificateHateoas;

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
        GiftCertificate certificate = certificateService.create(giftCertificate);
        return new ResponseEntity<>(createCertificateHateoas(certificate), HttpStatus.CREATED);
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
        GiftCertificate certificate = certificateService.findById(id);
        return new ResponseEntity<>(createCertificateHateoas(certificate), HttpStatus.OK);
    }

    /**
     * Method used to update existing {@link GiftCertificate}.
     *
     * @param giftCertificate {@link GiftCertificate}
     * @return updated {@link GiftCertificate}
     */
    @PutMapping
    public ResponseEntity<GiftCertificate> update(@RequestBody GiftCertificate giftCertificate) {
        GiftCertificate certificate = certificateService.update(giftCertificate);
        return new ResponseEntity<>(createCertificateHateoas(certificate), HttpStatus.OK);
    }


    /**
     * Remove giftCertificate from sale
     *
     * @param id {@link GiftCertificate} <code>id</code>
     * @return ResponseEntity with {@link GiftCertificate} id
     */
    @PutMapping("/{id:^[1-9]\\d{0,18}$}")
    public ResponseEntity<Long> removeFromSales(@PathVariable long id) {
        return new ResponseEntity<>(certificateService.removeFromSale(id), HttpStatus.OK);
    }

    /**
     * Method used to find all {@link GiftCertificate} by specified parameters.
     *
     * <code><p>All parameters are optional</p></code>
     *
     * @param tagName   <code>Set</code> of {@link com.epam.esm.model.entity.Tag Tag}
     * @param giftValue Part of <code>name</code> or <code>description</code> of {@link GiftCertificate}
     * @param dateOrder <code>DESC</code> or <code>ASC</code> sort {@link GiftCertificate} by date of creation
     * @param nameOrder <code>DESC</code> or <code>ASC</code> sort {@link GiftCertificate} by name
     * @return <code>List</code> of {@link GiftCertificate}
     */
    @GetMapping
    public ResponseEntity<List<GiftCertificate>> findCertificates(@RequestParam(required = false) List<String> tagName,
                                                                  @RequestParam(required = false) String giftValue,
                                                                  @RequestParam(required = false) String dateOrder,
                                                                  @RequestParam(required = false) String nameOrder,
                                                                  @RequestParam(required = false, defaultValue = "1") int page,
                                                                  @RequestParam(required = false, defaultValue = "10") int amount) {
        List<GiftCertificate> list = certificateService.findByParameters(tagName, giftValue, dateOrder, nameOrder,
                amount, page);
        list.forEach(Hateoas::createCertificateHateoas);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
