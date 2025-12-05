package com.currency.currency_converter.serv;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class CurrencyService {

    private static final String API_KEY = "0a8ae2b0f5263684933f61c1"; // Put your key
    private static final String API_TEMPLATE = "https://v6.exchangerate-api.com/v6/%s/latest/%s";

    public double convert(String from, String to, double amount) {

        RestTemplate rest = new RestTemplate();
        String url = String.format(API_TEMPLATE, API_KEY, from);

        try {
            @SuppressWarnings("unchecked")
            LinkedHashMap<String, Object> response =
                    rest.getForObject(url, LinkedHashMap.class);

            if (response == null) {
                throw new IllegalStateException("No response from API.");
            }

            Object result = response.get("result");
            if (!"success".equals(result)) {
                throw new IllegalStateException("API error: " + response.get("error-type"));
            }

            @SuppressWarnings("unchecked")
            Map<String, Object> rates = (Map<String, Object>) response.get("conversion_rates");

            if (rates == null) {
                throw new IllegalStateException("No conversion rates returned.");
            }

            Object rateObj = rates.get(to);
            if (rateObj == null) {
                throw new IllegalArgumentException("Currency not found: " + to);
            }

            double rate = Double.parseDouble(rateObj.toString());
            return amount * rate;

        } catch (RestClientException ex) {
            throw new IllegalStateException("API call failed: " + ex.getMessage());
        }
    }
}
