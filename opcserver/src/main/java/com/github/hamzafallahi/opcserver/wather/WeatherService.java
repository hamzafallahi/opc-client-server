/*
 *
 *  * Copyright (c) [2017] - [2024]
 *  * ADDIXO MEA (MOMSOFT) All rights reserved.
 *
 */

package com.github.hamzafallahi.opcserver.wather;

import com.github.hamzafallahi.opcserver.wather.bean.WeatherBean;

public interface WeatherService {
	
	/**
	 * Return the current weather of pre-configured city 
	 * @return CurrentWeatherBean that contains the last read weather
	 */
	WeatherBean getCurrentWeather();
	
	String getCity();
	
	String getApiCall();
	
	void setCity(String city);
	
	String getLang();

	void setLang(String lang);

	WeatherBean getWeather(String city, String country);
}
