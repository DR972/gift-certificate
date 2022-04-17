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

/**
 * Class {@code GiftCertificateController} is an endpoint of the API which allows you to perform CRUD operations on GiftCertificates.
 * Annotated by {@link RestController} without parameters to provide an answer in application/json.
 * Annotated by {@link RequestMapping} with parameter value = "/certificates".
 * Annotated by {@link Validated} without parameters  provides checking of constraints in method parameters.
 * So that {@code GiftCertificateController} is accessed by sending request to /certificates.
 *
 * @author Dzmitry Rozmysl
 * @since 1.0
 */
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

    /**
     * Method for getting GiftCertificate by ID.
     *
     * @param id GiftCertificate id
     * @return GiftCertificate
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GiftCertificate getCertificateById(@PathVariable long id) {
        return certificateService.findCertificate(id);
    }

    /**
     * Method for getting list of GiftCertificates from request parameters.
     *
     * @param allRequestParams request parameters, which include the information needed for the search
     * @param rows             number of lines per page (5 by default)
     * @param pageNumber       page number(default 0)
     * @return list of GiftCertificates
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<GiftCertificate> getAllCertificates(@RequestParam MultiValueMap<String, String> allRequestParams,
                                                    @RequestParam(name = ROWS, defaultValue = "5") int rows,
                                                    @RequestParam(name = PAGE_NUMBER, defaultValue = "0") int pageNumber) {
        return certificateService.findListCertificates(allRequestParams, rows, pageNumber, rows);
    }

    /**
     * Method for saving new GiftCertificate.
     * Annotated by {@link Validated} with parameters GiftCertificate.OnCreate.class provides validation of the fields
     * of the GiftCertificate object when creating.
     *
     * @param certificate GiftCertificate
     * @return created GiftCertificate
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GiftCertificate createCertificate(@Validated(GiftCertificate.OnCreate.class) @RequestBody GiftCertificate certificate) {
        return certificateService.createCertificate(certificate);
    }

    /**
     * Method for updating GiftCertificate.
     * Annotated by {@link Validated} with parameters GiftCertificate.OnUpdate.class provides validation of the fields
     * of the GiftCertificate object when updating.
     *
     * @param certificate new GiftCertificate parameters
     * @param id          GiftCertificate id
     * @return updated GiftCertificate
     */
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GiftCertificate updateCertificate(@Validated(GiftCertificate.OnUpdate.class) @RequestBody GiftCertificate certificate,
                                             @PathVariable long id) {
        return certificateService.updateCertificate(certificate, id);
    }

    /**
     * Method for removing GiftCertificate by ID.
     *
     * @param id GiftCertificate id
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCertificate(@PathVariable long id) {
        certificateService.deleteCertificate(id);
    }
}