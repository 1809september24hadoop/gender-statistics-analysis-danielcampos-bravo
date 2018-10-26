package com.revature.reduce;

import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import com.revature.format.FormatOutput;

public class PercentOfFemaleGraduatesReducer extends Reducer<Text, DoubleWritable, Text, DoubleWritable>  {

	@Override
	public void reduce(Text key, Iterable<DoubleWritable> values, Context context) throws IOException, InterruptedException {
		
		for (DoubleWritable avgGraduation : values) {
			Text parsedText = key;
			parsedText = FormatOutput.formatEducation(parsedText);
			
			boolean isLessThan30 = avgGraduation.get() < 30;
			boolean erroneousData = parsedText.toString().contains("Erroneous Data");
			
			if (isLessThan30 && !erroneousData) {
				
				DoubleWritable roundedValue = FormatOutput.formatDoubleWritable(avgGraduation);
				context.write(parsedText, roundedValue);
			}
		}
	}
}
