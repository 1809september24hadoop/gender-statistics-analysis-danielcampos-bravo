package com.revature.map;

import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class PercentOfFemaleGraduatesMapper extends Mapper<LongWritable, Text, Text, DoubleWritable>  {

	@Override
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		
		String record = value.toString().toLowerCase();
		String[] recordColumn = record.split("\",\"");
		
		boolean isEducationalAttainment = recordColumn[2].contains("educational attainment");
		boolean isFemale = recordColumn[2].contains("female");
		
		if (isEducationalAttainment && isFemale) {
			Text countryEducation = new Text(recordColumn[0] + " " + recordColumn[2]);
			int numOfYears = 0;
			double avgGraduation = 0;

			for (int i = 4; i < recordColumn.length; i++) {
				try {
					avgGraduation += Double.parseDouble(recordColumn[i]);
					numOfYears++;
				}
				catch (NumberFormatException e) {}
			}

			if (numOfYears != 0) {
				avgGraduation = avgGraduation / numOfYears;
				context.write(countryEducation, new DoubleWritable(avgGraduation));
			}
		}
	}
}
