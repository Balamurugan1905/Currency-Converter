package com.currency.currency_converter.contro;

import com.currency.currency_converter.serv.CurrencyService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@CrossOrigin(origins = "*") 
@RestController
public class CurrencyController {

    private final CurrencyService service;

    @GetMapping("/")
    public String home() {
        return "Currency Converter API is running 🚀";
    }

    @GetMapping("/convert")
    public Map<String, Object> convert(@RequestParam String from,
                                       @RequestParam String to,
                                       @RequestParam double amount) {

        double result = service.convert(from.toUpperCase(), to.toUpperCase(), amount);

        return Map.of(
                "from", from,
                "to", to,
                "amount", amount,
                "converted", result
        );
    }
}
