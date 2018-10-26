package com.revature.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.apache.hadoop.mrunit.mapreduce.MapReduceDriver;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.apache.hadoop.mrunit.types.Pair;
import org.junit.Before;
import org.junit.Test;

import com.revature.map.AvgIncreaseInAmericanFemaleEducationMapper;
import com.revature.reduce.AvgIncreaseInAmericanFemaleEducationReducer;

public class AvgIncreaseInAmericanFemaleEducationTest {
	
	private MapDriver<LongWritable, Text, Text, MapWritable>mapDriver;
	private ReduceDriver<Text, MapWritable, Text, DoubleWritable>reduceDriver;
	private MapReduceDriver<LongWritable, Text, Text, MapWritable, Text, DoubleWritable>mapReduceDriver;
	private Map<Text, MapWritable> mapperMockMap;
	private MapWritable writable;
	
	private static final Text MOCK_KEY = new Text("\"united states educational attainment, at least bachelor's or equivalent, population 25+, female (%) (cumulative)\"");
	
	@Before
	public void setUp() {
		AvgIncreaseInAmericanFemaleEducationMapper mapper = new AvgIncreaseInAmericanFemaleEducationMapper();
		mapDriver = new MapDriver<>();
		mapDriver.setMapper(mapper);

		AvgIncreaseInAmericanFemaleEducationReducer reducer = new AvgIncreaseInAmericanFemaleEducationReducer();
		reduceDriver = new ReduceDriver<>();
		reduceDriver.setReducer(reducer);
		
		mapReduceDriver = new MapReduceDriver<>();
		mapReduceDriver.setMapper(mapper);
		mapReduceDriver.setReducer(reducer);
		
		mapperMockMap = new HashMap<>();
		writable = new MapWritable();
		writable.put(new IntWritable(2013), new DoubleWritable(31.39076));
		writable.put(new IntWritable(2014), new DoubleWritable(32.00147));
		writable.put(new IntWritable(2015), new DoubleWritable(32.67396));
		mapperMockMap.put(MOCK_KEY, writable);
	}
	
	@Test
	public void testMapper() {
		mapDriver.withInput(new LongWritable(1), new Text("\"United States\",\"USA\",\"Educational attainment, at least Bachelor's or equivalent, population 25+, female (%) (cumulative)\",\"SE.TER.CUAT.BA.FE.ZS\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"31.39076\",\"32.00147\",\"32.67396\",\"\","));
		try {
			for(Pair<Text, MapWritable> output: mapDriver.run()) {
				for(Writable key: output.getSecond().keySet()) {
					assertEquals(mapperMockMap.get(MOCK_KEY).get(key), output.getSecond().get(key));
				}
			}
		} catch (IOException e) {
			return;
		}
	}
	
	@Test
	public void testReducer() {
		List<MapWritable> values = new ArrayList<>();
		values.add(writable);
		reduceDriver.withInput(MOCK_KEY, values);
		reduceDriver.withOutput(new Text("UNITED STATES At least Bachelor's or Equivalent: "), new DoubleWritable(32.02));
		reduceDriver.runTest();	
	}
	
	@Test
	public void testMapReducer() {
		mapReduceDriver.withInput(new LongWritable(1), new Text("\"United States\",\"USA\",\"Educational attainment, at least Bachelor's or equivalent, population 25+, female (%) (cumulative)\",\"SE.TER.CUAT.BA.FE.ZS\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"31.39076\",\"32.00147\",\"32.67396\",\"\","));
		mapReduceDriver.addOutput(new Text("UNITED STATES At least Bachelor's or Equivalent: "), new DoubleWritable(32.02));
		mapReduceDriver.runTest();	
	}
}
