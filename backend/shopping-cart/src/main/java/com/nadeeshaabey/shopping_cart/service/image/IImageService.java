package com.nadeeshaabey.shopping_cart.service.image;

import com.nadeeshaabey.shopping_cart.dto.ImageDto;
import com.nadeeshaabey.shopping_cart.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
    Image getImageById(Long id);
    void deleteImageById(Long id);
    List<ImageDto> saveImages(List<MultipartFile> files, Long ProductId);
    void updateImage(MultipartFile file, Long imageId);
}
