import java.util.LinkedHashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

public class ClientDataHandler extends ServerResource{
	SM sm = SMFactory.getSM();
	String localIP = ConfService.getInstance().getCurrentIP();
	int localPort = ConfService.getInstance().getCurrent_internal_port();
	
	@Get
	public String read() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		try {
			//TODO check connection to master, if broken then
			System.out.println("Read from Client Data Handler");
			String key = (String) this.getRequestAttributes().get(InstanceInfo.KEY);
			String value = NodeHelper.doHttpGet("http://"+ localIP + ":" + localPort + "/restlet/test/"+key);
			map.put("_NodeIP", ConfService.getInstance().getCurrentIP());
			map.put("_Version", "1");
			map.put("_Action", "READ (KEY)");
			if (value == null)
				map.put("_Status", "KEY NOT PRESENT");
			else {
				map.put("_Status", "200 SUCCESS");
				map.put("Key", key);
				map.put("Value", value);

			}

		} catch (ResourceException e) {
			System.out.println("ERROR: Read not successful");
		}
		return NodeHelper.createJSONStr(map);
	}
	
	@Post
	public String write(String jsonData) {
		int status = 404;
		Map<String, String> map = new LinkedHashMap<String, String>();
		try {
			System.out.println("Write from Client Data Handler");
			
			String masterIP = ConfService.getInstance().getMaster_ip();
			int masterInternalPort = ConfService.getInstance().getMaster_internal_port();
			if (NodeHelper.pingHost(masterIP, masterInternalPort, 1000)) {
				status = NodeHelper.doHttpPost("http://"+ ConfService.getInstance().getMaster_ip() +":"+ ConfService.getInstance().getMaster_internal_port() + "/restlet/test", jsonData);
			} 
			
			JSONObject json = NodeHelper.stringToJSON(jsonData);
			String key = (String) json.get(InstanceInfo.KEY);
			String value = (String) json.get(InstanceInfo.VALUE);

			map.put("_NodeIP", ConfService.getInstance().getCurrentIP());
			map.put("_Version", "1");
			map.put("_Action", "WRITE (KEY, VALUE)");
			if (status == 404) {
				map.put("_Action", "WRITE FAILED");
			} else {
				map.put("_Status", "200 SUCCESS");
			}
			
			map.put("Key", key);
			map.put("Value", value);
 
		} catch (ResourceException e) {
			System.out.println("ERROR: Write not successful");
		}
		return NodeHelper.createJSONStr(map);
	}
	
	@Delete
	public String deleteData() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		try {
			System.out.println("Delete from Client Data Handler");
			String key = (String) this.getRequestAttributes().get(InstanceInfo.KEY);
			String masterIP = ConfService.getInstance().getMaster_ip();
			int masterInternalPort = ConfService.getInstance().getMaster_internal_port();

			map.put("_NodeIP", ConfService.getInstance().getCurrentIP());
			map.put("_Version", "1");					
			map.put("_Action", "DELETE (KEY)");

			if (NodeHelper.pingHost(masterIP, masterInternalPort, 1000)) {
				NodeHelper.doHttpDelete("http://"+  masterIP+":"+ masterInternalPort + "/restlet/test/" + key);
				map.put("_Status", "200 SUCCESS");
			} else {
				map.put("_Status", "DELETE FAILED");
			}
			map.put("Key", key);
		} catch (Exception e) {
			System.out.println("ERROR: Delete not successful");
			e.printStackTrace();			
		}
		return NodeHelper.createJSONStr(map);
	}	
	
	@Put
	public String update(String jsonData) throws Exception {		
		Map<String, String> map = new LinkedHashMap<String, String>();
		try {
			System.out.println("Write from Client Data Handler");
			int status = 404;
			String masterIP = ConfService.getInstance().getMaster_ip();
			int masterInternalPort = ConfService.getInstance().getMaster_internal_port();
			JSONObject json = NodeHelper.stringToJSON(jsonData);
			String key = (String) json.get(InstanceInfo.KEY);
			String value = (String) json.get(InstanceInfo.VALUE);

			map.put("_NodeIP", ConfService.getInstance().getCurrentIP());
			map.put("_Version", "1");					
			map.put("_Action", "UPDATE (KEY, NEW VALUE)");
			if (NodeHelper.pingHost(masterIP, masterInternalPort, 1000)) {
				status = NodeHelper.doHttpPut("http://" + masterIP + ":" + masterInternalPort + "/restlet/test", jsonData);
				if (status == 200)
					map.put("_Status", "200 SUCCESS");
				else
					map.put("_Status", "UPDATE FAILED");
			}
			map.put("Key", key);
			map.put("Value", value);

		} catch (Exception e) {
			System.out.println("ERROR: Update not successful");
			e.printStackTrace();
		}
		return NodeHelper.createJSONStr(map);
	}	
}
