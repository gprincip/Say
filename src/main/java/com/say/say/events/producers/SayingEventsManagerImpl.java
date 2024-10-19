package com.say.say.events.producers;

import java.util.Properties;
import java.util.concurrent.Future;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.stereotype.Service;

import com.say.say.generated.avro.SayingPostedEvent;
import com.say.say.model.Saying;

@Service
public class SayingEventsManagerImpl implements SayingEventsManager {

	@Override
	public Future<RecordMetadata> generateSayingCreatedEvent(Saying saying){
		
		Properties producerProperties = new Properties();
	    producerProperties.put("bootstrap.servers", "localhost:9092,localhost:9093,localhost:9094");
	    producerProperties.put("key.serializer", "org.apache.kafka.common.serialization.LongSerializer");
	    producerProperties.put("value.serializer", "io.confluent.kafka.serializers.KafkaAvroSerializer");
	    producerProperties.put("schema.registry.url", "http://localhost:8081");

	    try(Producer<Long, SayingPostedEvent> producer = new KafkaProducer<Long, SayingPostedEvent>(producerProperties)){
	    	
	    	SayingPostedEvent sayingEvent = new SayingPostedEvent();
	    	sayingEvent.setSayingId(saying.getId());
	    	sayingEvent.setText(saying.getText());
	    	
	    	ProducerRecord<Long, SayingPostedEvent> record = new ProducerRecord<>("say_saying_posted", sayingEvent);
	    	
	    	Future<RecordMetadata> recordMetadata = producer.send(record);
	    	return recordMetadata;
	    }
	}
	
}
