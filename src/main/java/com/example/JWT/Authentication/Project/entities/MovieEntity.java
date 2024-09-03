package com.example.JWT.Authentication.Project.entities;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieEntity {

    @Id
    private ObjectId id;

    @NotBlank(message = "Field 'name' is missing")
    private String name;

    private String description;

}
