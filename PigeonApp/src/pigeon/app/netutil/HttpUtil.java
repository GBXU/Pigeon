package pigeon.app.netutil;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import android.telecom.Call;
import android.util.Log;
import okhttp3.FormBody;
import okhttp3.FormBody.Builder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtil {
	public static String sendGet(String str, String format){
		//"UTF-8"
		String result = null;
		try {
			//切记加 http://
			URL url = new URL(str);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("GET");
			connection.setConnectTimeout(8000);
			connection.setReadTimeout(8000);
			InputStream in = connection.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			StringBuilder response = new StringBuilder();
			String line;
			while ((line = reader.readLine())!= null) {
				response.append(line);
			}
			result = response.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
		/*
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder().url(str).build();
		Response response;
		try {
			response = client.newCall(request).execute();
			if (response.isSuccessful()) {
				result = response.body().string();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		 */
	}
	public static String sendPost(String str,Map<String,String> mParams){
		System.out.print(mParams);
		System.out.println("stest");
		String result = null;
		OkHttpClient client = new OkHttpClient();
		Builder  builder =new FormBody.Builder();
		for(Map.Entry<String, String> entry : mParams.entrySet()){
			builder.add(entry.getKey(),entry.getValue());
		}
		RequestBody body = builder.build();
		Request request = new Request.Builder()
										.url(str)
										.post(body)
										.build();

		try {
			Response response = client.newCall(request).execute();
			if (response.isSuccessful()) {
				result = response.body().string();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
/*		
		try {
			//切记加 http://
			URL url = new URL(str);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("POST");
			connection.setConnectTimeout(8000);
			connection.setReadTimeout(8000);
			//post
			DataOutputStream out =new DataOutputStream(connection.getOutputStream());

			out.writeBytes(content);
			out.flush();
			out.close();
			InputStream in = connection.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			StringBuilder response = new StringBuilder();
			String line;
			while ((line = reader.readLine())!= null) {
				response.append(line);
			}
			result = response.toString();
			Log.e("http", result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
*/
		System.out.println("result:   "+result);
		return result;
	}	
}
