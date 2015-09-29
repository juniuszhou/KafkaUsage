package ExpediaKafka;

import kafka.javaapi.TopicMetadataRequest;
import kafka.javaapi.consumer.SimpleConsumer;

import java.util.ArrayList;
import java.util.List;

public class MetaDataDump {
    public static void main(String[] args) {
        kafka.javaapi.consumer.SimpleConsumer consumer  =
                new SimpleConsumer("10.0.17.241",
                9092,
                10000,
                64 * 1024, "test");
        List<String> topics2 = new ArrayList<String>();
        TopicMetadataRequest req = new TopicMetadataRequest(topics2);
        kafka.javaapi.TopicMetadataResponse resp = consumer.send(req);
        List<kafka.javaapi.TopicMetadata> data3 =  resp.topicsMetadata();
        for (kafka.javaapi.TopicMetadata item : data3) {
            System.out.println("Topic: " + item.topic());
            for (kafka.javaapi.PartitionMetadata part: item.partitionsMetadata() ) {
                String replicas = "";
                String isr = "";
                for (kafka.cluster.Broker replica: part.replicas() ) {
                    replicas += " " + replica.host();
                }
                for (kafka.cluster.Broker replica: part.isr() ) {
                    isr += " " + replica.host();
                }
                System.out.println( "    Partition: " +   part.partitionId()  + ": Leader: " + part.leader().host() + " Replicas:[" + replicas + "] ISR:[" + isr + "]");
            }
        }
    }
}
