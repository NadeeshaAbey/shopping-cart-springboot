package com.nadeeshaabey.shopping_cart.controller;

import com.nadeeshaabey.shopping_cart.dto.ProductDTO;
import com.nadeeshaabey.shopping_cart.exceptions.AlreadyExistsException;
import com.nadeeshaabey.shopping_cart.exceptions.ResourceNotFoundException;
import com.nadeeshaabey.shopping_cart.model.Product;
import com.nadeeshaabey.shopping_cart.request.AddProductRequest;
import com.nadeeshaabey.shopping_cart.request.ProductUpdateRequest;
import com.nadeeshaabey.shopping_cart.response.ApiResponse;
import com.nadeeshaabey.shopping_cart.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {

    private final IProductService productService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        List<ProductDTO> convertedProducts = productService.getConvertedProducts(products);
        return ResponseEntity.ok(new ApiResponse("Products fetched successfully", convertedProducts));
    }

    @GetMapping("/product/{productId}/product")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable("productId") Long id) {
        try {
            Product product = productService.getProductById(id);
            ProductDTO productDTO = productService.convertToDto(product);
            return ResponseEntity.ok(new ApiResponse("Product fetched successfully", productDTO));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest product) {
        try {
            Product theProduct = productService.addProduct(product);
            return ResponseEntity.ok(new ApiResponse("Product added successfully", theProduct));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/product/{productId}/update")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody ProductUpdateRequest request,@PathVariable Long productId) {
        try {
            Product theProduct = productService.updateProduct(request, productId);
            return ResponseEntity.ok(new ApiResponse("Product updated successfully", theProduct));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/product/{productId}/delete")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId) {
        try {
            productService.deleteProductById(productId);
            return ResponseEntity.ok(new ApiResponse("Product deleted successfully", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("by/brand-and-name")
    public ResponseEntity<ApiResponse> getProductsByBrandAndName(@RequestParam String brandName, @RequestParam String productName) {
        try {
            List<Product> products = productService.getProductByBrandAndName(brandName, productName);

            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found", null));
            }

            List<ProductDTO> convertedProducts = productService.getConvertedProducts(products);

            return ResponseEntity.ok(new ApiResponse("Products fetched successfully", convertedProducts));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("by/category-and-brand")
    public ResponseEntity<ApiResponse> getProductsByCategoryAndBrand(@RequestParam String category, @RequestParam String brand) {
        try {
            List<Product> products = productService.getProductsByCategoryAndBrand(category, brand);

            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found", null));
            }

            List<ProductDTO> convertedProducts = productService.getConvertedProducts(products);

            return ResponseEntity.ok(new ApiResponse("Products fetched successfully", convertedProducts));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/products/{name}/products")
    public ResponseEntity<ApiResponse> getProductsByName(@PathVariable String name) {
        try {
            List<Product> products = productService.getProductsByName(name);

            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found", null));
            }

            List<ProductDTO> convertedProducts = productService.getConvertedProducts(products);

            return ResponseEntity.ok(new ApiResponse("Products fetched successfully", convertedProducts));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/product/by-brand")
    public ResponseEntity<ApiResponse> getProductsByBrand(@RequestParam String brand) {
        try {
            List<Product> products = productService.getProductsByBrand(brand);

            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found", null));
            }

            List<ProductDTO> convertedProducts = productService.getConvertedProducts(products);

            return ResponseEntity.ok(new ApiResponse("Products fetched successfully", convertedProducts));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/product/{category}/all/products")
    public ResponseEntity<ApiResponse> findProductsByCategory(@PathVariable String category) {
        try {
            List<Product> products = productService.getProductsByCategory(category);

            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found", null));
            }

            List<ProductDTO> convertedProducts = productService.getConvertedProducts(products);

            return ResponseEntity.ok(new ApiResponse("Products fetched successfully", convertedProducts));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("product/count/by-brand/and-name")
    public ResponseEntity<ApiResponse> countProductsByBrandAndName(@RequestParam String brand, @RequestParam String name) {
        try {
            var productCount = productService.countProductsByBrandAndName(brand, name);
            return ResponseEntity.ok(new ApiResponse("Product count fetched successfully", productCount));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));
        }
    }

}
