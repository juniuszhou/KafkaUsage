package ExpediaKafka

import kafka.admin.AdminUtils
import org.I0Itec.zkclient.ZkClient

object CreateTopic {
  def main (args: Array[String]) {

    // zk client must init with the root path. otherwise, it will get from root.
    // normally start from /brokers.
    val zkClient = new ZkClient(s"zk1.us-east-1.test.expedia.com:2181/kafka/ewep")

    //add topic
    val result = AdminUtils.createTopic(zkClient, "uis2", 1, 1 )

    //delete topic
    val res2 = AdminUtils.deleteTopic(zkClient, "uis2")

    // update topic.
    // AdminUtils.createOrUpdateTopicPartitionAssignmentPathInZK()
    println("end.")
  }
}
