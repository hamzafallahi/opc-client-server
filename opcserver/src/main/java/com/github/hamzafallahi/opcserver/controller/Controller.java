/*
 *
 *  * Copyright (c) [2017] - [2024]
 *  * ADDIXO MEA (MOMSOFT) All rights reserved.
 *
 */

package com.github.hamzafallahi.opcserver.controller;

import com.github.hamzafallahi.opcserver.wather.WeatherService;
import com.github.hamzafallahi.opcserver.wather.bean.WeatherBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
//under construction
@RestController
public class Controller {

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/weather")
    public WeatherBean getWeather(@RequestParam String city, @RequestParam String country) {
        return weatherService.getWeather(city, country);
    }
}