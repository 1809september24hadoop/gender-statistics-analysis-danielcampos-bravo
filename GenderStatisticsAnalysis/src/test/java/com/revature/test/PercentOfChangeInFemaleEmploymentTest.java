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

import com.revature.map.PercentOfChangeInFemaleEmploymentMapper;
import com.revature.reduce.PercentOfChangeInFemaleEmploymentReducer;

public class PercentOfChangeInFemaleEmploymentTest {

	private MapDriver<LongWritable, Text, Text, MapWritable>mapDriver;
	private ReduceDriver<Text, MapWritable, Text, DoubleWritable>reduceDriver;
	private MapReduceDriver<LongWritable, Text, Text, MapWritable, Text, DoubleWritable>mapReduceDriver;
	private Map<Text, MapWritable>mapperMockMap;
	private MapWritable writable;
	private static final Text MOCK_KEY = new Text("\"zimbabwe employment to population ratio, 15+, female (%) (modeled ILO estimate)");
	
	@Before
	public void setUp() {
		PercentOfChangeInFemaleEmploymentMapper mapper = new PercentOfChangeInFemaleEmploymentMapper();
		mapDriver = new MapDriver<>();
		mapDriver.setMapper(mapper);

		PercentOfChangeInFemaleEmploymentReducer reducer = new PercentOfChangeInFemaleEmploymentReducer();
		reduceDriver = new ReduceDriver<>();
		reduceDriver.setReducer(reducer);
		
		mapReduceDriver = new MapReduceDriver<>();
		mapReduceDriver.setMapper(mapper);
		mapReduceDriver.setReducer(reducer);
	
		mapperMockMap = new HashMap<>();
		
		writable = new MapWritable();
		for (int year = 1960; year < 1991; year++) 
			writable.put(new IntWritable(year), new DoubleWritable(-1.0));
		writable.put(new IntWritable(1991), new DoubleWritable(64.22299957));
		writable.put(new IntWritable(1992), new DoubleWritable(64.27400208));
		writable.put(new IntWritable(1993), new DoubleWritable(64.29399872));
		writable.put(new IntWritable(1994), new DoubleWritable(64.83599854));
		writable.put(new IntWritable(1995), new DoubleWritable(64.61399841));
		writable.put(new IntWritable(1996), new DoubleWritable(64.30699921));
		writable.put(new IntWritable(1997), new DoubleWritable(64.02500153));
		writable.put(new IntWritable(1998), new DoubleWritable(62.49399948));
		writable.put(new IntWritable(1999), new DoubleWritable(61.01499939));
		writable.put(new IntWritable(2000), new DoubleWritable(65.78299713));
		writable.put(new IntWritable(2001), new DoubleWritable(69.26599884));
		writable.put(new IntWritable(2002), new DoubleWritable(73.23899841));
		writable.put(new IntWritable(2003), new DoubleWritable(76.8710022));
		writable.put(new IntWritable(2004), new DoubleWritable(79.90699768));
		writable.put(new IntWritable(2005), new DoubleWritable(78.88200378));
		writable.put(new IntWritable(2006), new DoubleWritable(77.84899902));
		writable.put(new IntWritable(2007), new DoubleWritable(76.81500244));
		writable.put(new IntWritable(2008), new DoubleWritable(75.54199982));
		writable.put(new IntWritable(2009), new DoubleWritable(74.12400055));
		writable.put(new IntWritable(2010), new DoubleWritable(74.3010025));
		writable.put(new IntWritable(2011), new DoubleWritable(72.39600372));
		writable.put(new IntWritable(2012), new DoubleWritable(72.52999878));
		writable.put(new IntWritable(2013), new DoubleWritable(72.81700134));
		writable.put(new IntWritable(2014), new DoubleWritable(73.58799744));
		writable.put(new IntWritable(2015), new DoubleWritable(73.97899628));
		writable.put(new IntWritable(2016), new DoubleWritable(74.26300049));

		mapperMockMap.put(MOCK_KEY, writable);
	}
	
	@Test
	public void testMapper() {
		mapDriver.withInput(new LongWritable(1), new Text("\"Zimbabwe\",\"ZWE\",\"Employment to population ratio, 15+, female (%) (modeled ILO estimate)\",\"SL.EMP.TOTL.SP.FE.ZS\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"64.2229995727539\",\"64.2740020751953\",\"64.2939987182617\",\"64.8359985351563\",\"64.6139984130859\",\"64.306999206543\",\"64.0250015258789\",\"62.4939994812012\",\"61.0149993896484\",\"65.7829971313477\",\"69.265998840332\",\"73.2389984130859\",\"76.8710021972656\",\"79.9069976806641\",\"78.8820037841797\",\"77.8489990234375\",\"76.8150024414063\",\"75.5419998168945\",\"74.1240005493164\",\"74.3010025024414\",\"72.3960037231445\",\"72.5299987792969\",\"72.8170013427734\",\"73.5879974365234\",\"73.9789962768555\",\"74.2630004882813\",\n" + 
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
	
	/*
	@Test
	public void testReducer() {
		List<MapWritable> values = new ArrayList<>();
		values.add(writable);
		reduceDriver.withInput(MOCK_KEY, values);
		reduceDriver.withOutput(new Text("ZIMBABWE Ages 15+ Females (Modeled ILO Estimate): "), new DoubleWritable(13.74));
		reduceDriver.runTest();	
	}
	
	@Test
	public void testMapReducer() {
		mapReduceDriver.withInput(new LongWritable(1), new Text("\"Zimbabwe\",\"ZWE\",\"Employment to population ratio, 15+, female (%) (modeled ILO estimate)\",\"SL.EMP.TOTL.SP.FE.ZS\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"64.2229995727539\",\"64.2740020751953\",\"64.2939987182617\",\"64.8359985351563\",\"64.6139984130859\",\"64.306999206543\",\"64.0250015258789\",\"62.4939994812012\",\"61.0149993896484\",\"65.7829971313477\",\"69.265998840332\",\"73.2389984130859\",\"76.8710021972656\",\"79.9069976806641\",\"78.8820037841797\",\"77.8489990234375\",\"76.8150024414063\",\"75.5419998168945\",\"74.1240005493164\",\"74.3010025024414\",\"72.3960037231445\",\"72.5299987792969\",\"72.8170013427734\",\"73.5879974365234\",\"73.9789962768555\",\"74.2630004882813\",\n" + 
				""));
		mapReduceDriver.addOutput(new Text("ZIMBABWE Ages 15+ Females (Modeled ILO Estimate): "), new DoubleWritable(13.74));
		mapReduceDriver.runTest();	
	}
	*/
}
