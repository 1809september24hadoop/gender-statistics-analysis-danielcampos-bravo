package com.revature.reduce;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Reducer;

import com.revature.format.FormatOutput;

public class AvgIncreaseInAmericanFemaleEducationReducer extends Reducer<Text, MapWritable, Text, DoubleWritable>  {

	/*
	 * The reduce method receives the intermediates output as a Text, MapWritable<IntWritable, DoubleWritable> pair
	 * and calculates the average increase of all the records that have a records that contain the statement "educational attainment",
	 * "united states", and "female". It writes an intermediate output using a Text as a key
	 * and a MapWritable<IntWritable, DoubleWritable> as a value.
	 */
	@Override
	public void reduce(Text key, Iterable<MapWritable> values, Context context) throws IOException, InterruptedException {
		
		for (MapWritable annualGradPerc : values) {
			List<DoubleWritable> gradPercent = new ArrayList<>();
			double avgIncrease = 0;
			int numOfYears = 0;
			int year = 2000;
			
			for (Writable keyValue : annualGradPerc.keySet()) {
				
				if (((IntWritable) keyValue).get() >= year && ((DoubleWritable) annualGradPerc.get(keyValue)).get() > -1.0) {
					gradPercent.add((DoubleWritable) annualGradPerc.get(keyValue));
					numOfYears++;
				}
				year++;
			}
			
			for (DoubleWritable percent : gradPercent) {
				avgIncrease += percent.get();
			}
			
			avgIncrease /= numOfYears;
			key = FormatOutput.formatEducation(key);
			if (!key.toString().contains("Erroneous Data")) {
				DoubleWritable roundedValue = FormatOutput.formatDoubleWritable(new DoubleWritable(avgIncrease));
				context.write(key, roundedValue);
			}
		}
	}
}
