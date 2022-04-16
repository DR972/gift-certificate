package com.epam.esm.controller;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RestController
@RequestMapping("/certificates")
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
    public GiftCertificate getCertificateById(@PathVariable long id) {
        return certificateService.findCertificate(id);
    }

    @GetMapping("/allCertificates")
    @ResponseStatus(HttpStatus.OK)
    public List<GiftCertificate> getAllCertificates(@RequestParam MultiValueMap<String, String> allRequestParams,
                                                    @RequestParam(name = ROWS, defaultValue = "5") int rows,
                                                    @RequestParam(name = PAGE_NUMBER, defaultValue = "0") int pageNumber) {
        return certificateService.findListCertificates(allRequestParams, rows, pageNumber, rows);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public GiftCertificate createCertificate(@Validated(GiftCertificate.OnCreate.class) @RequestBody GiftCertificate certificate) {
        return certificateService.createCertificate(certificate);
    }

    @PatchMapping("/updateCertificate/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GiftCertificate updateCertificate(@Validated(GiftCertificate.OnUpdate.class) @RequestBody GiftCertificate certificate,
                                             @PathVariable long id) {
        return certificateService.updateCertificate(certificate, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCertificate(@PathVariable long id) {
        certificateService.deleteCertificate(id);
    }
}