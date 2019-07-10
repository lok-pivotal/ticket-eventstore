package com.spacetravel.ticket.eventstore.repo;

import com.spacetravel.ticket.eventstore.entity.DomainEventEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EventStore extends CrudRepository<DomainEventEntity, UUID> {

    List<DomainEventEntity> findByRootId(@Param("rootId") UUID rootId);
}
