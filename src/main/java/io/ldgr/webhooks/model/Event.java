package io.ldgr.webhooks.model;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Event {

  UUID id;
  Date timestamp;
  Map<Object, Object> value;

}