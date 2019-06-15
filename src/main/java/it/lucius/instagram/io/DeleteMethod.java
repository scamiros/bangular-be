package it.lucius.instagram.io;
import java.io.BufferedReader;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.HttpClientBuilder;

public class DeleteMethod extends APIMethod {
	HttpClient client;
	
	public DeleteMethod() {
		super();
		this.type = "GET";
		this.client = HttpClientBuilder.create().build();
	}
	
	@Override
	protected InputStream performRequest() {
		HttpResponse response;
		BufferedReader rd = null;
		HttpDelete post = new HttpDelete(this.methodUri);
		InputStream stream = null;
		try {
			response = client.execute(post);
			stream = response.getEntity().getContent();
		} catch (Exception e) {
			e.printStackTrace();
		}  
		return stream;
	}

}
