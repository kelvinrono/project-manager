package com.online.projectmanager.users.services;

import com.online.projectmanager.authentication.configs.JwtService;
import com.online.projectmanager.users.models.Role;
import com.online.projectmanager.users.models.User;
import com.online.projectmanager.users.repositories.UserRepository;
import com.smattme.requestvalidator.RequestValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
@Slf4j
public class UserServiceImpl implements  UserService{


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    @Override
    public HashMap register(HashMap params) {
        HashMap<String, Object> response=new HashMap<>();
        try {
            Optional<User> existingUser = userRepository.findByEmail(params.get("email").toString());
            if (existingUser.isPresent()){
                response.put("message", "user already exist");
                response.put("status", false);
                response.put("statusCode", 406);
            }
            else {
                String encodedPassword  = passwordEncoder.encode(params.get("password").toString());
                User newUser = User.builder()
                        .email(params.get("email").toString().toLowerCase())
                        .firstName(params.get("firstName").toString())
                        .lastName(params.get("lastName").toString())
                        .phoneNumber(params.get("phoneNumber").toString())
                        .password(encodedPassword)
                        .role(Role.USER)
                        .build();
                userRepository.save(newUser);
                response.put("message", "User created successfully");
                response.put("status", true);
                response.put("statusCode", 200);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            log.error("error::::"+e.getMessage());
            response.put("message", "Oops! Something went wrong");
            response.put("status", false);
        }
        return response;
    }

    @Override
    public HashMap login(HashMap params) {
        HashMap<String, Object> response = new HashMap();
        try {
            Optional<User> user = userRepository.findByEmail(params.get("email").toString().toLowerCase());
            if(user.isEmpty()){
                response.put("message", "User does not exist");
                response.put("status", false);
                response.put("statusCode", 404);
                log.info("...User not found....");
            }
            else if(!passwordEncoder.matches(params.get("password").toString(), user.get().getPassword())){
                response.put("message", "invalid credentials");
                response.put("status", "false");
                response.put("statusCode", 401);
                log.info("....invalid credentials...");
            }
            else {
                var jwtToken = jwtService.generateToken(user.get());
                response.put("message", "login successful");
                response.put("status", true);
                response.put("token", jwtToken);
                response.put("statusCode", 200);
                log.info("...Login successful....");
            }

        }catch (Exception e){
            log.info(e.getMessage());
            e.printStackTrace();
            response.put("message", "Oops! An error occurred!");
            response.put("status", false);
            response.put("statusCode", 500);
            return response;
        }
        return response;

    }

    public HashMap getAllUsers(){
        HashMap<String, Object> response = new HashMap<>();
        try{
            List<User> users = userRepository.findAll();
            response.put("data", users);
            response.put("status", true);
            return response;
        }
        catch (Exception e){
            log.info(e.getMessage());
            e.printStackTrace();
            response.put("message", "Oops! An error occurred!");
            response.put("status", false);
            return response;
        }


    }
    public HashMap validateUser(HashMap params) {

        HashMap<String, Object> response = new HashMap<>();

        HashMap<String, String> rules = new HashMap<>();

        try {
            rules.put("firstName", "required");
            rules.put("lastName", "required");
            rules.put("email", "required");
            rules.put("phoneNumber", "required");
            rules.put("password", "required");
            List<String> errors = RequestValidator.validate(params, rules);
            if (!errors.isEmpty()) {
                log.info("An error occurred...");
                response.put("message", "All fields are required");
                response.put("errors", errors);
                response.put("status", false);
                return response;
            } else {
                log.info("Parameters validated");
                return register(params);
            }

        } catch (Exception e) {
            log.info(e.getMessage());
            e.printStackTrace();
            response.put("message", "Oops! An error occurred!");
            response.put("status", false);
            return response;
        }
    }

    @Override
    public Optional<User> getUser(long id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()){
            return null;
        }else {
            return user;
        }
    }
}
