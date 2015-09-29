package ExpediaKafka

import kafka.api._
import kafka.common.TopicAndPartition
import kafka.consumer.SimpleConsumer
import scala.collection.immutable.Map

// "zk1.us-east-1.prod.expedia.com,zk2.us-east-1.prod.expedia.com,zk3.us-east-1.prod.expedia.com"
object MySimpleComsuer {
  // all brokers can return the metadata for each topic and partition
  def findLeader(brokers: List[String], topic: String, partition: Int): PartitionMetadata ={
    var res: PartitionMetadata = null
    val port = 9092
    brokers.foreach(broker => {
      try {
        // here test is client id.
        val consumer = new
            SimpleConsumer(broker, port, 10000, 64 * 1024, "test")
        //val topics = util.Collections.singletonList(topic)
        val req = new TopicMetadataRequest(Seq(topic), 0)
        val resp = consumer.send(req)

        val metaData = resp.topicsMetadata
        metaData.foreach(item => item.partitionsMetadata.foreach({
          part => {
            if (part.partitionId == partition) res = part
          }
        }))
      }
      catch {
        case e: Exception => e.printStackTrace()
      }
    })
    res
  }

  def main (args: Array[String]) {
    // brokers.
    val hosts = List("10.0.17.241", "10.0.17.157", "10.0.17.176", "10.0.17.169")

    val soTimeout = 1000
    val bufferSize = 1000
    val clientId = "client1"

    val kafkaClusterId = "ewep"
    val kafkaTopicName = "uis"
    val kafkaConsumerGroupId = "lpas-trends"

    val topic = "uis"
    val partition = 0

    val metadata = findLeader(hosts, topic, 1)
    println(metadata.leader.get.toString())
    println()

    val host = metadata.leader.get.host // leader's host
    val port = metadata.leader.get.port // leader's port


    val consumer = new SimpleConsumer(host, port, soTimeout,
      bufferSize, clientId)

    callFetch(1, 435790)

    def callFetch(partition: Int, offset: Long) {
      val tp = new TopicAndPartition(kafkaTopicName, partition)
      // each time offset will be +1 but the size is about all messages' byte size.
      val info = new PartitionFetchInfo(offset, 1572864) // offset and size

      val para = Map[TopicAndPartition, PartitionFetchInfo](tp -> info)

      val request = new FetchRequest(requestInfo = para)

      val response : FetchResponse  = consumer.fetch(request)
      val msgSet = response.messageSet(topic, partition)
      msgSet.foreach(msgAndOffset =>
        println(msgAndOffset.message.toString() + " " + msgAndOffset.offset))
    }

    def callOffsetsBefore(partition: Int): Unit = {

      val tp = TopicAndPartition(topic, partition)
      // kafka.api.OffsetRequest.EarliestTime
      val info = PartitionOffsetRequestInfo(kafka.api.OffsetRequest.LatestTime, 1) // time and maxNumOffsets

      val offsetRequest = new OffsetRequest(requestInfo = Map[TopicAndPartition, PartitionOffsetRequestInfo](tp -> info))

      val response = consumer.getOffsetsBefore(offsetRequest)


      val msgSet = response.partitionErrorAndOffsets
      msgSet.foreach(msgAndOffset => {
        println("Hello ")
        println(msgAndOffset._1.topic)
        println(msgAndOffset._1.partition)
        println(msgAndOffset._2.error) // maybe no error.
        msgAndOffset._2.offsets.foreach(println) // real offset.
      })
    }
  }
}