package com.epam.esm.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * The class {@code GiftCertificate} represents GiftCertificate entity.
 *
 * @author Dzmitry Rozmysl
 * @version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class GiftCertificate extends Entity<Long> {
    /**
     * GiftCertificate name.
     */
    private String name;
    /**
     * GiftCertificate description.
     */
    private String description;
    /**
     * GiftCertificate price.
     */
    private BigDecimal price;
    /**
     * GiftCertificate duration.
     */
    private Integer duration;
    /**
     * GiftCertificate createDate.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime createDate;
    /**
     * GiftCertificate lastUpdateDate.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime lastUpdateDate;
    /**
     * List<Tag> tags
     */
    private List<Tag> tags;

    /**
     * The constructor creates a GiftCertificate object
     *
     * @param id             long id
     * @param name           String name
     * @param description    String description
     * @param price          BigDecimal price
     * @param duration       int duration
     * @param createDate     LocalDateTime createDate
     * @param lastUpdateDate LocalDateTime lastUpdateDate
     * @param tags           List<Tag> tags
     */
    public GiftCertificate(long id, String name, String description, BigDecimal price, int duration, LocalDateTime createDate,
                           LocalDateTime lastUpdateDate, List<Tag> tags) {
        super(id);
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
        this.tags = tags;
    }

    /**
     * The constructor creates a GiftCertificate object
     *
     * @param name        String name
     * @param description String description
     * @param price       BigDecimal price
     * @param duration    int duration
     * @param tags        List<Tag> tags
     */
    public GiftCertificate(String name, String description, BigDecimal price, Integer duration, List<Tag> tags) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.tags = tags;
    }
}
