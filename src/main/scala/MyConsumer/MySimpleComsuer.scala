package MyConsumer

import java.util

import kafka.api._
import kafka.common.TopicAndPartition
import kafka.consumer.SimpleConsumer
import kafka.message.ByteBufferMessageSet

import scala.collection.immutable.Map

object MySimpleComsuer {
  def main (args: Array[String]) {
    val host = "localhost"
    val port = 9092
    val soTimeout = 1000
    val bufferSize = 1000
    val clientId = "client1"
    val consumer = new SimpleConsumer(host, port, soTimeout, bufferSize, clientId)


    val topic = "test"
    val partition = 0

    callOffsetsBefore()

    def callFetch() {
      val tp = new TopicAndPartition(topic, partition)
      val info = new PartitionFetchInfo(0, 1000 * 1000 * 2) // offset and size 2M byte message.

      val para = Map[TopicAndPartition, PartitionFetchInfo](tp -> info)

      val request = new FetchRequest(requestInfo = para)

      val response : FetchResponse  = consumer.fetch(request)
      val msgSet = response.messageSet(topic, partition)
      msgSet.foreach(msgAndOffset =>
        println(msgAndOffset.message.toString() + " " + msgAndOffset.offset))
    }

    def callOffsetsBefore(): Unit = {
      val tp = TopicAndPartition(topic, partition)
      val info = PartitionOffsetRequestInfo(kafka.api.OffsetRequest.LatestTime, 1) // time and maxNumOffsets

      val offsetRequest = new OffsetRequest(requestInfo = Map[TopicAndPartition, PartitionOffsetRequestInfo](tp -> info))

      val response = consumer.getOffsetsBefore(offsetRequest)
      val msgSet = response.partitionErrorAndOffsets
      msgSet.foreach(msgAndOffset =>
        println(msgAndOffset._1.topic + msgAndOffset._1.partition + " " + msgAndOffset._2))
    }

  }
}
