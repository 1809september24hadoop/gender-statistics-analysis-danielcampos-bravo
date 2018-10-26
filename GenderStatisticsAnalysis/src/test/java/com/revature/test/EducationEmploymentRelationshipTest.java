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

import com.revature.map.EducationEmploymentRelationshipMapper;
import com.revature.reduce.EducationEmploymentRelationshipReducer;

public class EducationEmploymentRelationshipTest {

	private MapDriver<LongWritable, Text, Text, MapWritable>mapDriver;
	private ReduceDriver<Text, MapWritable, Text, DoubleWritable>reduceDriver;
	private MapReduceDriver<LongWritable, Text, Text, MapWritable, Text, DoubleWritable>mapReduceDriver;
	private Map<Text, MapWritable>mapperMockMap;
	private MapWritable writable;
	private static final Text MOCK_KEY = new Text("\"west bank and gaza labor force with intermediate education, male (% of male working-age population with intermediate education)");
	
	@Before
	public void setUp() {
		EducationEmploymentRelationshipMapper mapper = new EducationEmploymentRelationshipMapper();
		mapDriver = new MapDriver<>();
		mapDriver.setMapper(mapper);

		EducationEmploymentRelationshipReducer reducer = new EducationEmploymentRelationshipReducer();
		reduceDriver = new ReduceDriver<>();
		reduceDriver.setReducer(reducer);
		
		mapReduceDriver = new MapReduceDriver<>();
		mapReduceDriver.setMapper(mapper);
		mapReduceDriver.setReducer(reducer);
	
		mapperMockMap = new HashMap<>();
		writable = new MapWritable();
		writable.put(new IntWritable(2011), new DoubleWritable(61.4799995422363));
		writable.put(new IntWritable(2012), new DoubleWritable(62.7799987792969));
		writable.put(new IntWritable(2013), new DoubleWritable(63.6500015258789));
		
		mapperMockMap.put(MOCK_KEY, writable);
	}
	
	@Test
	public void testMapper() {
		mapDriver.withInput(new LongWritable(1), new Text("\"West Bank and Gaza\",\"PSE\",\"Labor force with intermediate education, male (% of male working-age population with intermediate education)\",\"SL.TLF.INTM.MA.ZS\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"61.4799995422363\",\"62.7799987792969\",\"63.6500015258789\",\"\",\"\",\"\",\n" + 
				""));
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
		reduceDriver.withOutput(new Text("WEST BANK AND GAZA Employed with Intermediate Education (Male): "), new DoubleWritable(3.53));
		reduceDriver.runTest();	
	}
	
	@Test
	public void testMapReducer() {
		mapReduceDriver.withInput(new LongWritable(1), new Text("\"West Bank and Gaza\",\"PSE\",\"Labor force with intermediate education, male (% of male working-age population with intermediate education)\",\"SL.TLF.INTM.MA.ZS\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"61.4799995422363\",\"62.7799987792969\",\"63.6500015258789\",\"\",\"\",\"\",\n" + 
				""));
		mapReduceDriver.addOutput(new Text("WEST BANK AND GAZA Employed with Intermediate Education (Male): "), new DoubleWritable(3.53));
		mapReduceDriver.runTest();	
	}
}
