package com.fredo.martinez.kafkaproducer.config;

import com.fredo.martinez.kafkaproducer.model.SampleEvent;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

@Configuration
public class SenderConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(SenderConfig.class);

    @Value("${kafka.config.path}")
    private String KAFKA_CONFIG_PATH;

    @Value("${kafka.bootstrap.servers}")
    private String BOOTSTRAP_SERVERS;

    @Bean
    public Properties producerConfigs() {

        Properties props = new Properties();

        setupBootstrapAndSerializers(props);
        setupBatchingAndCompression(props);
        setupRetriesConfig(props);

        try {
            loadConfig(props, KAFKA_CONFIG_PATH);
        } catch (IOException e) {
            LOGGER.warn(e.getMessage());
        }

        return props;
    }

    @Bean
    public Producer<String, SampleEvent> producer() {
        return new KafkaProducer<>(producerConfigs());
    }

    private static void loadConfig(Properties props, String configPath) throws IOException {
        if (!Files.exists(Paths.get(configPath))) {
            throw new IOException(configPath + " not found.");
        }
        try (InputStream inputStream = new FileInputStream(configPath)) {
            props.load(inputStream);
        }
    }

    private void setupBootstrapAndSerializers(Properties props) {
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
    }

    private void setupBatchingAndCompression(final Properties props) {
        props.put(ProducerConfig.LINGER_MS_CONFIG, 5);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 2 * 1024 * 250);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 32 * 1024 * 1024);
        props.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "gzip");
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, true);
    }

    private void setupRetriesConfig(Properties props) {
        props.put(ProducerConfig.RETRIES_CONFIG, 3);
        props.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, 10000);
        props.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, 1000);
    }
}
