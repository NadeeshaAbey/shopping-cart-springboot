package com.nadeeshaabey.shopping_cart.service.user;

import com.nadeeshaabey.shopping_cart.model.User;
import com.nadeeshaabey.shopping_cart.request.CreateUserRequest;
import com.nadeeshaabey.shopping_cart.request.UpdateUserRequest;

public interface IUserService {

    User getUserById (Long userId);
    User createUser (CreateUserRequest request);
    User updateUser (UpdateUserRequest request, Long userId);
    void deleteUser (Long userId);


}
