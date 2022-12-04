package br.com.daniel;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.sql.SQLOutput;
import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;

public class FraudeDetectorService {

    public static final String ECOMMERCE_NEW_ORDER = "ECOMMERCE_NEW_ORDER";

    public static void main(String[] args) {
        var fraudeService = new FraudeDetectorService();
        var kafkaService = new KafkaService(FraudeDetectorService.class.getSimpleName(),
                ECOMMERCE_NEW_ORDER,
                fraudeService::parse);
        kafkaService.run();
    }

    private void parse(ConsumerRecord<String, String> record) {
        System.out.println("------------------------------");
        System.out.println("Processando nova ordem, checando se a fraude");
        System.out.println(record.key());
        System.out.println(record.value());
        System.out.println(record.partition());
        System.out.println(record.offset());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Ordem processada");
    }

}
