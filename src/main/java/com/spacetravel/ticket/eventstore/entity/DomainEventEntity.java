package com.spacetravel.ticket.eventstore.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.util.UUID;

@Entity(name = "DomainEvent")
@Table(name = "domain_event")
public class DomainEventEntity {

    @Id
    private UUID eventId;

    private String eventType;
    private long eventTime;
    private UUID rootId;

    @Lob
    private String payload;

    public UUID getEventId() {
        return eventId;
    }

    public void setEventId(UUID eventId) {
        this.eventId = eventId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public long getEventTime() {
        return eventTime;
    }

    public void setEventTime(long eventTime) {
        this.eventTime = eventTime;
    }

    public UUID getRootId() {
        return rootId;
    }

    public void setRootId(UUID rootId) {
        this.rootId = rootId;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}
