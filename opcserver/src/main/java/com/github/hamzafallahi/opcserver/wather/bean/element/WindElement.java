/*
 *
 *  * Copyright (c) [2017] - [2024]
 *  * ADDIXO MEA (MOMSOFT) All rights reserved.
 *
 */

package com.github.hamzafallahi.opcserver.wather.bean.element;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class WindElement {

	private Double speed;
	
	private Integer deg;

	public Double getSpeed() {
		return speed;
	}

	public void setSpeed(Double speed) {
		this.speed = speed;
	}

	public Integer getDeg() {
		return deg;
	}

	public void setDeg(Integer deg) {
		this.deg = deg;
	}

	@Override
	public String toString() {
		return "WindElement [speed=" + speed + ", deg=" + deg + "]";
	}
	
}
