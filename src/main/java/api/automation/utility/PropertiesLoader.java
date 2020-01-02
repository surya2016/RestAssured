package api.automation.utility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertiesLoader {
	
	public static Properties prop =new Properties();
	
	static{
		FileInputStream file;
		try {
			file = new FileInputStream(System.getProperty("user.dir")+"/src/resources/config.properties");
			prop.load(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
