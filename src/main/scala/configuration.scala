
import com.typesafe.config.{Config, ConfigFactory}
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

trait configuration {


  val config:Config=ConfigFactory.load("Cassandra.conf")
  val connection_host =config.getString("connection_host")
  val cassandra_host =config.getString("cassandra_host")
  val appName=config.getString("appName")
  val master =config.getString("master")
  val pathResult =config.getString("pathResult")
  val resultPrefix = config.getString("resultPrefix")
  val pathHdfs = config.getString("pathHdfs")


  val spark = SparkSession.builder
    .appName(appName)
    .config(connection_host,cassandra_host)
    .master(master)
    .getOrCreate()


  // spark Configuration
  val conf = new SparkConf(true)
    .set(connection_host,cassandra_host)
    .setAppName(appName)
    .setMaster(master)

}
