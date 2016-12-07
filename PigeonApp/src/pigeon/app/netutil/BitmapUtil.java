package pigeon.app.netutil;

import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BitmapUtil {
	public static Bitmap getBitmap(String url){
		Bitmap bitmap = null;
		try {
			URL url2=new URL(url);
			HttpURLConnection httpURLConnection=(HttpURLConnection) url2.openConnection();
		    httpURLConnection.setReadTimeout(3000);
		    int code=httpURLConnection.getResponseCode();
		    if(code==200){
		    	bitmap=BitmapFactory.decodeStream(httpURLConnection.getInputStream());
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;
	}
}
