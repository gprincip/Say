package com.say.say.events.consumers;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.say.say.generated.avro.SayingPostedEvent;

@Service
public class SayingEventsConsumerImpl implements SayingEventsConsumer {

	private static final String SAYING_TOPIC_NAME = "say_saying_posted";

	Logger log = LoggerFactory.getLogger(SayingEventsConsumerImpl.class);

	private static final String SAYING_CONSUMER_GROUP_ID = "0";

	@Override
	@Async
	public void consumeSayings() {

		Properties kaProperties = new Properties();
		kaProperties.put("bootstrap.servers", "localhost:9092,localhost:9093");
		kaProperties.put("enable.auto.commit", "false");
		kaProperties.put("group.id", SAYING_CONSUMER_GROUP_ID);
		kaProperties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		kaProperties.put("value.deserializer", "io.confluent.kafka.serializers.KafkaAvroDeserializer");
		kaProperties.put("schema.registry.url", "http://localhost:8081");
		
		try (KafkaConsumer<String, SayingPostedEvent> consumer = new KafkaConsumer<>(kaProperties)) {

			consumer.subscribe(List.of(SAYING_TOPIC_NAME));

			while (true) {
				ConsumerRecords<String, SayingPostedEvent> records = consumer.poll(Duration.ofMillis(250));
				for (ConsumerRecord<String, SayingPostedEvent> record : records) {
					log.info("kinaction_info offset = {}, value = {}", record.offset(), record.value());

					OffsetAndMetadata offsetMeta = new OffsetAndMetadata(record.offset() + 1, "");

					Map<TopicPartition, OffsetAndMetadata> kaOffsetMap = new HashMap<>();
					kaOffsetMap.put(new TopicPartition(SAYING_TOPIC_NAME, record.partition()), offsetMeta);

					consumer.commitSync(kaOffsetMap);
				}
			}
		}

	}

}
