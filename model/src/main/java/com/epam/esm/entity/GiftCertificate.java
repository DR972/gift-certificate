package com.epam.esm.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
 * The class {@code GiftCertificate} represents GiftCertificate entity.
 *
 * @author Dzmitry Rozmysl
 * @version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GiftCertificate extends Entity<Long> {

    /**
     * The marker  interface {@code OnCreate} helps in providing validation of {@link com.epam.esm.entity.GiftCertificate} class
     * fields when creating a new GiftCertificate object
     */
    public interface OnCreate {
    }

    /**
     * The marker  interface {@code OnCreate} helps in providing validation of {@link com.epam.esm.entity.GiftCertificate} class
     * fields when updating GiftCertificate object
     */
    public interface OnUpdate {
    }

    @NotNull(groups = {OnCreate.class}, message = "The name field must not be null")
    @Size(groups = {OnCreate.class, OnUpdate.class}, min = 2, max = 30, message = "The Gift Certificate Name must contain from 2 to 20 characters")
    private String name;

    @NotNull(groups = {OnCreate.class}, message = "The description field must not be null")
    @Size(groups = {OnCreate.class, OnUpdate.class}, min = 2, max = 200, message = "The Gift Certificate Description must contain from 2 to 200 characters")
    private String description;

    @NotNull(groups = {OnCreate.class}, message = "The price field must not be null")
    @Positive(groups = {OnCreate.class, OnUpdate.class}, message = "The Gift Certificate Price must be greater than 0")
    private BigDecimal price;

    @NotNull(groups = {OnCreate.class}, message = "The duration field must not be null")
    @Positive(groups = {OnCreate.class, OnUpdate.class}, message = "The Gift Certificate Duration must be greater than 0")
    private Integer duration;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime createDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime lastUpdateDate;

    @NotEmpty(groups = {OnCreate.class}, message = "The tags field must not be null")
    @Valid
    private List<Tag> tags;

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

    public GiftCertificate(String name, String description, BigDecimal price, Integer duration, List<Tag> tags) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.tags = tags;
    }
}
