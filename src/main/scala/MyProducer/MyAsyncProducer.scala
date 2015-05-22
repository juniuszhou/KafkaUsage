package MyProducer

import kafka.javaapi.producer.Producer
import kafka.producer.{KeyedMessage, ProducerConfig}

/**
 * Created by juzhou on 5/22/2015.
 */


object MyAsyncProducer {
  def main(args: Array[String]) = {

    val props = new java.util.Properties()
    props.put("metadata.broker.list", "localhost:9092")

    // we configure producer as async.
    props.put("config.producerType","async")
    val config = new ProducerConfig(props)

    val prod = new Producer[String, String](config)

    val topic = "test"

    val key = "127.0.0.1"
    val msg = " I am the message"
    val data = new KeyedMessage[String, String](topic, key, msg)

    // here the data send async because we set type as async.
    // the interface of send is unified, no interface named asyncsend but private method.
    prod.send(data)

    // the message will be queued in a buffer, then other thread ProduceSendThread will send
    // out message until queue.time or batch.size reached.
    prod.close
  }
}
