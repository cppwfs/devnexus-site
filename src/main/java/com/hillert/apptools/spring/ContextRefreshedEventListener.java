/*
 * Copyright 2002-2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hillert.apptools.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.devnexus.ting.core.service.SystemSetupService;

/**
 *
 * @author Gunnar Hillert
 * @since 2.0
 *
 */
public class ContextRefreshedEventListener implements
		ApplicationListener<ContextRefreshedEvent> {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ContextRefreshedEventListener.class);

	@Autowired
	private SystemSetupService systemSetupService;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {

		// From: http://forum.springsource.org/showthread.php?t=84312&page=2

		if (event.getApplicationContext().getParent() == null) {
			LOGGER.info("Setting up database...");
			systemSetupService.setupDatabase();
		}

	}

}
