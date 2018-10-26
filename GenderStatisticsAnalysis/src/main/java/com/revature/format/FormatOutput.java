package com.revature.format;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;

public class FormatOutput {
	
	public static Text formatEducation(Text someText) {
		String[] stringArr = someText.toString().split("educational attainment, ");
		String countryName = stringArr[0].toUpperCase();
		countryName = countryName.substring(1);
		Text returnText = new Text("");
		
		if (stringArr[1].toString().contains("primary")) {
			returnText = new Text("Primary: ");
		}
		else if (stringArr[1].toString().contains("lower secondary")) {
			returnText = new Text("Lower Secondary: ");
		}
		else if (stringArr[1].toString().contains("upper secondary")) {
			returnText = new Text("Upper Secondary: ");
		} 
		else if (stringArr[1].toString().contains("post-secondary")) {
			returnText = new Text("Post-Secondary: ");
		}
		else if (stringArr[1].toString().contains("short-cycle tertiary")) {
			returnText = new Text("Short-Cycle Tertiary: ");
		}
		else if (stringArr[1].contains("bachelor's or equivalent")) {
			returnText = new Text("Bachelor's or Equivalent: ");
		}
		else if (stringArr[1].toString().contains("master's or equivalent")) {
			returnText = new Text("Master's or Equivalent: ");
		}
		else if (stringArr[1].toString().contains("doctoral or equivalent")) {
			returnText = new Text("Doctoral or Equivalent: ");
		}
		else  {
			returnText = new Text("Erroneous Data:" + stringArr[1].toString());
		}

		
		if (someText.toString().contains("at least")) {
			returnText = new Text("At least " + returnText.toString());
		}
		
		returnText = new Text(countryName + returnText.toString());
		return returnText;
	}
	
	public static Text formatEmployment(Text someText) {
		String[] stringArr = someText.toString().split("labor force participation rate");
		String countryName = stringArr[0].toUpperCase();
		countryName = countryName.substring(1);
		Text returnText = new Text("");
		
		if (stringArr[1].toString().contains("ages 15-24, male (%) (modeled ilo estimate)")) {
			returnText = new Text("Ages 15-24 Males (Modeled ILO Estimate): ");
		}
		else if (stringArr[1].toString().contains("ages 15-24, male (%) (national estimate)")) {
			returnText = new Text("Ages 15-24 Males (National Estimate): ");
		}
		else if (stringArr[1].toString().contains("(% of male population ages 15+) (modeled ilo estimate)")) {
			returnText = new Text("Ages 15+ Males (Modeled ILO Estimate): ");
		}
		else if (stringArr[1].toString().contains("(% of male population ages 15+) (national estimate)")) {
			returnText = new Text("Ages 15+ Males (National Estimate): ");
		}
		else if (stringArr[1].toString().contains("ages 15-24, female (%) (modeled ilo estimate)")) {
			returnText = new Text("Ages 15-24 Females (Modeled ILO Estimate): ");
		}
		else if (stringArr[1].toString().contains("ages 15-24, female (%) (national estimate)")) {
			returnText = new Text("Ages 15-24 Females (National Estimate): ");
		}
		else if (stringArr[1].toString().contains("(% of female population ages 15+) (modeled ilo estimate)")) {
			returnText = new Text("Ages 15+ Females (Modeled ILO Estimate): ");
		}
		else if (stringArr[1].toString().contains("(% of female population ages 15+) (national estimate)")) {
			returnText = new Text("Ages 15+ Females (National Estimate): ");
		}
		else {
			returnText = new Text("Erroneous Data: " + stringArr[1].toString());
		}
		
		returnText = new Text(countryName.toString() + returnText.toString());
		return returnText;
	}
	
	public static Text formatEducationEmploymentCorrelation(Text someText) {
		
		String[] stringArr = someText.toString().split("labor force with");
		String countryName = stringArr[0].toUpperCase();
		countryName = countryName.substring(1);
		Text returnText = new Text("");
		
		if (stringArr[1].toString().contains("basic education, male")) {
			returnText = new Text("Employed with Basic Education (Male): ");
		}
		else if (stringArr[1].toString().contains("intermediate education, male")) {
			returnText = new Text("Employed with Intermediate Education (Male): ");
		}
		else if (stringArr[1].toString().contains("advanced education, male")) {
			returnText = new Text("Employed with Advanced Education (Male): ");
		}
		else if (stringArr[1].toString().contains("basic education, female")) {
			returnText = new Text("Employed with Basic Education (Female): ");
		}
		else if (stringArr[1].toString().contains("intermediate education, female")) {
			returnText = new Text("Employed with Intermediate Education (Female): ");
		}
		else if (stringArr[1].toString().contains("advanced education, female")) {
			returnText = new Text("Employed with Advanced Education (Female): ");
		}
		else {
			returnText = new Text("Erroneous Data: " + stringArr[1].toString());
		}
		
		returnText = new Text(countryName.toString() + returnText.toString());
		return returnText;
	}
	
	public static DoubleWritable formatDoubleWritable (DoubleWritable someDoubleWritable) {
		
		double roundOff = Math.round(someDoubleWritable.get() * 100.0) / 100.0;
		return new DoubleWritable(roundOff);
	}

}
