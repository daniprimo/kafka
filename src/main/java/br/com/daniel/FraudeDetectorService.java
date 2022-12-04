package br.com.daniel;

import org.apache.kafka.clients.consumer.ConsumerRecord;


public class FraudeDetectorService {

    public static final String ECOMMERCE_NEW_ORDER = "ECOMMERCE_NEW_ORDER";

    public static void main(String[] args) {
        var fraudeService = new FraudeDetectorService();
       try (var kafkaService = new KafkaService(FraudeDetectorService.class.getSimpleName(),
                ECOMMERCE_NEW_ORDER,
                fraudeService::parse)){
           kafkaService.run();
       }
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
