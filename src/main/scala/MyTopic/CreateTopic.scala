package MyTopic

import kafka.admin.TopicCommand
import kafka.admin.TopicCommand.TopicCommandOptions
import kafka.producer.ProducerConfig
import org.I0Itec.zkclient.ZkClient

import scala.concurrent.{ExecutionContext, Future}
import ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

/**
 * all create delete and list commands are console tool. no return value.
 * the output is shown in console.
 */
object CreateTopic {
  def main(args: Array[String]) {
    def create(topicName: String): Future[Unit] = Future {
      val zkClient = new ZkClient("localhost:2181")
      val topicOps = new TopicCommandOptions(Array[String](
        "--create",
        "--topic", topicName,
        "--partitions", "20",
        "--replication-factor", "1"))
      TopicCommand.createTopic(zkClient, topicOps)
    }

    def delete(topicName: String): Future[Unit] = Future {
      val zkClient = new ZkClient("localhost:2181")
      val topicOps = new TopicCommandOptions(Array[String](
        "--delete",
        "--topic", topicName,
        "--partitions", "20",
        "--replication-factor", "1"))
      TopicCommand.deleteTopic(zkClient, topicOps)
    }

    def list : Future[Unit] = Future {
      val zkClient = new ZkClient("localhost:2181")
      val topicOps = new TopicCommandOptions(Array[String]("--list"))
      TopicCommand.listTopics(zkClient, topicOps)
    }

    def modify(topicName: String): Future[Unit] = Future {
      val zkClient = new ZkClient("localhost:2181")
      val topicOps = new TopicCommandOptions(Array[String](
        "--alter",
        "--topic", topicName,
        "--partitions", "20",
        "--replication-factor", "1"))
      TopicCommand.deleteTopic(zkClient, topicOps)
    }

    val res = create("test")
    println("start")

    // if not set timeout, then future will no response.
    res onComplete {
      case Success(_) => println("topic created")
      case Failure(e) => e.printStackTrace()
    }

    /*
    res onSuccess {
      case _ => println("topic created")
    }

    res onFailure {
      case _ => println("failure")
    }
    */

    Thread.sleep(100000)
    println("over")
  }
}
