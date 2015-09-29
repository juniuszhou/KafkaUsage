package MyConsumer

import kafka.api.{FetchRequest, FetchRequestBuilder}
import kafka.consumer.SimpleConsumer
import kafka.javaapi.FetchResponse

/**
 * Created by junius on 8/18/15.
 */
object RequestBuilderUsage {
  def main (args: Array[String]) {
    val topic: String = "test"
    val partitionId: Int = 0
    val builder: FetchRequestBuilder = new FetchRequestBuilder
    val fetchRequest: FetchRequest =
      builder.addFetch(topic, partitionId, 0, 64 * 1024)
        .clientId("clientId").maxWait(10000).build
    var fetchResponse: FetchResponse = null


    val host = "localhost"
    val port = 9092
    val soTimeout = 1000
    val bufferSize = 1000
    val clientId = "client1"
    val consumer = new SimpleConsumer(host, port, soTimeout, bufferSize, clientId)

    // fetchResponse = consumer.fetch(fetchRequest)

  }
}
