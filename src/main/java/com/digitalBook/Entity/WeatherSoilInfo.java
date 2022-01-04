package com.digitalBook.Entity;

import org.apache.ibatis.type.Alias;

import lombok.Data;
import lombok.Setter;
import lombok.ToString;

@Setter
@ToString
@Alias("WeatherSoilInfo")
public class WeatherSoilInfo {
	private String weather;
	private String soil;
	private int weather_soil_info_id;
	private int plan_id;
	private String comment;
}
