package com.bigdatacompany.eticaret.api;

import com.bigdatacompany.eticaret.MessageProducer;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@RestController
public class SearchController {

    // Class oluşturma olayını developerdan alıyor. Eğer class varsa tekrardan oluşturmasını engelliyor.
    @Autowired
    MessageProducer messageProducer;

    // Map işlemi ile postman üzerinden gelen verileri term alanında tutuyoruz.
    // With the map operation, we keep the data from postman in the term field.

    @GetMapping("/search")
    public void searchIndex(@RequestParam String term){

        // Şehir bilgilerini random olarak atamak için arraylist oluşturup, random kütüphanesi kullanarak indexlerine göre çağırıyoruz.
        // In order to assign city information randomly, we create an arraylist and call it according to indexes using the random library.

        List<String> cities = Arrays.asList("Ankara","İstanbul","Sivas","İzmir","Manisa","Kayseri","Trabzon","Konya","Uşak","Adana","Niğde");

        Random random = new Random();

        int i = random.nextInt(cities.size());

        // Gelen verilerin ne zaman gönderildiğini TimeStamp kullanarak bildiriyoruz.
        // We report when the incoming data is sent using TimeStamp.

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        // Verileri JSON dosyasına çeviriyoruz.
        // We convert the data to JSON file.

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("search",term);
        jsonObject.put("timestamp",timestamp);
        jsonObject.put("region",cities.get(i));

        // Verileri ham haliyle konsola yazdırıyoruz.
        // We print the data in its raw form to the console.

        // System.out.println(term+" "+timestamp+" "+cities.get(i));

        // Verileri JSON dosyası halinde konsola yazdırıyoruz.
        // We print the data into the console as a JSON file.

        // System.out.println(jsonObject.toJSONString());

        // Verileri messageProducer üzerinden kafkaya gönderme.

        messageProducer.send(jsonObject.toJSONString());
    }
}
