package com.myfitbody.domain.food;

import com.myfitbody.domain.food.enums.FoodTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(of = "id")
public abstract class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private double calories;
    private double protein;
    private double fat;
    private double carbohydrates;
    private String imgUrl;
    @Enumerated(EnumType.STRING)
    private FoodTime foodTime;
    private Instant createdAt;
    private Instant updatedAt;
}
