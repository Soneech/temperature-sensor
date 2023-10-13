package org.soneech.controller;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/sensors")
public class SensorController {
    private final String BASE_URL = "http://localhost:8080/sensors";
    private final RestTemplate restTemplate = new RestTemplate();
    @GetMapping
    public ResponseEntity<Object> getAllSensors() throws HttpStatusCodeException {
        return restTemplate.exchange(BASE_URL, HttpMethod.GET, null,
                new ParameterizedTypeReference<Object>() {});
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getSensorById(@PathVariable("id") Long id) throws HttpStatusCodeException {
        return restTemplate.exchange(
                BASE_URL + "/" + id, HttpMethod.GET, null, new ParameterizedTypeReference<Object>(){});
    }

    @PostMapping("/registration")
    public ResponseEntity<Object> registerSensor(@RequestBody Object requestObject) throws HttpStatusCodeException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> request = new HttpEntity<>(requestObject, headers);

        return restTemplate.exchange(
                BASE_URL + "/registration", HttpMethod.POST, request, new ParameterizedTypeReference<Object>(){});
    }
}
