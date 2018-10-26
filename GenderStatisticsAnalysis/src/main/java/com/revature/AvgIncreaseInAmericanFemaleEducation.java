package com.revature;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import com.revature.map.AvgIncreaseInAmericanFemaleEducationMapper;
import com.revature.reduce.AvgIncreaseInAmericanFemaleEducationReducer;

public class AvgIncreaseInAmericanFemaleEducation {

	public static void main(String[] args) throws Exception {
		if (args.length != 2) {
			System.out.printf("Usage: WordCount <input dir> <output  ");
			System.exit(-1);
		}
		
		Job job = new Job();
		
		job.setJarByClass(AvgIncreaseInAmericanFemaleEducation.class);
		job.setJobName("Avg Increase In American Female Education");
		
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		job.setMapperClass(AvgIncreaseInAmericanFemaleEducationMapper.class);
		job.setReducerClass(AvgIncreaseInAmericanFemaleEducationReducer.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(MapWritable.class);
		
		boolean success = job.waitForCompletion(true);
		System.exit(success ? 0 : 1);
	}

}
