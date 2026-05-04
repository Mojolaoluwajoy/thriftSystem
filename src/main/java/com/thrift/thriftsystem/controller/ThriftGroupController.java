package com.thrift.thriftsystem.controller;

import com.thrift.thriftsystem.dto.request.CreateGroupRequest;
import com.thrift.thriftsystem.dto.response.ApiResponse;
import com.thrift.thriftsystem.dto.response.GroupResponse;
import com.thrift.thriftsystem.model.ThriftGroup;
import com.thrift.thriftsystem.service.ThriftGroupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class ThriftGroupController {

    private final ThriftGroupService thriftGroupService;

    @PostMapping
    public ResponseEntity<ApiResponse> createGroup(@RequestBody @Valid CreateGroupRequest createGroupRequest){
        GroupResponse response=thriftGroupService.createGroup(createGroupRequest);
        return new ResponseEntity<>(ApiResponse.success("Group successfully created",response), HttpStatus.OK);
    }
@GetMapping
public ResponseEntity<ApiResponse> getMyGroups(){
        return new ResponseEntity<>(ApiResponse.success("Your groups has been found",thriftGroupService.getMyGroups()),HttpStatus.OK);
}

@PostMapping
    public ResponseEntity<ApiResponse> getGroupById(@RequestBody @Valid String id){
        GroupResponse response=thriftGroupService.getGroupById(id);
        return new ResponseEntity<>(ApiResponse.success("Group with id found",response), HttpStatus.OK);
}

    @PostMapping
    public ResponseEntity<ApiResponse> addMember(@RequestBody @Valid String groupId,@RequestBody @Valid String userId){
        return new ResponseEntity<>(ApiResponse.success("Member added successfully",thriftGroupService.addMember(groupId,userId)), HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<ApiResponse> getAllGroups(){
        return new ResponseEntity<>(ApiResponse.success("All groups has been found",thriftGroupService.getAllGroups()), HttpStatus.OK);
    }

}
