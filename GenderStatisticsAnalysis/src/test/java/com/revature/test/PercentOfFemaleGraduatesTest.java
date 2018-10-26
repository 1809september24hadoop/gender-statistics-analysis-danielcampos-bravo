package com.revature.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.apache.hadoop.mrunit.mapreduce.MapReduceDriver;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.junit.Before;
import org.junit.Test;

import com.revature.map.PercentOfFemaleGraduatesMapper;
import com.revature.reduce.PercentOfFemaleGraduatesReducer;

public class PercentOfFemaleGraduatesTest {
	
	private MapDriver<LongWritable, Text, Text, DoubleWritable>mapDriver;
	private ReduceDriver<Text, DoubleWritable, Text, DoubleWritable>reduceDriver;
	private MapReduceDriver<LongWritable, Text, Text, DoubleWritable, Text, DoubleWritable>mapReduceDriver;

	@Before
	public void setUp() {
		PercentOfFemaleGraduatesMapper mapper = new PercentOfFemaleGraduatesMapper();
		mapDriver = new MapDriver<>();
		mapDriver.setMapper(mapper);

		PercentOfFemaleGraduatesReducer reducer = new PercentOfFemaleGraduatesReducer();
		reduceDriver = new ReduceDriver<>();
		reduceDriver.setReducer(reducer);
		
		mapReduceDriver = new MapReduceDriver<>();
		mapReduceDriver.setMapper(mapper);
		mapReduceDriver.setReducer(reducer);
	}
	 
	@Test
	public void testMapper() {
		mapDriver.withInput(new LongWritable(1), new Text("\"Zimbabwe\",\"ZWE\",\"Educational attainment, at least Bachelor's or equivalent, population 25+, female (%) (cumulative)\",\"SE.TER.CUAT.BA.FE.ZS\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"2.4011\",\"\",\"\",\"\",\"\","));
		mapDriver.withOutput(new Text("\"zimbabwe educational attainment, at least bachelor's or equivalent, population 25+, female (%) (cumulative)"), new DoubleWritable(2.4011));
		mapDriver.runTest();
	}
	
	@Test
	public void testReducer() {
		List<DoubleWritable> values = new ArrayList<DoubleWritable>();
		values.add(new DoubleWritable(2.4011));
		
		reduceDriver.withInput(new Text("\"zimbabwe educational attainment, at least bachelor's or equivalent, population 25+, female (%) (cumulative)"), values);
		reduceDriver.withOutput(new Text("ZIMBABWE At least Bachelor's or Equivalent: "), new DoubleWritable(2.4));
		reduceDriver.runTest();
	}

	@Test
	public void testMapReduce() {
		mapReduceDriver.withInput(new LongWritable(1), new Text("\"Zimbabwe\",\"ZWE\",\"Educational attainment, at least Bachelor's or equivalent, population 25+, female (%) (cumulative)\",\"SE.TER.CUAT.BA.FE.ZS\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"2.4011\",\"\",\"\",\"\",\"\","));
		mapReduceDriver.addOutput(new Text("ZIMBABWE At least Bachelor's or Equivalent: "), new DoubleWritable(2.4));
		mapReduceDriver.runTest();
	}
}
