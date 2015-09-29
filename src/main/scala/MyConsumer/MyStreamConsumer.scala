package MyConsumer

import java.util.Properties

import kafka.consumer.{ConsumerConfig, Consumer}

object MyStreamConsumer {
  def main (args: Array[String]) {
    val props =  new Properties
    props.put("metadata.broker.list", "localhost:9092")

    //each group will get all message from a topic.
    props.put("group.id", "BeiJingGroup")
    props.put("zookeeper.connect", "127.0.0.1")
    val consumerConfig = new ConsumerConfig(props)

    val connector = Consumer.create(consumerConfig)
    //Consumer.createJavaConsumerConnector()

    // create stream
    val str = connector.createMessageStreams(Map[String, Int]("test" -> 4))

    // get stream for test
    val testStr = str.get("test").get

    val iterator = testStr.head.iterator()

    while (iterator.hasNext()){
      println(iterator.next().message().toString)
    }


  }
}
