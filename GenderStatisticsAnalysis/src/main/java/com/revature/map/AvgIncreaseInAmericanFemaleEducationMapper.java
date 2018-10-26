package com.revature.map;

import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class AvgIncreaseInAmericanFemaleEducationMapper extends Mapper<LongWritable, Text, Text, MapWritable> {

	/*
	 * The map method selects records that contain the statement "educational attainment",
	 * "united states", and "female". It writes an intermediate output using a Text as a key
	 * and a MapWritable<IntWritable, DoubleWritable> as a value.
	 */
	@Override
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		
		String record = value.toString().toLowerCase();
		String[] recordColumn = record.split("\",\"");
		String americanEducation = recordColumn[0] + " " + recordColumn[2];
		
		boolean isAmericanEducation = americanEducation.contains("educational attainment") && americanEducation.contains("united states");
		boolean isFemale = americanEducation.contains("female");
		
		if (isAmericanEducation && isFemale) {
			MapWritable annualGradPerc = new MapWritable();
			int year = 1960;

			for (int i = 4; i < recordColumn.length; i++) {
				try { 
					double stringDouble = Double.parseDouble(recordColumn[i]);
					annualGradPerc.put(new IntWritable(year), new DoubleWritable(stringDouble));
				}
				catch (NumberFormatException e) {
					if (recordColumn[0].equals("")) {
						annualGradPerc.put(new IntWritable(year), new DoubleWritable(-1.0));
					}
				}
				year++;
			}
			context.write(new Text(americanEducation), annualGradPerc);
		}
	}
}
