package com.jzsec.webclient.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
@EnableKafka
public class KafkaProducerConfig {

	public Map<String, Object> producerConfig()
	{
        Map<String, Object> props = new HashMap<String,Object>();
        
        //配置broker列表，host1:port1,host2:port2
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "10.1.171.31:9092");
        //消息发送失败，重发次数
        props.put(ProducerConfig.RETRIES_CONFIG, 0);
        //批量发送消息数量，当达到该消息数量才发送
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 0);
        //每次发送延迟，增加吞吐量，0则立即发往服务器不等待，单位：毫秒
        props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        //发送缓冲区，以字节单位
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 40960); //40K
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return props;		
	}
    public ProducerFactory<String, String> producerFactory() {
        return new DefaultKafkaProducerFactory(producerConfig());
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<String, String>(producerFactory());
    }
}
