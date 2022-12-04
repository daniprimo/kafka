package br.com.daniel;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface ConsumeFunction {
    void consume(ConsumerRecord<String, String> record);
}
