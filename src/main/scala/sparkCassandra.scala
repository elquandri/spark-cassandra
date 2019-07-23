
import java.io.BufferedOutputStream

import com.datastax.spark.connector._
import com.datastax.spark.connector.cql.CassandraConnector
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.spark.rdd.RDD
import resultcheck.generate


object sparkCassandra extends App with configuration {

                   initifile()
val rddCreated =   createRdd()
                   writeCassandra(rddCreated)
                   checkresult()



  def createRdd(): RDD[(String,Int)] ={

    return spark.sparkContext.parallelize(Seq(("Kafka",13),("Spark",62),("Cassandra",500),("Mesos",43),("HDFS",285),("CEPH",58)))

  }

  def writeCassandra(rdd: RDD[(String,Int)]){

    CassandraConnector(conf).withSessionDo { session =>
      session.execute("DROP KEYSPACE IF EXISTS test ")
      session.execute("CREATE KEYSPACE test WITH REPLICATION = {'class': 'SimpleStrategy', 'replication_factor': 1 }")
      session.execute("CREATE TABLE test.words (word text PRIMARY KEY, count int)")
    }
    rdd.saveToCassandra("test", "words", SomeColumns("word", "count"))

  }

  def checkresult(): Unit =
  {
    var c=""
    val rddRead = spark.sparkContext.cassandraTable("test","words")



    if (rddRead.count()== 6)
    {
      c = generate("passed").toString
    }
    else if(rddRead.count()!= 6) {
      c = generate("failed").toString
    }
    else {
      c = generate("bloqued").toString
    }

    saveResultFile(c)


  }


  def initifile(): Unit =
  {
    val c =generate("failed").toString
    saveResultFile(c)
  }

  def saveResultFile(content: String) = {
    val conf = new Configuration()
    conf.set("fs.defaultFS", pathHdfs)

    val date = java.time.LocalDate.now.toString
    val filePath = pathResult + resultPrefix + "_" + date + ".json"

    val fs = FileSystem.get(conf)
    val output = fs.create(new Path(filePath), true)

    val writer = new BufferedOutputStream(output)

    try {
      writer.write(content.getBytes("UTF-8"))
    }
    finally {
      writer.close()
    }
  }

}
