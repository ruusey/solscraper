package com.solscraper.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class PropertiesUtils {
	private static final File FILE = new File("./state.properties");
	public static void saveProperties(Properties properties) throws IOException
    {
        FileOutputStream fr = new FileOutputStream(FILE);
        properties.store(fr, "Properties");
        fr.close();
    }

    public static Properties loadProperties() throws IOException
    {
		Properties properties = new Properties();

    	if(FILE!=null && !FILE.exists()) {
    		FILE.createNewFile();
            FileInputStream fi=new FileInputStream(FILE);
            properties.load(fi);
            properties.setProperty("tl.last-message-id-txt", "-1");
            properties.setProperty("tl.last-message-id-img", "-1");
            properties.setProperty("tl.last-message-id-basic", "-1");
            saveProperties(properties);
            fi.close();
    	}else {
            FileInputStream fi=new FileInputStream(FILE);
            properties.load(fi);
            fi.close();
            log.info("After Loading properties: {}", properties);
    	}
    	
        return properties;
    }
}
