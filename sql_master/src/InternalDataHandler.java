

import org.json.simple.JSONObject;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;

public class InternalDataHandler extends ServerResource{
	SM sm = SMFactory.getSM();
	DataMapper mapper = DataMapper.getInstance();

	@Get
	public String read() {
		String key = (String) this.getRequestAttributes().get(InstanceInfo.KEY);
		SM.OID oid = mapper.get(key);		
		Record fetched;
		try {
			fetched = (Record) sm.fetch(oid);
			String response = new String(fetched.getBytes(0, 0));
			System.out.println("Write from internal Data Handler");
			return response;
		} catch (Exception e) {
			e.printStackTrace();			
		}
		return null;		
	}
	
	@Post
	public String write(String jsonData) {
		try {
			JSONObject json = NodeHelper.stringToJSON(jsonData);
			String key = (String) json.get(InstanceInfo.KEY);
			String value = (String) json.get(InstanceInfo.VALUE);
		
			Record record = new Record(20);
			record.setBytes(value.getBytes());
		
			SM.OID oid = sm.store(record);				
			mapper.put(key, oid);
			
			int status = 404;
			if (ConfService.getInstance().isMaster()) {
				//TODO different behavior for AP & CP in case of parition
				 status = NodeHelper.doHttpPost("http://"+ ConfService.getInstance().getSlave1_ip() +":"+ ConfService.getInstance().getSlave1_internal_port() + "/restlet/test", jsonData);
				 status = NodeHelper.doHttpPost("http://"+ ConfService.getInstance().getSlave2_ip() +":"+ ConfService.getInstance().getSlave2_internal_port() + "/restlet/test", jsonData);				
			}
			System.out.println("Write from internal Data Handler");
			return status + "";

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Delete
	public void deleteData() {
		try {
			String key = (String) this.getRequestAttributes().get("key");
			SM.OID oid = mapper.get(key);		
			sm.delete(oid);
			if (ConfService.getInstance().isMaster()) {
				NodeHelper.doHttpDelete("http://"+ ConfService.getInstance().getSlave1_ip() +":"+ ConfService.getInstance().getSlave1_internal_port() + "/restlet/test/" + key);
				NodeHelper.doHttpDelete("http://"+ ConfService.getInstance().getSlave2_ip() +":"+ ConfService.getInstance().getSlave2_internal_port() + "/restlet/test/" + key);
			}
			System.out.println("Delete from internal Data Handler");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	@Put
	public String update(String jsonData) throws Exception {		
		JSONObject json = NodeHelper.stringToJSON(jsonData);
		String key = (String) json.get(InstanceInfo.KEY);
		String value = (String) json.get(InstanceInfo.VALUE);
		
		Record record = new Record(20);
		record.setBytes(value.getBytes());

		SM.OID oid = mapper.get(key);		
		oid = sm.update(oid, record);
		mapper.put(key, oid);
		if (ConfService.getInstance().isMaster()) {
			 NodeHelper.doHttpPut("http://"+ ConfService.getInstance().getSlave1_ip() +":"+ ConfService.getInstance().getSlave1_internal_port() + "/restlet/test", jsonData);
			 NodeHelper.doHttpPut("http://"+ ConfService.getInstance().getSlave2_ip() +":"+ ConfService.getInstance().getSlave2_internal_port() + "/restlet/test", jsonData);				
		}
		System.out.println("Put from internal Data Handler");
		return key;
	}	
}
