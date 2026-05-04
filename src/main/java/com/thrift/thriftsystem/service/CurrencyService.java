package com.thrift.thriftsystem.service;

import com.thrift.thriftsystem.exception.BadRequestException;
import com.thrift.thriftsystem.model.Currency;
import com.thrift.thriftsystem.repository.CurrencyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CurrencyService {

    private final CurrencyRepository currencyRepository;
    private final RestTemplate restTemplate;

    @Value("${EXCHANGE_RATE_API_KEY}")
    private String apiKey;

    @Value("${EXCHANGE_RATE_BASE_URL}")
    private String baseUrl;

    public BigDecimal convert(BigDecimal amount, String fromCurrency,
                              String toCurrency) {
        if (fromCurrency.equals(toCurrency)) return amount;

        Currency from = currencyRepository.findByCode(fromCurrency)
                .orElseThrow(() -> new BadRequestException(
                        "Currency not found: " + fromCurrency));

        Currency to = currencyRepository.findByCode(toCurrency)
                .orElseThrow(() -> new BadRequestException(
                        "Currency not found: " + toCurrency));

        BigDecimal inNgn = amount.multiply(from.getExchangeRate());
        return inNgn.divide(to.getExchangeRate(), 2,
                java.math.RoundingMode.HALF_UP);
    }

    public void updateExchangeRates() {
        try {
            String url = baseUrl + "/" + apiKey + "/latest/NGN";
            Map response = restTemplate.getForObject(url, Map.class);

            if (response != null && response.get("conversion_rates") != null) {
                Map<String, Double> rates =
                        (Map<String, Double>) response.get("conversion_rates");

                List<Currency> currencies = currencyRepository.findByActiveTrue();
                currencies.forEach(currency -> {
                    if (rates.containsKey(currency.getCode())) {
                        currency.setExchangeRate(
                                BigDecimal.valueOf(rates.get(currency.getCode())));
                        currencyRepository.save(currency);
                        log.info("Rate updated for: {}", currency.getCode());
                    }
                });
            }
        } catch (Exception e) {
            log.error("Failed to update exchange rates: {}", e.getMessage());
        }
    }

    public List<Currency> getActiveCurrencies() {
        return currencyRepository.findByActiveTrue();
    }
}
