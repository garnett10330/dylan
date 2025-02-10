package com.momo.dylantest.consumer;

import com.momo.dylantest.properties.KafkaConfigProperties;
import com.momo.dylantest.util.LogUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaStream {

    private final KafkaConfigProperties kafkaConfigProperties;

    /**
     * 定義 Kafka Streams 的處理邏輯：
     * - 從 streamInput topic 讀取字串訊息
     * - 將訊息轉成大寫（可在此處加入其他業務邏輯）
     * - 寫入 streamOutput topic
     */
    @Bean
    public KStream<String, String> kStream(StreamsBuilder streamsBuilder) {
        KStream<String, String> stream = streamsBuilder.stream(
                kafkaConfigProperties.getTopics().getStreamInput(),
                Consumed.with(Serdes.String(), Serdes.String())
        );

        stream.mapValues(value -> {
            log.info(LogUtil.info(LogUtil.GATE_OTHER,"Stream processing - original value: {}", value));
            return value.toUpperCase();
        }).to(kafkaConfigProperties.getTopics().getStreamOutput(), Produced.with(Serdes.String(), Serdes.String()));

        return stream;
    }
}
