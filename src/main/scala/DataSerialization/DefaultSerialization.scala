package DataSerialization

import kafka.serializer.{DefaultDecoder, DefaultEncoder}

/**
 * Created by juzhou on 5/22/2015.
 */
object DefaultSerialization {
  def main(args: Array[String]) {
    val encoder = new DefaultEncoder
    val str = "hello world"
    val bytes = encoder.toBytes(str.getBytes)

    println(bytes)

    val decoder = new DefaultDecoder
    val str2 = new String(decoder.fromBytes(bytes))
    println(str2)
  }
}
