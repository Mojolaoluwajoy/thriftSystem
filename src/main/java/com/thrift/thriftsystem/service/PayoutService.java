package com.thrift.thriftsystem.service;

import com.thrift.thriftsystem.dto.request.PayoutRequest;
import com.thrift.thriftsystem.dto.response.PayoutResponse;
import com.thrift.thriftsystem.exception.BadRequestException;
import com.thrift.thriftsystem.exception.ResourceNotFoundException;
import com.thrift.thriftsystem.model.Membership;
import com.thrift.thriftsystem.model.Payout;
import com.thrift.thriftsystem.model.ThriftGroup;
import com.thrift.thriftsystem.model.User;
import com.thrift.thriftsystem.repository.MembershipRepository;
import com.thrift.thriftsystem.repository.PayoutRepository;
import com.thrift.thriftsystem.repository.ThriftGroupRepository;
import com.thrift.thriftsystem.util.PayoutMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PayoutService {

    private final PayoutRepository payoutRepository;
    private final MembershipRepository membershipRepository;
    private final ThriftGroupRepository groupRepository;
    private final UserService userService;
    private final EmailService emailService;

    public PayoutResponse schedulePayout(String groupId,String userId,Integer cycleNumber){
        ThriftGroup group = groupRepository.findById(groupId)
                .orElseThrow(()->new ResourceNotFoundException("group not found"));

        Membership membership=membershipRepository.findByUserIdAndGroupId(userId,groupId)
                .orElseThrow(()->new ResourceNotFoundException("membership not found"));

        if (payoutRepository.existsByUserIdAndGroupIdAndCycleNumber(userId,groupId,cycleNumber)) {
throw new BadRequestException("Payout already scheduled for this cycle");
        }
        Payout payout= PayoutMapper.toModel(userId,groupId, membership.getId(), group.getContributionAmount(),group.getCurrency(),cycleNumber, LocalDateTime.now().plusDays(1));
  payoutRepository.save(payout);
  log.info("Payout scheduled for user: {} in group: {}",userId,groupId);
  return PayoutMapper.toResponse(payout);

    }
    public List <PayoutResponse> getMyPayout(){
        User currentUser = userService.getCurrentUser();
        return payoutRepository.findByUserId(currentUser.getId())
                .stream()
                .map(PayoutMapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<PayoutResponse> getGroupPayouts(String groupId){
        return payoutRepository.findByGroupId(groupId)
                .stream()
                .map(PayoutMapper::toResponse)
                .collect(Collectors.toList());
    }
}
