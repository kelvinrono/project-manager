package com.online.projectmanager.users.services;

import java.util.HashMap;

public interface UserService {
    HashMap register(HashMap params);
    HashMap login( HashMap params);
    HashMap validateUser(HashMap params);
}
