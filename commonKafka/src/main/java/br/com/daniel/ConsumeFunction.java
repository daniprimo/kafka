package br.com.daniel;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.concurrent.ExecutionException;

public interface ConsumeFunction<T> {
    void consume(ConsumerRecord<String, T> record) throws ExecutionException, InterruptedException;
}
