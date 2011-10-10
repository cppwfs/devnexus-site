package com.hillert.apptools.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.Transactional;

import com.devnexus.ting.common.SpringContextMode;
import com.devnexus.ting.core.service.SystemSetupService;

/**
 *
 * @author Gunnar Hillert
 * @since 2.0
 *
 */
public class ContextRefreshedEventListener implements
                               ApplicationListener < ContextRefreshedEvent > {

	private static final Logger LOGGER = LoggerFactory.getLogger(ContextRefreshedEventListener.class);

	@Autowired
	private SystemSetupService systemSetupService;

	@Autowired
	Environment environment;

    /**
    *
    *
    */
   @Transactional
   @Override
   public void onApplicationEvent(ContextRefreshedEvent event) {

       //From: http://forum.springsource.org/showthread.php?t=84312&page=2

       if (event.getApplicationContext().getParent() == null) {

           LOGGER.info("Parent Spring Context started.");

           if (!systemSetupService.isDatabaseSetup()) {

               LOGGER.info("Ting Database is not setup, yet. Initializing DB...");

               systemSetupService.createDatabase();

               LOGGER.info("Ting Database is not setup, yet. Populating Seed Data...");

               //systemSetupService.loadAndRestoreSeedData();

               if (environment.acceptsProfiles(SpringContextMode.DemoContextConfiguration.getCode())) {
            	   LOGGER.info("Ting Database is not setup, yet. Populating Demo Data...");

            	   //FIXME need to populate demo data.

               }


           }

       }

   }

}
