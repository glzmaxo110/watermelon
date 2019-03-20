package com.xx.watermelon

import com.xx.watermelon.CdnStatics.IPPattern
import org.apache.spark.rdd.RDD
import org.apache.spark.storage.StorageLevel
import org.apache.spark.{SparkConf, SparkContext}


/**
  * cdn数据处理demo
  */
object CdnDemo {

    // 数据结构：
    // IP 命中率 响应时间 请求时间 请求方法 请求URL    请求协议 状态吗 响应大小 referer 用户代理
    // 100.79.121.48 HIT 33 [15/Feb/2017:00:00:46 +0800] "GET http://cdn.v.abc.com.cn/videojs/video.js HTTP/1.1" 200 174055 "http://www.abc.com.cn/" "Mozilla/4.0+(compatible;+MSIE+6.0;+Windows+NT+5.1;+Trident/4.0;)"

  def main(args: Array[String]): Unit = {
    // 需求1：计算独立IP数
    // 统计所有IP，并去重；
    // 配置Spark应用名称
    val conf = new SparkConf().setAppName("cdnDemo").setMaster("local[*]");
    // 初始化SparkContext
    val sc = new SparkContext(conf);
    // 构建RDD
    val cdnRDD = sc.textFile("hdfs://192.168.1.102:9000/user/xuan/spark/cdn.txt").persist(StorageLevel.DISK_ONLY);

    // 统计独立IP访问量前10位
    ipStatics(cdnRDD)

    sc.stop()

  }

  // 统计独立IP访问量前10位
  def ipStatics(data: RDD[String]): Unit = {

    //1.统计独立IP数
    //val ipNums = data.map(x => (IPPattern.findFirstIn(x).get, 1)).reduceByKey(_ + _).sortBy(_._2, false)
    val ipNums = data.map(x => (x.split(" ")(0), 1)).reduceByKey(_ + _).sortBy(_._2, false)
    println(data.map(x => (x.split(" ")(0), 1)))

    //输出IP访问数前量前10位
    ipNums.take(10).foreach(println)
    println("独立IP数：" + ipNums.count())

  }








}
