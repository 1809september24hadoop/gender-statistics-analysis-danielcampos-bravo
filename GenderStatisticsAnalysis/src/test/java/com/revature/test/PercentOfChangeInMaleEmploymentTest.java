package com.revature.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.HashMap;
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

import com.revature.map.PercentOfChangeInMaleEmploymentMapper;
import com.revature.reduce.PercentOfChangeInMaleEmploymentReducer;

public class PercentOfChangeInMaleEmploymentTest {

	private MapDriver<LongWritable, Text, Text, MapWritable>mapDriver;
	private ReduceDriver<Text, MapWritable, Text, DoubleWritable>reduceDriver;
	private MapReduceDriver<LongWritable, Text, Text, MapWritable, Text, DoubleWritable>mapReduceDriver;
	private Map<Text, MapWritable>mapperMockMap;
	
	private static final Text MOCK_KEY = new Text("\"Zimbabwe\",\"ZWE\",\"Employment to population ratio, 15+, male (%) (modeled ILO estimate)\",\"SL.EMP.TOTL.SP.MA.ZS\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"74.3040008544922\",\"74.4280014038086\",\"74.4789962768555\",\"75.4830017089844\",\"75.0410003662109\",\"74.4759979248047\",\"73.3160018920898\",\"73.1859970092773\",\"73.1429977416992\",\"76.8079986572266\",\"77.6699981689453\",\"81.068000793457\",\"84.1679992675781\",\"86.6729965209961\",\"85.3789978027344\",\"83.9309997558594\",\"84.1480026245117\",\"84.4789962768555\",\"84.6709976196289\",\"81.8899993896484\",\"84.2190017700195\",\"83.9380035400391\",\"83.6549987792969\",\"82.7959976196289\",\"82.6269989013672\",\"82.5979995727539\",\n" + 
			"");
	
	//new Text("\"zimbabwe employment to population ratio, 15+, female (%) (modeled ILO estimate)");
	
	@Before
	public void setUp() {
		PercentOfChangeInMaleEmploymentMapper mapper = new PercentOfChangeInMaleEmploymentMapper();
		mapDriver = new MapDriver<>();
		mapDriver.setMapper(mapper);

		PercentOfChangeInMaleEmploymentReducer reducer = new PercentOfChangeInMaleEmploymentReducer();
		reduceDriver = new ReduceDriver<>();
		reduceDriver.setReducer(reducer);
		
		mapReduceDriver = new MapReduceDriver<>();
		mapReduceDriver.setMapper(mapper);
		mapReduceDriver.setReducer(reducer);
		
		mapperMockMap = new HashMap<>();
		MapWritable writable = new MapWritable();
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
}
