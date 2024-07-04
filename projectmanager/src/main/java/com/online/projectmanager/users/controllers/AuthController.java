package com.online.projectmanager.users.controllers;

import com.online.projectmanager.users.services.UserService;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/")
public class AuthController {
    private  final UserService userService;


    @PostMapping("auth/register")
    public ResponseEntity<HashMap> createAccount(@RequestBody HashMap params){
        return new ResponseEntity<>(userService.validateUser( params), HttpStatus.OK);
    }
    @PostMapping("auth/login")
    public ResponseEntity<HashMap> loginUser(@RequestBody HashMap params){
        HashMap<String, Object> response = userService.login(params);
        if((Integer)userService.register(params).get("statusCode")!=200){
            return new ResponseEntity<>(userService.login(params), HttpStatusCode.valueOf((Integer) response.get("statusCode"))) ;
        }
        return new ResponseEntity<>(response, HttpStatus.OK) ;
    }
//    @GetMapping("/user")
//    public ResponseEntity<HashMap> getUser(@RequestParam("id") long id){
//        HashMap<String, Object> response = userService.getUser(id);
//        return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK) ;
//    }
    @GetMapping("/users")
    public ResponseEntity<HashMap> getUser(){
        HashMap<String, Object> response = userService.getAllUsers();
        return new ResponseEntity<>(response, HttpStatus.OK) ;
    }



}
