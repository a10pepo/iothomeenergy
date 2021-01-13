package energy;


import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer011;

import java.util.Properties;

public class KafkaConsumer {

    public static void main(String[] args)
            throws Exception
    {
        String inputTopic="iotdata";
        String outputTopic="iotdataagg";
        StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
        FlinkKafkaConsumer011<String> flinkKafkaConsumer=createStringConsumerForTopic("iotdata","192.168.1.16:9092","test");
        FlinkKafkaProducer011<String> flinkKafkaProducer=createStringProducer("iotdataagg","192.168.1.16:9092");
        DataStream<String> stringInputStream = environment.addSource(flinkKafkaConsumer);
        ParameterTool params = ParameterTool.fromArgs(args);

        stringInputStream.map(new WordsCapitalizer()).addSink(flinkKafkaProducer);




    }

    public static FlinkKafkaConsumer011<String> createStringConsumerForTopic(String topic, String kafkaAddress, String kafkaGroup ) {

        Properties props = new Properties();
        props.setProperty("bootstrap.servers", kafkaAddress);
        props.setProperty("group.id",kafkaGroup);
        FlinkKafkaConsumer011<String> consumer = new FlinkKafkaConsumer011<>(topic, new SimpleStringSchema(), props);

        return consumer;

    }

    public static FlinkKafkaProducer011<String> createStringProducer(String topic, String kafkaAddress){
        return new FlinkKafkaProducer011<>(kafkaAddress,topic, new SimpleStringSchema());
    }





}
class WordsCapitalizer implements MapFunction<String, String> {
    @Override
    public String map(String s) {
        return s.toUpperCase();
    }
}