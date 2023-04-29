package ru.naumen.testerudite;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MetricService {

    @Autowired
    private MeterRegistry registry;

    public void increaseCount(String request, String name) {
        String counterName = "counter.name." + name;
        registry.counter(counterName).increment(1);
    }
}
