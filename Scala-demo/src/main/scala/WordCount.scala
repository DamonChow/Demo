import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by Damon on 2017/5/4.
  */
object WordCount {
  def main(args : Array[String]): Unit = {
    val inputPath = "D:\\soft\\spark-2.1.0-bin-hadoop2.7\\README.md"
    val conf = new SparkConf().setMaster("local").setAppName("WordCount")
    val sc = new SparkContext(conf)

    val rowRdd = sc.textFile(inputPath)
    println(rowRdd.take(1).toString)
    val resultRdd = rowRdd.flatMap(line => line.split("\\s+"))
      .map(word => (word, 1)).reduceByKey(_ + _)
    println(resultRdd.take(2).toString)
    for (data <- resultRdd) {
      println(data)
    }
    sc.stop()
  }
}
