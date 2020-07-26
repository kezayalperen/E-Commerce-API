package com.bigdatacompany.eticaret;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import  org.springframework.stereotype.Component;
import org.apache.kafka.clients.producer.ProducerConfig;

import javax.annotation.PostConstruct;
import java.util.Properties;

@Component
public class MessageProducer {

    Producer producer;

    // Bir kere çalıştırmak için kullanıyoruz. Kafkaya bir kere bağlanacağız her geldiğinde bağlanırsak Kafka'ya istek yığını oluşur.

    @PostConstruct
    public void init(){


        // Kafka bağlantısını yapıyoruz.
        Properties config = new Properties();

        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"134.122.108.9:9092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,new StringSerializer().getClass().getName());
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,new StringSerializer().getClass().getName());

        // Kafka producerını oluşturuyoruz.
        producer = new KafkaProducer<String,String>(config);
    }

    // verileri kafkaya gönderiyoruz.

    public void send(String term){
        ProducerRecord<String,String> rec = new ProducerRecord<String, String>("searchv2", term);
        producer.send(rec);
    }

    // kafkayı kapatıyoruz.
    public void close(){
        producer.close();
    }
}
