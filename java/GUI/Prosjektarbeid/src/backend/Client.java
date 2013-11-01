package backend;
 
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

 
public class Client {
 
	private String url = "http://dev.krekle.net:8081/";
	private Type request;
	private String[] dict;
 
	public Client(String path, Type request, String...keyvalue) throws Exception {
		url = url + path;
		this.request = request;
		this.dict = keyvalue;
		}
 
	public String execute() throws Exception{

		if(this.request == Type.POST){
			return sendPost(this.dict);
		}else{
			return sendGet(this.dict);
		}

	}
	
	// HTTP GET request
	private String sendGet(String...strings) throws Exception {
		url = url + "?";
		for (int i = 0; i < strings.length; i=i+2) {
			url += "&" + strings[i] + "=" + strings[i+1];
		}
		System.out.print("All good");
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);

		HttpResponse response = client.execute(request);
		BufferedReader rd = new BufferedReader(
                       new InputStreamReader(response.getEntity().getContent()));
 
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
 
		//System.out.println(result.toString());
		return result.toString();
	}
 
	// HTTP POST request
	@SuppressWarnings({ "resource", "deprecation" })
	private String sendPost(String...strings) throws Exception {
 
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		
		for (int i = 0; i < strings.length; i = i+2) {
			urlParameters.add(new BasicNameValuePair(strings[i], strings[i+1]));
		}

		post.setEntity(new UrlEncodedFormEntity(urlParameters));
 
		HttpResponse response = client.execute(post);
		BufferedReader rd = new BufferedReader(
                        new InputStreamReader(response.getEntity().getContent()));
 
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
 
		//System.out.println(result.toString());
		return result.toString();
	}
	public enum Type{
		POST, GET
	}
}
