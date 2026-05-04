package com.thrift.thriftsystem.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class HolidayService {

    private final RestTemplate restTemplate;
    private static final String BASE_URL =
            "https://date.nager.at/api/v3/PublicHolidays";
    private static final String COUNTRY_CODE = "NG";

    public boolean isPublicHoliday(LocalDate date) {
        try {
            List<Map> holidays = getHolidaysForYear(date.getYear());
            return holidays.stream()
                    .anyMatch(h -> h.get("date").equals(date.toString()));
        } catch (Exception e) {
            log.error("Failed to check public holiday: {}", e.getMessage());
            return false;
        }
    }

    public LocalDate adjustForHoliday(LocalDate date) {
        while (isPublicHoliday(date) || isWeekend(date)) {
            date = date.plusDays(1);
        }
        return date;
    }

    private boolean isWeekend(LocalDate date) {
        return date.getDayOfWeek().getValue() >= 6;
    }

    private List<Map> getHolidaysForYear(int year) {
        String url = BASE_URL + "/" + year + "/" + COUNTRY_CODE;
        Map[] response = restTemplate.getForObject(url, Map[].class);
        return response != null ? Arrays.asList(response) : List.of();
    }
}
