package com.shulu.bigdata.hadoop;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.mapreduce.lib.partition.HashPartitioner;


public class WordCount
{
     public static String path1 = "file:///C:\\word.txt";//读取本地windows文件系统中的数据
     public static String path2 = "file:///D:\\dir";
     public static void main(String[] args) throws Exception
     {
    	 System.setProperty("hadoop.home.dir","E:\\hadoop-2.7.4" );
    	 
         Configuration conf = new Configuration();
         FileSystem fileSystem = FileSystem.get(conf);

         if(fileSystem.exists(new Path(path2)))
         {
             fileSystem.delete(new Path(path2), true);
         }
         Job job = Job.getInstance(conf);
         job.setJarByClass(WordCount.class);

         FileInputFormat.setInputPaths(job, new Path(path1));
         job.setInputFormatClass(TextInputFormat.class);
         job.setMapperClass(MyMapper.class);
         job.setMapOutputKeyClass(Text.class);
         job.setMapOutputValueClass(LongWritable.class);

         job.setNumReduceTasks(1);
         job.setPartitionerClass(HashPartitioner.class);


         job.setReducerClass(MyReducer.class);
         job.setOutputKeyClass(Text.class);
         job.setOutputValueClass(LongWritable.class);
         job.setOutputFormatClass(TextOutputFormat.class);
         FileOutputFormat.setOutputPath(job, new Path(path2));
         job.waitForCompletion(true);
     }    
     public  static  class MyMapper extends Mapper<LongWritable, Text, Text, LongWritable>
     {
             protected void map(LongWritable k1, Text v1,Context context)throws IOException, InterruptedException
            {
                 String[] splited = v1.toString().split("\t");
                 for (String string : splited)
                {
                       context.write(new Text(string),new LongWritable(1L));
                }
            }     
     }
     public  static class MyReducer extends Reducer<Text, LongWritable, Text, LongWritable>
     {
        protected void reduce(Text k2, Iterable<LongWritable> v2s,Context context)throws IOException, InterruptedException
        {
                 long sum = 0L;
                 for (LongWritable v2 : v2s)
                {
                    sum += v2.get();
                }
                context.write(k2,new LongWritable(sum));
        }
     }
}

