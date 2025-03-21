package com.farmhousebooking.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "reviews")
@EntityListeners(AuditingEntityListener.class)
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "farmhouse_id", nullable = false)
    private Farmhouse farmhouse;

    @NotNull
    @Min(1)
    @Max(5)
    private Integer rating;

    @Column(columnDefinition = "TEXT")
    private String comment;

    private boolean approved = false;

    @Enumerated(EnumType.STRING)
    private ReviewStatus status = ReviewStatus.PENDING;

    private String moderationComment;
    private LocalDateTime moderatedAt;
    private String moderatedBy;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public enum ReviewStatus {
        PENDING,
        APPROVED,
        REJECTED,
        REPORTED
    }

    // Additional fields for review metrics
    private Integer helpfulVotes = 0;
    private Integer reportCount = 0;

    // Fields for review categories
    private Integer cleanlinessRating;
    private Integer locationRating;
    private Integer valueRating;
    private Integer communicationRating;
    private Integer accuracyRating;

    @PrePersist
    @PreUpdate
    private void calculateAverageRating() {
        if (cleanlinessRating != null && locationRating != null && 
            valueRating != null && communicationRating != null && 
            accuracyRating != null) {
            
            this.rating = (cleanlinessRating + locationRating + valueRating + 
                         communicationRating + accuracyRating) / 5;
        }
    }
}