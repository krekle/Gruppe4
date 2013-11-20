package sheepfarmer.net.client;
 
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
/**
 * 
 * @author krekle
 *	Generic made class for communicating with the server.
 *	Can send both POST and GET 
 */
 
@SuppressWarnings("deprecation")

public class Client {
 
	private String url = "http://dev.krekle.net:8081/";
	private Type request;
	private String[] dict;
 
	/**
	 * 
	 * @param path
	 * is the url to the wanted api endpoint url + path
	 * @param request
	 * is an Enum with either POST or GET
	 * @param keyvalue
	 * is an unknow amount of string parameters sent in as key, value, key, value ...
	 * @throws Exception
	 * 
	 */
	public Client(String path, Type request, String...keyvalue) throws Exception {
		url = url + path;
		this.request = request;
		this.dict = keyvalue;
		}
 
	/**
	 * This method executes the request
	 * @return 
	 * sendPost method if type = POST
	 * sendGet method if type = GET
	 * @throws Exception
	 */
	public String execute() throws Exception{

		if(this.request == Type.POST){
			return sendPost(this.dict);
		}else{
			return sendGet(this.dict);
		}

	}
	/**
	 * Sends the Http-Get requests when the execute() method is called
	 * @param strings
	 * @return 
	 * A string made from the response from the server
	 * @throws Exception
	 * Catches an exception if there is errors when communicating with the server
	 */
	// HTTP GET request
	@SuppressWarnings("resource")
	private String sendGet(String...strings) throws Exception {
		url = url + "?";
		for (int i = 0; i < strings.length; i=i+2) {
			url += "&" + strings[i] + "=" + strings[i+1];
		}
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
 
		return result.toString();
	}
 
	// HTTP POST request
	/**Sends the http-post request when execute() method is called
	 * 
	 * @param strings
	 * @return
	 * A string of the response from the server
	 * @throws Exception
	 * Catches an exception if there is errors when communicating with the server
	 */
	@SuppressWarnings({ "resource" })
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
 
		return result.toString();
	}
	public enum Type{
		POST, GET
	}
}
