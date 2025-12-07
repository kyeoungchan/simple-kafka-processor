# 💻 실행 가이드
먼저 해당 커맨드를 입력한다.
```shell
$ bin/kafka-topics.sh --create \
--bootstrap-server my-kafka:9092 \
--partitions 3 \
--topic stream_log

WARNING: Due to limitations in metric names, topics with a period ('.') or underscore ('_') could collide. To avoid issues it is best to use either, but not both.
Created topic stream_log.
```

<br>

`SimpleKafkaProcessor`를 실행한다.

<br>

다음의 커맨드를 통해 stream_log에 데이터를 프로듀스한다.
```shell
$ bin/kafka-console-producer.sh --bootstrap-server my-kafka:9092 \
--topic stream_log
>kyeongchan
>charles
>hello
>streams
```

<br>

다음의 커맨드를 통해 stream_log_filter 토픽에서 데이터를 확인한다.
```shell
$ bin/kafka-console-consumer.sh --bootstrap-server my-kafka:9092 \
--topic stream_log_filter --from-beginning
kyeongchan
charles
# hello가 없다.
streams
^CProcessed a total of 3 messages
```