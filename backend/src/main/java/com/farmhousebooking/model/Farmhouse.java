package com.farmhousebooking.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "farmhouses")
@EntityListeners(AuditingEntityListener.class)
public class Farmhouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @NotNull
    private BigDecimal pricePerNight;

    private String location;
    private String address;
    private Double latitude;
    private Double longitude;

    @ElementCollection
    @CollectionTable(name = "farmhouse_amenities", joinColumns = @JoinColumn(name = "farmhouse_id"))
    @Column(name = "amenity")
    private Set<String> amenities = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "farmhouse_images", joinColumns = @JoinColumn(name = "farmhouse_id"))
    @Column(name = "image_url")
    private List<String> imageUrls = new ArrayList<>();

    private Integer maxGuests;
    private Integer bedrooms;
    private Integer bathrooms;

    @OneToMany(mappedBy = "farmhouse", cascade = CascadeType.ALL)
    private Set<Booking> bookings = new HashSet<>();

    @OneToMany(mappedBy = "farmhouse", cascade = CascadeType.ALL)
    private Set<Review> reviews = new HashSet<>();

    private Double averageRating;
    private Integer totalReviews;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    private boolean active = true;
}