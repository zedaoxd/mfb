package com.myfitbody.domain.food.ingredient;

import com.myfitbody.domain.food.Food;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;


@Entity
@Table(name = "ingredients")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper = true)
public class Ingredient extends Food {


    private Boolean isChecked;

    @PrePersist
    public void prePersist() {
       this.setCreatedAt(Instant.now());
       this.setUpdatedAt(Instant.now());
    }

    @PreUpdate
    public void preUpdate() {
        this.setUpdatedAt(Instant.now());
    }
}
