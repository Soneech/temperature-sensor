package org.soneech.controller;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RestController
@RequestMapping("/measurements")
public class MeasurementController {
    private final String BASE_URL = "http://localhost:8080/measurements";
    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping
    public ResponseEntity<Object> getAllMeasurements() throws HttpStatusCodeException {
        return restTemplate.exchange(BASE_URL, HttpMethod.GET, null,
                new ParameterizedTypeReference<Object>() {});

    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getMeasurementById(@PathVariable("id") Long id) throws HttpStatusCodeException {
        return restTemplate.exchange(BASE_URL + "/" + id, HttpMethod.GET, null,
                new ParameterizedTypeReference<Object>() {});
    }

    @GetMapping("/rainyDaysCount")
    public ResponseEntity<Object> getRainyDaysCount() {
        return restTemplate.exchange(BASE_URL + "/rainyDaysCount", HttpMethod.GET, null,
                new ParameterizedTypeReference<Object>() {});
    }

    @PostMapping("/add")
    public ResponseEntity<Object> saveMeasurement(@RequestBody Object requestObject) throws HttpStatusCodeException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> request = new HttpEntity<>(requestObject, headers);

        return restTemplate.exchange(BASE_URL + "/add", HttpMethod.POST, request,
                new ParameterizedTypeReference<Object>() {});
    }

    @PostMapping("/addAll")
    public ResponseEntity<Object> saveRandomMeasurements(@RequestParam(name = "count") int count,
                                                         @RequestParam(name = "sensor_id") Long sensorId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Random random = new Random();
        int tempMin = -100;
        int tempMax = 100;
        double randomTemp;
        boolean randomIsRainy;

        List<Map<String, Object>> measurements = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            randomTemp = tempMin + (tempMax - tempMin) * random.nextDouble();
            randomIsRainy = random.nextBoolean();

            Map<String, Object> measurement = new HashMap<>();
            measurement.put("value", randomTemp);
            measurement.put("raining", randomIsRainy);
            measurement.put("sensor_id", sensorId);
            measurements.add(measurement);
        }

        HttpEntity<List<Map<String, Object>>> request = new HttpEntity<>(measurements, headers);
        return restTemplate.exchange(BASE_URL + "/addAll", HttpMethod.POST, request,
                new ParameterizedTypeReference<Object>() {});
    }
}
