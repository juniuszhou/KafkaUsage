package MyProducer

import java.util
import kafka.javaapi.producer.Producer
import kafka.producer.KeyedMessage
import kafka.producer.ProducerConfig

import scala.util.Random

object SimpleProducer {
  def main(args: Array[String]) = {

    val r = new Random()
    val props = new java.util.Properties()
    props.put("metadata.broker.list", "localhost:8888")
    props.put("serializer.class", "kafka.serializer.StringEncoder")
    //props.put("partitioner.class", "example.producer.SimplePartitioner")
    props.put("request.required.acks", "1")
    val config = new ProducerConfig(props)

    val prod = new Producer[String,String](config)
    (0 until 100).foreach(i => {
      val ip = "127.0.0.1"
      val msg = " I am " + i + "th message"
      val data = new KeyedMessage[String, String]("topic one", ip, msg)

        prod.send(data)

    })


    prod.close
  }
}
