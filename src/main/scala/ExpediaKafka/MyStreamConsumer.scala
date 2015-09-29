package ExpediaKafka

import java.util.Properties

import kafka.consumer.{ConsumerConfig, Consumer}

object MyStreamConsumer {
  def main (args: Array[String]) {
    val props =  new Properties
    props.put("metadata.broker.list", "10.0.17.241:9092")

    //each group will get all message from a topic.
    props.put("group.id", "BeiJingGroup")
    props.put("zookeeper.connect", "zk1.us-east-1.test.expedia.com:2181/kafka/ewep")
    val consumerConfig = new ConsumerConfig(props)

    val connector = Consumer.create(consumerConfig)
    //Consumer.createJavaConsumerConnector()

    // create stream
    val str = connector.createMessageStreams(Map[String, Int]("uis" -> 4))

    // get stream for test
    val testStr = str.get("uis").get

    println(testStr.size)

    var i = 0
    while (i < 4) {

      val each = testStr(i)
      i += 1


        println("create new thread and run.")
        val thread = new Thread() {
          override def run() = {
            println("start a new stream.")
            val iterator = each.iterator()

            while (iterator.hasNext()) {
              val msg = iterator.next().message()
              println(msg)
            }
          }
        }

        thread.start()

    }

  }
}

