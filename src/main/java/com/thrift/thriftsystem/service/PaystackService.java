package com.thrift.thriftsystem.service;

import com.thrift.thriftsystem.exception.BadRequestException;
import com.thrift.thriftsystem.integration.PaystackClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaystackService {

    private final PaystackClient paystackClient;

    public Map<String, Object> initializePayment(String email,
                                                 BigDecimal amount, String reference) {
        try {
            Map<String, Object> request = new HashMap<>();
            request.put("email", email);
            // Paystack expects amount in kobo (multiply by 100)
            request.put("amount", amount.multiply(BigDecimal.valueOf(100))
                    .intValue());
            request.put("reference", reference);
            request.put("currency", "NGN");

            Map<String, Object> response =
                    paystackClient.initializeTransaction(request);

            log.info("Payment initialized for: {}", email);
            return response;
        } catch (Exception e) {
            log.error("Payment initialization failed: {}", e.getMessage());
            throw new BadRequestException("Payment initialization failed");
        }
    }

    public boolean verifyPayment(String reference) {
        try {
            Map<String, Object> response =
                    paystackClient.verifyTransaction(reference);

            Map<String, Object> data =
                    (Map<String, Object>) response.get("data");

            if (data != null) {
                String status = (String) data.get("status");
                return "success".equals(status);
            }
            return false;
        } catch (Exception e) {
            log.error("Payment verification failed: {}", e.getMessage());
            return false;
        }
    }

    public Map<String, Object> createTransferRecipient(String name,
                                                       String accountNumber, String bankCode) {
        try {
            Map<String, Object> request = new HashMap<>();
            request.put("type", "nuban");
            request.put("name", name);
            request.put("account_number", accountNumber);
            request.put("bank_code", bankCode);
            request.put("currency", "NGN");

            Map<String, Object> response =
                    paystackClient.createTransferRecipient(request);

            log.info("Transfer recipient created for: {}", name);
            return response;
        } catch (Exception e) {
            log.error("Failed to create transfer recipient: {}", e.getMessage());
            throw new BadRequestException("Failed to create transfer recipient");
        }
    }

    public Map<String, Object> initiateTransfer(String recipientCode,
                                                BigDecimal amount, String reason) {
        try {
            Map<String, Object> request = new HashMap<>();
            request.put("source", "balance");
            request.put("amount", amount.multiply(BigDecimal.valueOf(100))
                    .intValue());
            request.put("recipient", recipientCode);
            request.put("reason", reason);

            Map<String, Object> response =
                    paystackClient.initiateTransfer(request);

            log.info("Transfer initiated: {}", reason);
            return response;
        } catch (Exception e) {
            log.error("Transfer initiation failed: {}", e.getMessage());
            throw new BadRequestException("Transfer initiation failed");
        }
    }

    public String generateReference() {
        return "THRIFT-" + System.currentTimeMillis();
    }
}