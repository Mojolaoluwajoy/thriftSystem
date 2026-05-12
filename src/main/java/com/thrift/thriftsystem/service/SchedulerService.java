package com.thrift.thriftsystem.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SchedulerService {

    private final CurrencyService currencyService;

    @Scheduled(cron = "0 0 0 * * *")
    public void updateExchangeRates() {
        log.info("Scheduled exchange rate update starting...");
        currencyService.updateExchangeRates();
        log.info("Scheduled exchange rate update completed.");
    }
}