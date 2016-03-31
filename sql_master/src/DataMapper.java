import java.util.HashMap;
import java.util.Map;

public class DataMapper {
	
	private static DataMapper instance = null;
	Map<String, SM.OID> map = new HashMap<String, SM.OID>();
	
	public static DataMapper getInstance() {
		if (instance == null) {
			instance = new DataMapper();
		}
		return instance;
	}
	
	private DataMapper() {		
	}
	
	public void put(String key, SM.OID oid) {
		map.put(key, oid);
	}
	
	public SM.OID get(String key) {
		return map.get(key);
	}
}
