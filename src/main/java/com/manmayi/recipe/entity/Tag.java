package com.manmayi.recipe.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Tag {

    @Id
    private String tag;
}
