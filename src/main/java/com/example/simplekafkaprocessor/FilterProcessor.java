package com.example.simplekafkaprocessor;


import org.apache.kafka.streams.processor.api.Processor;
import org.apache.kafka.streams.processor.api.ProcessorContext;
import org.apache.kafka.streams.processor.api.Record;

public class FilterProcessor implements Processor<String, String, String, String> {

    // 프로세서에 대한 정보를 담고 있다.
    // 현재 스트림 처리 중인 토폴로지의 토픽 정보, 애플리케이션 아이디를 조회할 수 있다.
    private ProcessorContext<String, String> context;

    // 스트림 프로세서의 생성자
    @Override
    public void init(org.apache.kafka.streams.processor.api.ProcessorContext<String, String> context) {
        this.context = context;
    }

    // 실질적인 프로세싱 로직이 들어가는 부분
    @Override
    public void process(Record<String, String> record) {
        if (record.value().length() > 5) {
            context.forward(record);
        }
        context.commit();
    }

    // 프로세싱을 하기 위해 사용했던 리소스를 해제하는 구문을 주로 넣는다.
    // 여기서는 해제할 리소스가 없으므로 별도로 작성할 코드는 없다.
    @Override
    public void close() {
    }
}
