package com.kirichproduction.messenger04.repository;

import com.kirichproduction.messenger04.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findByImageName(String imageName);
}
