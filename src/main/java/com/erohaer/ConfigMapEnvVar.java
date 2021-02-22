package com.erohaer;


/**
 * 
 * FILE_NAME: ConfigMapEnvVar.java
 * 
 * MODULE DESCRIPTION: US/F/D
 *
 * @author , Date: Aug 25, 2020 11:00:51 AM 2020
 * 
 *
 */
public class ConfigMapEnvVar {

	/**
	 * User: erohaer , Date: Aug 25, 2020 11:00:51 AM 2020
	 *
	 * Purpose: To get value from ENV variable and print on console
	 *
	 * US/D/F Number:
	 * 
	 * Return Type: void
	 *
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
			for(int i=0;i<30000;i++){
				Thread.sleep(5000);
				String var1=LoadConfigMapProp.getInstance().getProp("DSA_REST_RETRY_DELAY");
				String var2=LoadConfigMapProp.getInstance().getProp("AGW_REST_RETRY_DELAY");
				String var3=LoadConfigMapProp.getInstance().getProp("CUSTOMERCARE_REST_RETRY_DELAY");
				String var4=LoadConfigMapProp.getInstance().getProp("SELFCARE_REST_RETRY_DELAY");
				System.out.println(i+": DSA_REST_RETRY_DELAY="+var1+", AGW_REST_RETRY_DELAY="+var2+", CUSTOMERCARE_REST_RETRY_DELAY="+var3+", SELFCARE_REST_RETRY_DELAY="+var4);
			}
	}
}
