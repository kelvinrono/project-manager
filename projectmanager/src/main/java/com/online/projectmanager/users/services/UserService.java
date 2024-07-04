package com.online.projectmanager.users.services;

import com.online.projectmanager.users.models.User;

import java.util.HashMap;
import java.util.Optional;

public interface UserService {
    HashMap register(HashMap params);
    HashMap login( HashMap params);
    HashMap validateUser(HashMap params);

    HashMap getAllUsers ();
    Optional <User> getUser(long id);
}
