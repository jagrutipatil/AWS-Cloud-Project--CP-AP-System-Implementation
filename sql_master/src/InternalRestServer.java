

import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Restlet;
import org.restlet.data.Protocol;
import org.restlet.routing.Router;;

public class InternalRestServer extends Application{
//	public static int localPort = NodeHelper.MASTER_INTERNAL_PORT;
	@Override
    public synchronized Restlet createInboundRoot() {
		Router router = new Router(getContext());
		router.attach("/test", InternalDataHandler.class);
		router.attach("/test/{key}", InternalDataHandler.class);
		return router;
	}
	
	public static void main(String args[]) {          
        try {
        	if (args.length > 0) {
        		ConfService.getInstance().loadProperties(args[0]);        		
        	}  else {
        		usage();
        	}
        	
            Component component = new Component();    
            component.getServers().add(Protocol.HTTP, ConfService.getInstance().getMaster_internal_port());  
            component.getDefaultHost().attach("/restlet", new InternalRestServer());

			component.start();
		} catch (Exception e) {
			e.printStackTrace();
		}  
	}
	
	private static void usage() {
		System.out.println("Usage: example <conf file>");
		
	}

}
