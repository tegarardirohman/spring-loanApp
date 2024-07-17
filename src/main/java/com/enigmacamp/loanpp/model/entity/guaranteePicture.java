package com.enigmacamp.loanpp.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "t_guarantee_picture")
public class guaranteePicture {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String contentType;
    private String name;
    private String path;
    private Long size;
}
