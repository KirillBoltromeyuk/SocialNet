package com.kirichproduction.messenger04.service;

import com.kirichproduction.messenger04.model.Image;
import com.kirichproduction.messenger04.repository.ImageRepository;
import org.springframework.stereotype.Service;

@Service
public class ImageService {
    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public Image saveImage (String name){
        Image image = new Image(name);
        imageRepository.save(image);
        Image imageRename = imageRepository.findByImageName(name).orElseThrow();
        imageRename.setImageName(image.getId().toString()+name);
        imageRepository.save(imageRename);
        return imageRename;
    }
}
