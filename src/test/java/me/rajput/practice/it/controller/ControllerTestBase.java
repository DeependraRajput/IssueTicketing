package me.rajput.practice.it.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;

import me.rajput.practice.it.TestBase;

public class ControllerTestBase extends TestBase {
	
	@Autowired
	protected TestRestTemplate restTemplate;

}
