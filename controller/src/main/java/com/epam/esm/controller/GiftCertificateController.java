package com.epam.esm.controller;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/certificate/", produces = MediaType.APPLICATION_JSON_VALUE)
public class GiftCertificateController {

    private final CertificateService certificateService;

    @Autowired
    public GiftCertificateController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    @PostMapping("/create")
    public ResponseEntity<GiftCertificate> create(@RequestBody GiftCertificate giftCertificate) {
        return new ResponseEntity<>(certificateService.create(giftCertificate), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Long> delete(@RequestParam String id) {
        return new ResponseEntity<>(certificateService.delete(id), HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity<GiftCertificate> findById(@RequestParam String id) {
        return new ResponseEntity<>(certificateService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<GiftCertificate>> findAll() {
        return new ResponseEntity<>(certificateService.findAll(), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<GiftCertificate> update(@RequestBody GiftCertificate giftCertificate) {
        return new ResponseEntity<>(certificateService.update(giftCertificate), HttpStatus.OK);
    }

    @GetMapping("/findByAttribute")
    public ResponseEntity<List<GiftCertificate>> findCertificates(@RequestParam(required = false) String tagName,
                                                                  @RequestParam(required = false) String giftValue,
                                                                  @RequestParam(required = false) String dataSort,
                                                                  @RequestParam(required = false) String nameSort) {
        List<GiftCertificate> list = certificateService.findByParameters(tagName, giftValue, dataSort, nameSort);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

}
