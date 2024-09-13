package org.zerock.myapp.security;

import java.util.Arrays;

import org.zerock.myapp.security.annotation.DTO;

import lombok.extern.log4j.Log4j2;

@DTO

@Log4j2
public class App {

	public static void main(String[] args) {
		log.trace("main({}) invoked.", Arrays.toString(args));

	} // main

} // end class
