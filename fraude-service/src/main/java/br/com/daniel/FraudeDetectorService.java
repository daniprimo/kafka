package br.com.daniel;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;


public class FraudeDetectorService {

    public static final String ECOMMERCE_NEW_ORDER = "ECOMMERCE_NEW_ORDER";

    public static void main(String[] args) {
        var fraudeService = new FraudeDetectorService();
       try (var kafkaService = new KafkaService<Order>(FraudeDetectorService.class.getSimpleName(),
                ECOMMERCE_NEW_ORDER,
                fraudeService::parse,
               Order.class,
               new HashMap<String, String>())){
           kafkaService.run();
       }
    }

    private final KafkaDispatcher<Order> orderDispatcher = new KafkaDispatcher<>();

    private void parse(ConsumerRecord<String, Order> record) throws ExecutionException, InterruptedException {
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
        var order = record.value();
        if(isFraude(order)) {
            System.out.println("Ordem é fraude" + order.toString());
            orderDispatcher.send("ECOMMERCE_ORDER_REJECTED",
                    order.getUserID(),
                    order);
        }else {
            System.out.println("Aprovado " + order.toString());
            orderDispatcher.send("ECOMMERCE_ORDER_APPROVED",
                    order.getUserID(),
                    order);
        }


    }

    private boolean isFraude(Order order) {
        return order.getAmount().compareTo(new BigDecimal("4500")) >= 0;
    }

}
