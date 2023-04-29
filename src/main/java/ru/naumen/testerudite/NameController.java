package ru.naumen.testerudite;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class NameController {

    @Autowired
    private FileService fileService;
    @Autowired
    private MetricService metricService;

    @GetMapping(path = "/getAgeByName")
    public Integer getAgeByName(@RequestParam String name) throws IOException {
        return fileService.getAge(name);
    }
    @GetMapping(path = "/getRequestsNumber")
    public String getRequestsNumber(@RequestParam String name) throws IOException {
        return fileService.getRequests(name);
    }
    @GetMapping(path = "/getMaxAge")
    public Integer getMaxAge() throws IOException {
        return fileService.getMaxAge();
    }
}
