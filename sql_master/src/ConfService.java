import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfService {

	private String master_ip = "";
	private int master_internal_port = 0;
	private int master_external_port = 0;

	private String slave1_ip = "";
	private int slave1_internal_port = 0;
	private int slave1_external_port = 0;

	private String slave2_ip = "";
	private int slave2_internal_port = 0;
	private int slave2_external_port = 0;

	private boolean isMaster = false;
	
	private Properties props = new Properties();
	
	private static ConfService instance = null;
	
	public static ConfService getInstance() {
		if (instance == null) {
			instance = new ConfService();
		}
		return instance;
	}
	
	private ConfService() {
		
	}

	public void loadProperties(String fileName) {
		InputStream is;
		try {
			is = new FileInputStream(fileName);
			props.load(is);
			
			master_ip = (String) props.get("master_ip");
			master_internal_port = Integer.parseInt((String) props.get("master_internal_port"));
			master_external_port = Integer.parseInt((String) props.get("master_external_port"));

			slave1_ip = (String) props.get("slave1_ip");
			slave1_internal_port = Integer.parseInt((String) props.get("slave1_internal_port"));
			slave1_external_port = Integer.parseInt((String) props.get("slave1_external_port"));

			slave2_ip = (String) props.get("slave2_ip");
			slave2_internal_port = Integer.parseInt((String) props.get("slave2_internal_port"));
			slave2_external_port = Integer.parseInt((String) props.get("slave2_external_port"));
			
			String masterValue = (String) props.get("master"); 
			if(masterValue.equalsIgnoreCase("true")) {
				isMaster = true;
			} else {
				isMaster = false;
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	    
	}
	
	public boolean isMaster() {
		return isMaster;
	}
	
	public String getMaster_ip() {
		return master_ip;
	}

	public String getSlave1_ip() {
		return slave1_ip;
	}

	public String getSlave2_ip() {
		return slave2_ip;
	}

	public int getMaster_internal_port() {
		return master_internal_port;
	}

	public int getMaster_external_port() {
		return master_external_port;
	}

	public int getSlave1_internal_port() {
		return slave1_internal_port;
	}

	public int getSlave1_external_port() {
		return slave1_external_port;
	}

	public int getSlave2_internal_port() {
		return slave2_internal_port;
	}

	public int getSlave2_external_port() {
		return slave2_external_port;
	}

	
}
