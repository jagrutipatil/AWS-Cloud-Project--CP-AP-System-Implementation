import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

public class ClientDataHandler extends ServerResource{
	SM sm = SMFactory.getSM();
	String localIP = ConfService.getInstance().getMaster_ip();
	int localPort = ConfService.getInstance().getMaster_internal_port();
	
	@Get
	public String read() {
		try {
			//TODO check connection to master, if broken then
			System.out.println("Read from Client Data Handler");
			String key = (String) this.getRequestAttributes().get(InstanceInfo.KEY);
			String value = NodeHelper.doHttpGet("http://"+ localIP + ":" + localPort + "/restlet/test/"+key);
			if (value == null)
				return "ERROR";
			return value;
		} catch (ResourceException e) {
			System.out.println("ERROR: Read not successful");
		}
		return "ERROR";
	}
	
	@Post
	public String write(String jsonData) {		
		try {
			System.out.println("Write from Client Data Handler");
			int status = 404;
			if (ConfService.getInstance().isMaster()) {
				status = NodeHelper.doHttpPost("http://"+ ConfService.getInstance().getMaster_ip() +":"+ ConfService.getInstance().getMaster_internal_port() + "/restlet/test", jsonData);
			} else {
				status = NodeHelper.doHttpPost("http://"+ ConfService.getInstance().getMaster_ip() +":"+ ConfService.getInstance().getMaster_external_port() + "/restlet/test", jsonData);
			}
			if (status == 200)
				return "SUCCESS";
		} catch (ResourceException e) {
			System.out.println("ERROR: Write not successful");
		}
		return "ERROR";
	}
	
	@Delete
	public String deleteData() {
		try {
			System.out.println("Delete from Client Data Handler");
			String key = (String) this.getRequestAttributes().get(InstanceInfo.KEY);
			if (ConfService.getInstance().isMaster()) {
				NodeHelper.doHttpDelete("http://"+ ConfService.getInstance().getMaster_ip() +":"+ ConfService.getInstance().getMaster_internal_port() + "/restlet/test/" + key);
			} else {
				NodeHelper.doHttpDelete("http://"+ ConfService.getInstance().getMaster_ip() +":"+ ConfService.getInstance().getMaster_external_port() + "/restlet/test/" + key);
			}
			return "SUCCESS";
		} catch (Exception e) {
			System.out.println("ERROR: Delete not successful");
			e.printStackTrace();			
		}
		return "ERROR";
	}	
	
	@Put
	public String update(String jsonData) throws Exception {		
		try {
			System.out.println("Write from Client Data Handler");
			int status = 404;
			if (ConfService.getInstance().isMaster()) {
				status = NodeHelper.doHttpPut("http://" + ConfService.getInstance().getMaster_ip() + ":" + ConfService.getInstance().getMaster_internal_port() + "/restlet/test", jsonData);
			} else {
				status = NodeHelper.doHttpPut("http://" + ConfService.getInstance().getMaster_ip() + ":" + ConfService.getInstance().getMaster_external_port() + "/restlet/test", jsonData);
			}
			if (status == 200)
				return "SUCCESS";
		} catch (Exception e) {
			System.out.println("ERROR: Update not successful");
			e.printStackTrace();
		}
		return "ERROR";
	}	
}
