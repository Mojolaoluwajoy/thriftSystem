package com.thrift.thriftsystem.repository;

import com.thrift.thriftsystem.enums.MembershipStatus;
import com.thrift.thriftsystem.model.Membership;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MembershipRepository extends MongoRepository<Membership,String> {

    List<Membership> findByGroupId(String groupId);
    List<Membership> findByUserId(String userId);
    List<Membership> findByUserIdAndGroupId(String userId,String groupId);
    List<Membership> findByGroupIdAndStatus(String groupId, MembershipStatus status);

    boolean existsByUserIdAndGroupId(String userId,String groupId);
    long countByGroupId(String userGroupId);
}
