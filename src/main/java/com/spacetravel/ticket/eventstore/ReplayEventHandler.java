package com.spacetravel.ticket.eventstore;

import com.spacetravel.ticket.eventstore.entity.DomainEventEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.List;
import java.util.UUID;

@EnableBinding(Source.class)
public class ReplayEventHandler {

    Logger log = LoggerFactory.getLogger(ReplayEventHandler.class);

    @Autowired
    Source source;

    public void handleReplayByRootId(UUID rootId){

    }

    public void handleReplayAll(){

    }

    private final static String HEADER_EVENT_TYPE = "event_type";
    private final static String HEADER_EVENT_ID = "event_id";
    private final static String HEADER_EVENT_TIME = "event_time";
    private final static String HEADER_ROOT_ID = "root_id";

    private void pubMsg(List<DomainEventEntity> list){
        if(list==null || list.isEmpty()) throw new IllegalArgumentException("empty event list");

        final Source pub = source;

        list.stream().sorted((a,b)->Long.compare(a.getEventTime(),b.getEventTime()))
                .forEachOrdered(e->{
                    Message message = MessageBuilder
                            .withPayload(e.getPayload())
                            .setHeader(HEADER_EVENT_TYPE, e.getEventType())
                            .setHeader(HEADER_EVENT_TIME, e.getEventTime())
                            .setHeader(HEADER_EVENT_ID, e.getEventId())
                            .setHeader(HEADER_ROOT_ID, e.getRootId())
                            .build();

                    source.output().send(message);

                    log.info("eventType: " + e.getEventType() + ", payload=" + e.getPayload());

                });
    }
}
