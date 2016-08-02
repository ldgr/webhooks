package io.ldgr.webhooks.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.ldgr.webhooks.model.Event;
import java.util.concurrent.ExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventRepository {

  Logger logger = LoggerFactory.getLogger(EventRepository.class);

  private MessageProducer engine;
  private ObjectMapper    mapper;

  public EventRepository() {
    this.engine = MessageProducer.create();
    this.mapper = new ObjectMapper();
  }

  public String save(Event event) throws JsonProcessingException,
                                         InterruptedException,
                                         ExecutionException {
    String message = this.mapper.writeValueAsString(event);
    this.engine.send(null, message);
    return message;
  }

}