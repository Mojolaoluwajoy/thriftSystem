package com.thrift.thriftsystem.integration;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;
import java.util.Map;

@FeignClient(name = "holidayClient",
        url = "https://date.nager.at/api/v3")
public interface HolidayClient {

    @GetMapping("/PublicHolidays/{year}/{countryCode}")
    List<Map<String, Object>> getPublicHolidays(
            @PathVariable int year,
            @PathVariable String countryCode);
}