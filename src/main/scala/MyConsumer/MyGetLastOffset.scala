package MyConsumer

import kafka.consumer.SimpleConsumer

/**
 * Created by junius on 8/18/15.
 */
object MyGetLastOffset {
  def main (args: Array[String]) {
    val host = "localhost"
    val port = 9092
    val soTimeout = 1000
    val bufferSize = 1000 * 64
    val clientId = "client1"
    val consumer = new SimpleConsumer(host, port, soTimeout, bufferSize, clientId)


    val topic = "test"
    val partition = 0
  }
}
