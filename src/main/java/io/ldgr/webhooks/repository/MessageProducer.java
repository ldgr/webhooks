package io.ldgr.webhooks.repository;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

public class MessageProducer {

  String topic;
  private Producer<String, String> producer;

  public MessageProducer(Properties producerProps, String topic) {
    this.topic = topic;
    this.producer = new KafkaProducer<>(producerProps);
  }

  public static MessageProducer create() {
    Properties producerProps = new Properties();
    producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
    producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
        "org.apache.kafka.common.serialization.StringSerializer");
    producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
        "org.apache.kafka.common.serialization.StringSerializer");
    producerProps.put(ProducerConfig.ACKS_CONFIG, Integer.toString(1));
    producerProps.put("retries", "0");
    producerProps.put("producer.type", "sync");
    MessageProducer producer = new MessageProducer(producerProps, "default");
    return producer;
  }

  public void send(String key, String value) throws InterruptedException, ExecutionException {
    ProducerRecord<String, String> record   = new ProducerRecord<String, String>(topic, key, value);
    RecordMetadata                 response = producer.send(record).get();
  }

}