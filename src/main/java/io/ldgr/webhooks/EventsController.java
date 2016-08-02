package io.ldgr.webhooks;

import io.ldgr.webhooks.model.Event;
import io.ldgr.webhooks.repository.EventRepository;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@EnableAutoConfiguration
public class EventsController {

  Logger logger = LoggerFactory.getLogger(EventsController.class);

  EventRepository eventRepository = new EventRepository();

  @RequestMapping(value = "/", method = RequestMethod.POST)
  @ResponseBody
  String recordEvent(
      @RequestBody Map<Object, Object> payload,
      @RequestParam("key") String key,
      HttpServletResponse response
  ) {
    logger.info("payload: " + payload.toString());
    Event event = Event.builder()
        .id(UUID.randomUUID())
        .timestamp(new Date())
        .value(payload)
        .build();
    logger.info("built event");
    try {
      response.setStatus(202);
      return eventRepository.save(event);
    } catch (Exception e) {
      logger.error(e.toString());
      response.setStatus(500);
      return "{\"error\": \"Internal Server Error\"}\n";
    }
  }

  public static void main(String[] args) throws Exception {
    SpringApplication.run(EventsController.class, args);
  }

}