package com.example.webservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class Post {
    private Long id;
    private Long userId;
    private String title;
    private String body;
}
