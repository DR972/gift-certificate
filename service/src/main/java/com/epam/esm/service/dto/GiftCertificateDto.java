package com.epam.esm.service.dto;

import com.epam.esm.entity.Tag;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * The Class {@code GiftCertificateDto} represents GiftCertificateDto.
 *
 * @author Dzmitry Rozmysl
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GiftCertificateDto {

    /**
     * The marker  interface {@code OnCreate} helps in providing validation of {@link com.epam.esm.service.dto.GiftCertificateDto} class
     * fields when creating a new GiftCertificateDto object
     */
    public interface OnCreate {
    }

    /**
     * The marker  interface {@code OnCreate} helps in providing validation of {@link com.epam.esm.service.dto.GiftCertificateDto} class
     * fields when updating GiftCertificateDto object
     */
    public interface OnUpdate {
    }

    private long id;

    @NotNull(groups = {GiftCertificateDto.OnCreate.class}, message = "ex.certificateNameNotNull")
    @Size(groups = {GiftCertificateDto.OnCreate.class, GiftCertificateDto.OnUpdate.class}, min = 2, max = 30, message = "ex.certificateNameSize")
    private String name;

    @NotNull(groups = {GiftCertificateDto.OnCreate.class}, message = "ex.descriptionNotNull")
    @Size(groups = {GiftCertificateDto.OnCreate.class, GiftCertificateDto.OnUpdate.class}, min = 2, max = 200, message = "ex.descriptionSize")
    private String description;

    @NotNull(groups = {GiftCertificateDto.OnCreate.class}, message = "ex.priceNotNull")
    @Positive(groups = {GiftCertificateDto.OnCreate.class, GiftCertificateDto.OnUpdate.class}, message = "ex.pricePositive")
    private BigDecimal price;

    @NotNull(groups = {GiftCertificateDto.OnCreate.class}, message = "ex.durationNotNull")
    @Positive(groups = {GiftCertificateDto.OnCreate.class, GiftCertificateDto.OnUpdate.class}, message = "ex.durationPositive")
    private Integer duration;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime createDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime lastUpdateDate;

    @NotEmpty(groups = {GiftCertificateDto.OnCreate.class}, message = "ex.tagsNotNull")
    @Valid
    private List<Tag> tags;

    public GiftCertificateDto(String name, String description, BigDecimal price, Integer duration, List<Tag> tags) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.tags = tags;
    }
}
