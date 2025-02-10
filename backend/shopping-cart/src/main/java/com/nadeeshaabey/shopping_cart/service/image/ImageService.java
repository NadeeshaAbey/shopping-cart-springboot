package com.nadeeshaabey.shopping_cart.service.image;

import com.nadeeshaabey.shopping_cart.dto.ImageDto;
import com.nadeeshaabey.shopping_cart.exceptions.ResourceNotFoundException;
import com.nadeeshaabey.shopping_cart.model.Image;
import com.nadeeshaabey.shopping_cart.model.Product;
import com.nadeeshaabey.shopping_cart.repository.ImageRpository;
import com.nadeeshaabey.shopping_cart.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService {

    private final ImageRpository imageRpository;
    private final IProductService productService;

    @Override
    public Image getImageById(Long id) {
        return imageRpository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("No image found with id: " + id));
    }

    @Override
    public void deleteImageById(Long id) {
        imageRpository.findById(id).ifPresentOrElse(imageRpository::delete, ()->{
            throw new ResourceNotFoundException("No image found with id: " + id);
        });
    }

    @Override
    public List<ImageDto> saveImages(List<MultipartFile> files, Long productId) {
        Product product = productService.getProductById(productId);
        List<ImageDto> savedImageDto = new ArrayList<>();

        for (MultipartFile file : files) {
            try {
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);

                // Save image first to generate an ID
                Image savedImage = imageRpository.save(image);

                // Now that savedImage has an ID, set download URL
                String buildDownloadUrl = "api/v1/images/image/download/";
                savedImage.setDownloadUrl(buildDownloadUrl + savedImage.getId());

                // Save again to persist the download URL
                savedImage = imageRpository.save(savedImage);

                // Convert to DTO
                ImageDto imageDto = new ImageDto();
                imageDto.setImageId(savedImage.getId());
                imageDto.setFileName(savedImage.getFileName());
                imageDto.setDownloadUrl(savedImage.getDownloadUrl()); // Ensure DTO gets the correct URL

                savedImageDto.add(imageDto);
            } catch (IOException | SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return savedImageDto;
    }


    @Override
    public void updateImage(MultipartFile file, Long ImageId) {
        Image image = getImageById(ImageId);
        try {
            image.setFileType(file.getContentType());
            image.setFileName(file.getOriginalFilename());
            image.setImage(new SerialBlob(file.getBytes()));
            imageRpository.save(image);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
