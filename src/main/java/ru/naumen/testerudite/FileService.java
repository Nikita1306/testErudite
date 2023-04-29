package ru.naumen.testerudite;

import net.minidev.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class FileService {

    private int maxAge = 0;
    //Список случайных имен для запроса к api.agify.io
    private final String[] randomNames = new String[]{"John", "George", "Jack"};
    private HashMap<String, Integer> namesAndAge = new HashMap<>();
    public void getNamesFromFile() throws IOException {
        String filePath = new File("").getAbsolutePath();

        //Местоположение файла с именами
        List<String> rows = Files.readAllLines(Paths.get(filePath + "\\src\\main\\resources\\static\\names.txt"));
        for (String row : rows) {
            String[] splitRow = row.split("_");
            int age = Integer.parseInt(splitRow[1]);
            if (age > maxAge) {
                maxAge = age;
            }
            namesAndAge.put(splitRow[0], age);
        }
    }

    public Integer getAge(String name) {
        RestTemplate restTemplate = new RestTemplate();
        if (namesAndAge.containsKey(name)) {
            return namesAndAge.get(name);
        }
        else {
            RestTemplate rest = new RestTemplate();
            // В случае, если сервер agify не отвечает, возвращаем случайное положительное число
            try {
                JSONObject random = rest.getForObject("https://api.agify.io/?name=" + randomNames[new Random().nextInt(randomNames.length)], JSONObject.class);
                return Integer.valueOf(random.get("age").toString());
            } catch (RestClientException exception) {
                return ThreadLocalRandom.current().nextInt(1, Integer.MAX_VALUE);

            }
        }

    }
    public String getRequests(String name) {
        RestTemplate restTemplate = new RestTemplate();
        JSONObject metric = new JSONObject();
        try {
            metric = restTemplate.getForObject("http://localhost:8080/actuator/metrics/counter.name." + name, JSONObject.class);
        } catch (HttpClientErrorException exception) {
            return "0";
        }
        List<Object> test = (List<Object>)metric.get("measurements");
        return test.get(0).toString().split(",")[1].split("=")[1].replace("}", "");

    }
    public Integer getMaxAge() {
        return maxAge;
    }
}
