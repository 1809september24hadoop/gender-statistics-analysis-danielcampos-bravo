package com.revature;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import com.revature.map.PercentOfFemaleGraduatesMapper;
import com.revature.reduce.PercentOfFemaleGraduatesReducer;

public class PercentOfFemaleGraduates {

	public static void main(String[] args) throws Exception {
		if (args.length != 2) {
			System.out.printf("Usage: WordCount <input dir> <output  ");
			System.exit(-1);
		}
		
		Job job = new Job();
		
		job.setJarByClass(PercentOfFemaleGraduates.class);
		job.setJobName("Percent of Female Graduates");
		
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		job.setMapperClass(PercentOfFemaleGraduatesMapper.class);
		job.setReducerClass(PercentOfFemaleGraduatesReducer.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(DoubleWritable.class);
		
		boolean success = job.waitForCompletion(true);
		System.exit(success ? 0 : 1);
	}
}
