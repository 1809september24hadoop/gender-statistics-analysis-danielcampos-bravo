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

public class EducationEmploymentRelationshipReducer extends Reducer<Text, MapWritable, Text, DoubleWritable>  {

	@Override
	public void reduce(Text key, Iterable<MapWritable> values, Context context) throws IOException, InterruptedException {
		
		for (MapWritable annualEmploymentPerc : values) {
			List<IntWritable> validEmploymentYears = new ArrayList<>();
			int year = 2000;
			double percentOfChange;
			
			for (Writable keyValue : annualEmploymentPerc.keySet()) {
				boolean pastYear2000 = ((IntWritable) keyValue).get() >= year;
				
				if (((DoubleWritable) annualEmploymentPerc.get(keyValue)).get() > 0.0 && pastYear2000) {
					validEmploymentYears.add((IntWritable) keyValue);
				}
			}
			
			if (validEmploymentYears.size() > 0) {
				
				Writable firstYear = validEmploymentYears.get(0);
				Writable lastYear = validEmploymentYears.get(validEmploymentYears.size() - 1);
				DoubleWritable firstPercent = (DoubleWritable) annualEmploymentPerc.get(firstYear);
				DoubleWritable lastPercent = (DoubleWritable) annualEmploymentPerc.get(lastYear);
				percentOfChange = lastPercent.get() - firstPercent.get();
				percentOfChange /= firstPercent.get();
				percentOfChange *= 100;
				
				key = FormatOutput.formatEducationEmploymentCorrelation(key);
				
				if (!key.toString().contains("Erroneous Data")) {
					DoubleWritable roundedValue = FormatOutput.formatDoubleWritable(new DoubleWritable(percentOfChange));
					context.write(key, roundedValue);
				}
			}
		}
	}
}
