package com.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.security.KeyStore;
import java.util.Arrays;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

public class Client {
	public static void main(String[] arstring) {
		
		String keyStoreName = "/Users/Pons/certficates/truststore.jks";
		String pwd = "123456";
		try {
			System.out.println("Client");
//			System.setProperty("javax.net.ssl.keyStore", "/Users/Pons/certficates/sample_keystore.jks");
//			System.setProperty("javax.net.ssl.keyStorePassword", "123456");
			
			/* Create keystore */
	        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
	        keyStore.load(new FileInputStream(keyStoreName), pwd.toCharArray());

	        /* Get factory for the given keystore */
	        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
	        tmf.init(keyStore);
	        SSLContext ctx = SSLContext.getInstance("SSL");
	        ctx.init(null, tmf.getTrustManagers(), null);
			
			SSLSocketFactory sslsocketfactory = ctx.getSocketFactory();
			
			
			SSLSocket sslsocket = (SSLSocket) sslsocketfactory.createSocket(
					"localhost", 9999);
			System.out.println("Supported cipher suites - "
					+ Arrays.toString(sslsocket.getEnabledCipherSuites()));
			InputStream inputstream = System.in;
			InputStreamReader inputstreamreader = new InputStreamReader(
					inputstream);
			BufferedReader bufferedreader = new BufferedReader(
					inputstreamreader);

			OutputStream outputstream = sslsocket.getOutputStream();
			OutputStreamWriter outputstreamwriter = new OutputStreamWriter(
					outputstream);
			BufferedWriter bufferedwriter = new BufferedWriter(
					outputstreamwriter);

			String string = null;
			while ((string = bufferedreader.readLine()) != null) {
				bufferedwriter.write(string + '\n');
				bufferedwriter.flush();
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
}
