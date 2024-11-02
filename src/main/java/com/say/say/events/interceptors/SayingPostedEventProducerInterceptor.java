package com.say.say.events.interceptors;

import java.util.Map;
import java.util.UUID;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.header.Headers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.say.say.generated.avro.SayingPostedEvent;

public class SayingPostedEventProducerInterceptor implements ProducerInterceptor<SayingPostedEvent, String> {
	final static Logger log = LoggerFactory.getLogger(SayingPostedEventProducerInterceptor.class);

	public ProducerRecord<SayingPostedEvent, String> onSend(ProducerRecord<SayingPostedEvent, String> record) {
		Headers headers = record.headers();
		String sayingCreatedTraceId = UUID.randomUUID().toString();
		headers.add("sayingCreatedTraceId", sayingCreatedTraceId.getBytes());
		log.info("Created sayingCreatedTraceId: {}", sayingCreatedTraceId);
		return record;
	}

	public void onAcknowledgement(RecordMetadata metadata, Exception exception) {
		if (exception != null) {
			log.info("sayingCreated_error " + exception.getMessage());
		} else {
			log.info("sayingCreated_info topic = {} offset = {}", metadata.topic(), metadata.offset());
		}
	}

	@Override
	public void close() {

	}

	@Override
	public void configure(final Map<String, ?> map) {

	}
}
