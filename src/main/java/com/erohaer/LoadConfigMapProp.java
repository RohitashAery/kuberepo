package com.erohaer;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.sql.Timestamp;
import java.util.Properties;
import java.util.concurrent.Executors;

/**
 * 
 * FILE_NAME: GetPropertyValues.java
 * 
 * MODULE DESCRIPTION: TODO, US/F/D
 *
 * @author erohaer, Date: Sep 9, 2020 4:15:16 PM 2020
 * 
 * @(c) COPYRIGHT 2020 Ericsson Inc. All Rights Reserved. Ericsson Inc.Proprietary.
 *
 */
public class LoadConfigMapProp {
		private Properties prop = new Properties();
		Timestamp timestamp;
		int i=0;
		// static variable single_instance of type Singleton 
	    private static LoadConfigMapProp single_instance = null;
	    private String propFileName = "/opt/test/cmfile.properties";
	    private String dirName = "/opt/test";
//	    private String dirName = "C:\\Users\\erohaer\\Desktop";
//	    private String propFileName = "C:\\Users\\erohaer\\Desktop\\cmfile.properties";
	    
		/**
		 *
		 * Purpose: private constructor to make it singleton
		 *
		 * Date: Sep 9, 2020 4:16:18 PM 2020
		 * 
		 * US/D/F Number: 
		 *
		 * @param result
		 * @param inputStream 
		 */
		private LoadConfigMapProp() {
			super();
			try {
				loadProperties(propFileName);
//				watchFile();
				
				Executors.newSingleThreadExecutor().execute(new Runnable() {
				    @Override
				    public void run() {
				    	watchFile();
				    }
				});
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		 // static method to create instance of Singleton class 
	    public static LoadConfigMapProp getInstance() 
	    { 
	        if (single_instance == null) 
	            single_instance = new LoadConfigMapProp(); 
	  
	        return single_instance; 
	    } 

	    /**
	     * 
	     * User: erohaer , Date: Sep 9, 2020 4:21:03 PM 2020
	     *
	     * Purpose: load properties
	     *
	     * US/D/F Number:
	     * 
	     * Return Type: void
	     *
	     * @throws IOException
	     */
		public void loadProperties(String propFileName) throws IOException {
			try (InputStream input = new FileInputStream(propFileName)) {
				Timestamp timestampNew = null;
				prop.load(input);
				System.out.println("Properties loaded from "+propFileName);
				
				 if(i>0){
					 timestampNew = new Timestamp(System.currentTimeMillis());	 
				 }else{
					 timestamp = new Timestamp(System.currentTimeMillis());
					 timestampNew= timestamp;
				 }
				 
				 
				 long milliseconds = timestampNew.getTime() - timestamp.getTime();
					int seconds = (int) milliseconds / 1000;

					int hours = seconds / 3600;
					int minutes = (seconds % 3600) / 60;
					seconds = (seconds % 3600) % 60;
				 
				System.out.println("i:"+i+", OldTime: "+timestamp+" , NewTime: "+timestampNew);
				System.out.println("Diff: "+hours+"hrs, "+minutes+"minutes, "+seconds+"secs");
				System.out.println(prop+" , "+prop.getProperty("DSA_REST_RETRY_DELAY"));
				i=i+1;
			} catch (Exception e) {
				System.out.println("Exception: " + e);
			} 
		}
		
		/**
		 * 
		 * User: erohaer , Date: Sep 9, 2020 4:23:54 PM 2020
		 *
		 * Purpose: get properties value
		 *
		 * US/D/F Number:
		 * 
		 * Return Type: String
		 *
		 * @param propName
		 * @return
		 */
		public String getProp(String propName){
			return prop.getProperty(propName);
		}
		
		/**
		 * 
		 * User: erohaer , Date: Sep 9, 2020 5:20:57 PM 2020
		 *
		 * Purpose: TODO
		 *
		 * US/D/F Number:
		 * 
		 * Return Type: void
		 *
		 */
		private void watchFile(){
			 try {
		            WatchService watcher = FileSystems.getDefault().newWatchService();
		            Path dir = Paths.get(dirName);
		            dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
		             
		            System.out.println("Watch Service registered for dir: " + dir.getFileName());
		             
		            while (true) {
		                WatchKey key;
		                try {
		                    key = watcher.take();
		                } catch (InterruptedException ex) {
		                    return;
		                }
		                 
		                for (WatchEvent<?> event : key.pollEvents()) {
		                    WatchEvent.Kind<?> kind = event.kind();
		                     
		                    @SuppressWarnings("unchecked")
		                    WatchEvent<Path> ev = (WatchEvent<Path>) event;
		                    Path fileName = ev.context();
		                     
		                    System.out.println(kind.name() + ": " + fileName);
		                     
		                    if (kind == ENTRY_MODIFY) {
		                    	loadProperties(propFileName);
		                        System.out.println(propFileName+": file has changed!!!");
		                    }
		                }
		                 
		                boolean valid = key.reset();
		                if (!valid) {
		                    break;
		                }
		            }
		             
		        } catch (IOException ex) {
		            System.err.println(ex);
		        }
		    }
}
