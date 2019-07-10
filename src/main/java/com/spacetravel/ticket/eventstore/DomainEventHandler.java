package com.spacetravel.ticket.eventstore;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spacetravel.ticket.eventstore.entity.DomainEventEntity;
import com.spacetravel.ticket.eventstore.repo.EventStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.UUID;

@EnableBinding(Sink.class)
public class DomainEventHandler {

    @Autowired
    EventStore eventStore;

    @StreamListener(Sink.INPUT)
    public void handleEvent(@Payload Object payload,
                            @Header(name="event_type") String eventType,
                            @Header(name="event_id") String eventId,
                            @Header(name="event_time") Long eventTime,
                            @Header(name="root_id") String rootId){

        if(payload==null) throw new IllegalArgumentException("payload cannot be null");

        if(eventId==null) throw new IllegalArgumentException("eventId cannot be null");

        DomainEventEntity entity = new DomainEventEntity();
        entity.setEventId(UUID.fromString(eventId));
        entity.setEventTime(eventTime);
        entity.setEventType(eventType);
        entity.setRootId(UUID.fromString(rootId));
        String payloadLob = null;
        try{ payloadLob = (new ObjectMapper()).writeValueAsString(payload); }
        catch (JsonProcessingException je) { throw new IllegalArgumentException("fail to convert payload into json string", je); }

        entity.setPayload(payloadLob);

        eventStore.save(entity);
    }
}
