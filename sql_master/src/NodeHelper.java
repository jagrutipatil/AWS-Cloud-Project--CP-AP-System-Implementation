import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;



public class NodeHelper {
		
//	public static final boolean isMaster = true;
	
//	public static final String MASTER_IP = "52.24.88.230";
//	public static final int MASTER_EXTERNAL_PORT = 80;
//	public static final int MASTER_INTERNAL_PORT = 8080;
//	
//	public static final String SLAVE_1_IP = "52.34.240.250";	
//	public static final int SLAVE_1_EXTERNAL_PORT = 80;
//	public static final int SLAVE_1_INTERNAL_PORT = 8080;
//
//	public static final String SLAVE_2_IP = "52.26.144.117";
//	public static final int SLAVE_2_EXTERNAL_PORT = 80;
//	public static final int SLAVE_2_INTERNAL_PORT = 8080;
	
	public static JSONObject stringToJSON(String jsonData) {
		try {
			JSONParser parser = new JSONParser();			
			JSONObject obj = (JSONObject) parser.parse(jsonData);
			return obj;
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static int doHttpPost(String url, String jsonData) {
		
		try {
			HttpClient client = HttpClientBuilder.create().build();
			HttpPost httpPost = new HttpPost(url);
			StringEntity params = new StringEntity(jsonData);
			httpPost.setEntity(params);		
			HttpResponse response = client.execute(httpPost);
			
			return response.getStatusLine().getStatusCode();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 404;
	}
	
	public static String doHttpGet(String url) {
		try {
			HttpClient client = HttpClientBuilder.create().build();
			HttpGet get = new HttpGet(url);
			HttpResponse response = client.execute(get);
			if (response.getEntity() != null) {
				HttpEntity entity = response.getEntity();
			    InputStream instream = entity.getContent();

			    StringWriter writer = new StringWriter();
			    IOUtils.copy(instream, writer, "UTF-8");
			    String jsonData = writer.toString();
			    return jsonData;
			}
		} catch (IOException e2) {
			e2.printStackTrace();
		} 
		return null;
	}

	public static int doHttpPut(String url, String jsonData) {
		
		try {
			HttpClient client = HttpClientBuilder.create().build();
			HttpPut httpPut = new HttpPut(url);
			StringEntity params = new StringEntity(jsonData);
			httpPut.setEntity(params);		
			HttpResponse response = client.execute(httpPut);
			
			return response.getStatusLine().getStatusCode();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 404;
	}

	public static String doHttpDelete(String url) {
		try {
			HttpClient client = HttpClientBuilder.create().build();
			HttpDelete delete = new HttpDelete(url);
			HttpResponse response = client.execute(delete);
			if (response.getEntity() != null) {
				HttpEntity entity = response.getEntity();
			    InputStream instream = entity.getContent();

			    StringWriter writer = new StringWriter();
			    IOUtils.copy(instream, writer, "UTF-8");
			    String jsonData = writer.toString();
			    return jsonData;
			}
		} catch (IOException e2) {
			e2.printStackTrace();
		} 
		return null;
	}

}
