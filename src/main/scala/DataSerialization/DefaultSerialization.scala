package DataSerialization

import kafka.serializer.{DefaultDecoder, DefaultEncoder}

/**
 * Default encoder and decoder just get/set the input, it is useless.
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
