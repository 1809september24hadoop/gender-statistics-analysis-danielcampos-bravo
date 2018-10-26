package com.revature;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import com.revature.map.PercentOfChangeInMaleEmploymentMapper;
import com.revature.reduce.PercentOfChangeInMaleEmploymentReducer;

public class PercentOfChangeInMaleEmployment {

	public static void main(String[] args) throws Exception {
		if (args.length != 2) {
			System.out.printf("Usage: WordCount <input dir> <output  ");
			System.exit(-1);
		}
		
		Job job = new Job();
		
		job.setJarByClass(PercentOfChangeInMaleEmployment.class);
		job.setJobName("Percent Of Change In Male Employment");
		
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		job.setMapperClass(PercentOfChangeInMaleEmploymentMapper.class);
		job.setReducerClass(PercentOfChangeInMaleEmploymentReducer.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(MapWritable.class);
		
		boolean success = job.waitForCompletion(true);
		System.exit(success ? 0 : 1);
	}
}
