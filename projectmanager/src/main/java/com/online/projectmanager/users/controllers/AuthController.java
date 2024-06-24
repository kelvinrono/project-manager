package com.online.projectmanager.users.controllers;

import com.online.projectmanager.users.services.UserService;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthController {
    private  final UserService userService;


    @PostMapping("/register")
    public ResponseEntity<HashMap> createAccount(@RequestBody HashMap params){
        return new ResponseEntity<>(userService.validateUser( params), HttpStatus.OK);
    }
    @PostMapping("/login")
    public ResponseEntity<HashMap> loginUser(@RequestBody HashMap params){
//        if(!userService.register(params).get("status")){
//            return new ResponseEntity<>(userService.login(params), HttpStatus.BAD_REQUEST) ;
//        }
        return new ResponseEntity<>(userService.login(params), HttpStatus.OK) ;
    }
//    @GetMapping("/user/")
//    public ResponseEntity<HashMap> getUser(@RequestParam("id") long id){
//        HashMap<String, Object> response = userService.getUser(id);
//        return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK) ;
//    }




}
