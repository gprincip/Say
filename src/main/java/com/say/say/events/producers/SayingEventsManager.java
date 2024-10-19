package com.say.say.events.producers;

import java.util.concurrent.Future;

import org.apache.kafka.clients.producer.RecordMetadata;

import com.say.say.model.Saying;

public interface SayingEventsManager {

	Future<RecordMetadata> generateSayingCreatedEvent(Saying saying);

}