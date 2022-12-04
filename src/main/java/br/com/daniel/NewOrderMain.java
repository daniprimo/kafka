package br.com.daniel;


import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ExecutionException;


public class NewOrderMain {

    public static final String ECOMMERCE_NEW_ORDER = "ECOMMERCE_NEW_ORDER";
    public static final String ECOMMERCE_SEND_EMAIL = "ECOMMERCE_SEND_EMAIL";

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        try (var orderDispatcher = new KafkaDispatcher<Order>()) {
            try (var emailDispatcher = new KafkaDispatcher<String>()) {

                for (var i = 0; i < 10; i++) {

                    var userId = UUID.randomUUID().toString();
                    var orderId = UUID.randomUUID().toString();
                    var amount = new BigDecimal(Math.random() * 5000 + 1);
                    var order = new Order(userId, orderId, amount);

                    orderDispatcher.send(ECOMMERCE_NEW_ORDER, userId, order);

                    var email = "Thank you for order! We are processing you order!";
                    emailDispatcher.send(ECOMMERCE_SEND_EMAIL, userId, email);

                }
            }

        }

    }


}
