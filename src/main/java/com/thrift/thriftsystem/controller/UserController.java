package com.thrift.thriftsystem.controller;

import com.thrift.thriftsystem.dto.request.RegisterRequest;
import com.thrift.thriftsystem.dto.response.ApiResponse;
import com.thrift.thriftsystem.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/avi/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<ApiResponse> getUserProfile(){
        return new ResponseEntity<>(ApiResponse.success("Profile found",userService.getCurrentUserProfile()), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<ApiResponse> getUserById(@RequestBody @Valid String id){
        return new ResponseEntity<>(ApiResponse.success("Uset found",userService.getUserById(id)), HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<ApiResponse> getAllUsers(){
        return new ResponseEntity<>(ApiResponse.success("All found",userService.getAllUsers()), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<ApiResponse> updateWhatsappNumber(@RequestBody @Valid String whatsappNumber){
        return new ResponseEntity<>(ApiResponse.success("whatsapp number updated",userService.updateWhatsappNumber(whatsappNumber)), HttpStatus.OK);

    }
    @PostMapping
    public ResponseEntity<ApiResponse> updateProfilePicture(@RequestBody @Valid String profilePictureUrl){
        return new ResponseEntity<>(ApiResponse.success("profile picture updated",userService.updateProfilePicture(profilePictureUrl)), HttpStatus.OK);
    }
}
