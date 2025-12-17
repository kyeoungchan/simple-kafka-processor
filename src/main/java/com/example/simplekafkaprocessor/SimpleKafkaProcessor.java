package com.example.simplekafkaprocessor;

import java.util.Properties;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;

public class SimpleKafkaProcessor {

    private static final String APPLICATION_NAME = "processor-application";
    private static final String BOOTSTRAP_SERVERS = "my-kafka:9092";
    private static final String STREAM_LOG = "stream_log";
    private static final String STREAM_LOG_FILTER = "stream_log_filter";

    public static void main(String[] args) {

        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, APPLICATION_NAME);
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        // 프로세서 API를 사용한 토폴로지를 구성하기 위해 사용된다.
        Topology topology = new Topology();

        // stream_log 토픽을 소스 프로세서로 가져온다.
        topology.addSource("Source", STREAM_LOG)

                // 스트림 프로세서를 사용하는데, 사용자가 정의한 프로세서 인스턴스를 2번째 파라미터로 넣고,
                // 3번째 파라미터는 부모 노드를 입력하는 곳이다.
                .addProcessor("Process",
                        () -> new FilterProcessor(),
                        "Source")

                // 3번째 파라미터는 부모 노드를 입력하는 곳이다.
                .addSink("Sink",
                        STREAM_LOG_FILTER,
                        "Process");

        KafkaStreams streaming = new KafkaStreams(topology, props);
        streaming.start();
    }
}
