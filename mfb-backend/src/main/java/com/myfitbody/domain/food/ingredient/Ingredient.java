package com.myfitbody.domain.food.ingredient;

import com.myfitbody.domain.food.Food;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "ingredients")
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(of = "id", callSuper = true)
public class Ingredient extends Food {

    private UUID id;
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
