package br.com.daniel;


import java.util.UUID;
import java.util.concurrent.ExecutionException;


public class NewOrderMain {

    public static final String ECOMMERCE_NEW_ORDER = "ECOMMERCE_NEW_ORDER";
    public static final String ECOMMERCE_SEND_EMAIL = "ECOMMERCE_SEND_EMAIL";

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        try (var dispatcher = new KafkaDispatcher()) {
            for (var i = 0 ; i < 10 ; i++) {
                var key = UUID.randomUUID().toString();
                var value = "ID:" + key.toString() + " , Nome Acacio Neves, Prod: Ervilha";
                dispatcher.send(ECOMMERCE_NEW_ORDER, key, value);

                var email = "Thank you for order! We are processing you order!";
                dispatcher.send(ECOMMERCE_SEND_EMAIL, key, email);

            }
        }

    }




}
