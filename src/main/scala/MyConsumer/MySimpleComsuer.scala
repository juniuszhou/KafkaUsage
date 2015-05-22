package MyConsumer

import kafka.api._
import kafka.common.TopicAndPartition
import kafka.consumer.SimpleConsumer
import kafka.message.ByteBufferMessageSet

import scala.collection.immutable.Map

/**
 * Created by junius on 15-5-18.
 */


object MySimpleComsuer {
  def main (args: Array[String]) {
    val host = "localhost"
    val port = 9092
    val soTimeout = 1000
    val bufferSize = 1000
    val clientId = "client1"
    val consumer = new SimpleConsumer(host, port, soTimeout, bufferSize, clientId)


    val topic = "test"
    val partition = 1

    def callFetch() {
      val tp = TopicAndPartition(topic, partition)
      val info = PartitionFetchInfo(0, 100) // offset and size
      val request = new FetchRequest(requestInfo = Map[TopicAndPartition, PartitionFetchInfo](tp, info))
      val response : FetchResponse  = consumer.fetch(request)
      val msgSet = response.messageSet(topic, partition)
      msgSet.foreach(msgAndOffset =>
        println(msgAndOffset.message.toString() + " " + msgAndOffset.offset))
    }

    def callOffsetsBefore(): Unit = {
      val tp = TopicAndPartition(topic, partition)
      val info = PartitionOffsetRequestInfo(0, 100) // time and maxNumOffsets

      val offetRequest = new OffsetRequest(requestInfo = Map[TopicAndPartition, PartitionOffsetRequestInfo](tp, info))

      val response = consumer.getOffsetsBefore(offetRequest)
      val msgSet = response.partitionErrorAndOffsets
      msgSet.foreach(msgAndOffset =>
        println(msgAndOffset._1.topic + msgAndOffset._1.partition + " " + msgAndOffset._2))
    }



  }
}
