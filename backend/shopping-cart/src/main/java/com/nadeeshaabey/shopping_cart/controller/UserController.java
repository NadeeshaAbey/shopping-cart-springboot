package com.nadeeshaabey.shopping_cart.controller;

import com.nadeeshaabey.shopping_cart.dto.UserDTO;
import com.nadeeshaabey.shopping_cart.exceptions.AlreadyExistsException;
import com.nadeeshaabey.shopping_cart.exceptions.ResourceNotFoundException;
import com.nadeeshaabey.shopping_cart.model.User;
import com.nadeeshaabey.shopping_cart.request.CreateUserRequest;
import com.nadeeshaabey.shopping_cart.request.UpdateUserRequest;
import com.nadeeshaabey.shopping_cart.response.ApiResponse;
import com.nadeeshaabey.shopping_cart.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/users")
public class UserController {

    private final IUserService userService;


    @GetMapping("{userId}/user")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long userId){
        try {
            User user = userService.getUserById(userId);
            UserDTO userDTO = userService.convertUserToDTO(user);
            return ResponseEntity.ok(new ApiResponse("Success", userDTO));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createUser (@RequestBody CreateUserRequest request){
        try {
            User user = userService.createUser(request);
            UserDTO userDTO = userService.convertUserToDTO(user);
            return ResponseEntity.ok(new ApiResponse("User created successfully", userDTO));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/{userId}/update")
    public ResponseEntity<ApiResponse> updateUser (@RequestBody UpdateUserRequest request, @PathVariable Long userId){
        try {
            User user = userService.updateUser(request, userId );
            UserDTO userDTO = userService.convertUserToDTO(user);
            return ResponseEntity.ok(new ApiResponse("User updated successfully", userDTO));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/{userId}/delete")
    public ResponseEntity<ApiResponse> deleteUser (@PathVariable Long userId){
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok(new ApiResponse("User deleted successfully", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
