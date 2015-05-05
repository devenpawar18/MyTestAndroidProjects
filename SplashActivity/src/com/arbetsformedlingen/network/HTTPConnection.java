package com.arbetsformedlingen.network;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;

import android.util.Log;

public class HTTPConnection {
	StringBuilder builder = new StringBuilder();
	public String convertStreamToString(InputStream is) {
		/*
		 * To convert the InputStream to String we use the Reader.read(char[]
		 * buffer) method. We iterate until the Reader return -1 which means
		 * there's no more data to read. We use the StringWriter class to
		 * produce the string.
		 */
		try {
			if (is != null) {
				Writer writer = new StringWriter();

				char[] buffer = new char[1024];
				try {
					Reader reader = new BufferedReader(new InputStreamReader(
							is, "UTF-8"));
					int n;
					while ((n = reader.read(buffer)) != -1) {
						writer.write(buffer, 0, n);
					}
					Log.d("HttpConnection", "convertStreamToString :: "
							+ writer.toString());
				} finally {
					is.close();
				}
				return writer.toString();
			} else {
				return "";
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return "";
		}
	}
	
	

	public InputStream OpenHttpConnection(String urlString) {
		InputStream input = null;

		try {
			urlString.replace(" ", "%20");
			try {
				// URI uri = new URI(urlString);
				HttpClient client = new DefaultHttpClient();
				HttpGet post = new HttpGet(urlString);
				post.setHeader("From", "PBIphone@arbetsformedlingen.se");
				post.setHeader("Accept", "application/json");
				post.setHeader("Accept-Language", "SE");
				Log.d("Header Count", post.getAllHeaders().length + "");
				for (int i = 0; i < post.getAllHeaders().length; i++) {
					Log.d("Header Count", post.getAllHeaders().length + "");
					Log.d("Header " + post.getAllHeaders()[i].getName(),
							post.getAllHeaders()[i].getValue());
				}

				HttpResponse response = client.execute(post);
				input = response.getEntity().getContent();

				Log.d("Entity Content", response.getEntity().toString());

			} catch (Exception ex) {
				Log.d("Connection1", ex.toString());
			}

		} catch (Exception ex) {
			Log.d("Connection2", ex.toString());
		}

		return input;
	}

	public String translate(String urlString, String data) {
		try {

			Log.d("URL", urlString.toString());
			try {
				URL myURL = new URL(urlString);
				HttpURLConnection httpConn = (HttpURLConnection) myURL
						.openConnection();
				httpConn.setRequestMethod("POST");
				httpConn.setRequestProperty("Host",
						"api.microsofttranslator.com");
				httpConn.setRequestProperty("User-Agent", "");
				httpConn.setRequestProperty("Content-Type", "text/xml");
				httpConn.setRequestProperty("Content-Length",
						Integer.toString(data.getBytes("UTF-8").length));
				httpConn.setRequestProperty("Accept-Language", "en_US,en");
				httpConn.setDoOutput(true);
				httpConn.getOutputStream().write(data.getBytes("UTF-8"));

				// read the output from the server
				BufferedReader reader;
				String line = null;
				System.out.println("Response code:"
						+ httpConn.getResponseCode());
				if (httpConn.getResponseCode() != 200) {
					reader = new BufferedReader(new InputStreamReader(
							httpConn.getErrorStream()));
				} else {
					reader = new BufferedReader(new InputStreamReader(
							httpConn.getInputStream()));
				}
				while ((line = reader.readLine()) != null)
				{
					builder.append(line);
					System.out.println(line);
				}
				reader.close();
				if (httpConn != null)
					httpConn.disconnect();
			
			} catch (Exception ex) {
				Log.d("Connection1", ex.toString());
			}

		} catch (Exception ex) {
			Log.d("Connection2", ex.toString());
		}
			
		return builder.toString();
	}
	
	public InputStream shortenUrl(String urlString) {
		InputStream input = null;
		try {
			try {
				
				 HttpClient client = new DefaultHttpClient();
				 HttpPost post = new HttpPost(urlString);
				 post.setHeader("Content-Type", "text/xml");
				 
				 HttpResponse response = client.execute(post);
				 input = response.getEntity().getContent();
				
			} catch (Exception ex) {
				Log.d("Connection1", ex.toString());
			}

		} catch (Exception ex) {
			Log.d("Connection2", ex.toString());
		}
		
		return input;
	}
	

	public InputStream secureTranslate(String urlString) {
		InputStream input = null;
		try {

			Log.d("URL", urlString.toString());
			try {
				HostnameVerifier hostnameVerifier = org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
				DefaultHttpClient client = new DefaultHttpClient();
				SchemeRegistry registry = new SchemeRegistry();
				SSLSocketFactory socketFactory = SSLSocketFactory
						.getSocketFactory();
				socketFactory
						.setHostnameVerifier((X509HostnameVerifier) hostnameVerifier);
				registry.register(new Scheme("https", socketFactory, 443));
				SingleClientConnManager mgr = new SingleClientConnManager(
						client.getParams(), registry);
				DefaultHttpClient httpClient = new DefaultHttpClient(mgr,
						client.getParams());
				// Set verifier
				HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);
				HttpGet httpPost = new HttpGet(urlString);
				HttpResponse response = httpClient.execute(httpPost);
				input = response.getEntity().getContent();

			} catch (Exception ex) {
				Log.d("Connection1", ex.toString());
			}

		} catch (Exception ex) {
			Log.d("Connection2", ex.toString());
		}

		return input;
	}

}
