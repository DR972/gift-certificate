package com.epam.esm.controller;

import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/certificate")
@Validated
public class CertificateController {
    private static final String ROWS = "rows";
    private static final String PAGE_NUMBER = "pageNumber";
    private final GiftCertificateService certificateService;

    @Autowired
    public CertificateController(GiftCertificateService certificateService) {
        this.certificateService = certificateService;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GiftCertificate> getCertificateById(@PathVariable long id) {
        GiftCertificate certificate = certificateService.findCertificate(id);
        if (certificate == null) {
            throw new NoSuchEntityException("There is no Gift Certificate with ID = " + id);
        }
        return ResponseEntity.status(OK).body(certificate);
    }

    @GetMapping("/allCertificates")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<GiftCertificate>> getAllCertificates(@RequestParam MultiValueMap<String, String> allRequestParams,
                                                                    @RequestParam(name = ROWS, defaultValue = "5") int rows,
                                                                    @RequestParam(name = PAGE_NUMBER, defaultValue = "0") int pageNumber) {
        return ResponseEntity.status(OK).body(certificateService.findListCertificates(allRequestParams, rows, pageNumber, rows));
    }

    @PostMapping("/createCertificate")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<GiftCertificate> createCertificate(@Validated(GiftCertificate.OnCreate.class) @RequestBody GiftCertificate certificate) {
        return ResponseEntity.status(OK).body(certificateService.createCertificate(certificate));
    }

    @PatchMapping("/updateCertificate/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GiftCertificate> updateCertificate(@Validated(GiftCertificate.OnUpdate.class) @RequestBody GiftCertificate certificate,
                                                             @PathVariable long id) {
        if (certificateService.findCertificate(id) == null) {
            throw new NoSuchEntityException("There is no Gift Certificate with this ID = " + id);
        }
        return ResponseEntity.status(OK).body(certificateService.updateCertificate(certificate, id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Object> deleteCertificate(@PathVariable long id) {
        if (certificateService.findCertificate(id) == null) {
            throw new NoSuchEntityException("There is no Gift Certificate with this ID = " + id);
        }
        certificateService.deleteCertificate(id);
        return new ResponseEntity<>(NO_CONTENT);
    }
}