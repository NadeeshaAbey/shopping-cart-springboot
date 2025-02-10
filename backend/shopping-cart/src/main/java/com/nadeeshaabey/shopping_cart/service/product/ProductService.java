package com.nadeeshaabey.shopping_cart.service.product;

import com.nadeeshaabey.shopping_cart.dto.ImageDto;
import com.nadeeshaabey.shopping_cart.dto.ProductDTO;
import com.nadeeshaabey.shopping_cart.exceptions.ProductNotFoundException;
import com.nadeeshaabey.shopping_cart.model.Category;
import com.nadeeshaabey.shopping_cart.model.Image;
import com.nadeeshaabey.shopping_cart.model.Product;
import com.nadeeshaabey.shopping_cart.repository.CategoryRepository;
import com.nadeeshaabey.shopping_cart.repository.ImageRpository;
import com.nadeeshaabey.shopping_cart.repository.ProductRepository;
import com.nadeeshaabey.shopping_cart.request.AddProductRequest;
import com.nadeeshaabey.shopping_cart.request.ProductUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService{

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final ImageRpository imageRpository;

    @Override
    public Product addProduct(AddProductRequest request) {
//         check if category is found in the DB
//        if Yes, set it as a new product category
//        if No, then save it as a new category
//        then set as the new product category

        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
                .orElseGet(()->{
                    Category newCategory = new Category(request.getCategory().getName());
                    return categoryRepository.save(newCategory);
                });
        request.setCategory(category);
        return productRepository.save(createProduct(request,category));
    }

    private Product createProduct(AddProductRequest request, Category category) {
        return new Product(
                request.getName(),
                request.getBrand(),
                request.getPrice(),
                request.getInventory(),
                request.getDescription(),
                category

        );
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository
                .findById(id).orElseThrow(()->new ProductNotFoundException("Product not found!"));
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.findById(id)
                .ifPresentOrElse(productRepository::delete,
                        ()->{throw new ProductNotFoundException("Product not found!");});
    }

    @Override
    public Product updateProduct(ProductUpdateRequest request, Long productId) {
        return productRepository.findById(productId)
                .map(existingProduct->updateExistingProduct(existingProduct,request))
                .map(productRepository :: save)
                .orElseThrow(()->new ProductNotFoundException("Product not found!"));
    }

    private Product updateExistingProduct(Product existingProduct, ProductUpdateRequest request) {
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setInventory(request.getInventory());
        existingProduct.setDescription(request.getDescription());

        Category category = categoryRepository.findByName(request.getCategory().getName());
        existingProduct.setCategory(category);
        return existingProduct;

    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category,brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand,name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand,name);
    }

    @Override
    public List<ProductDTO> getConvertedProducts(List<Product> products){
        return products.stream()
                .map(this::convertToDto)
                .toList();
    }
    @Override
    public ProductDTO convertToDto(Product product){
        ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
        List<Image> images = imageRpository.findByProductId(product.getId());
        List<ImageDto> imageDtos = images.stream()
                .map(image -> modelMapper.map(image, ImageDto.class))
                .toList();
        productDTO.setImages(imageDtos);
        return productDTO;
    }
}
