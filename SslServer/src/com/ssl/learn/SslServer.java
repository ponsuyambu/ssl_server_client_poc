package com.ssl.learn;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyStore;
import java.util.Arrays;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

public class SslServer {
	
	public void startServer(){
		
	}
	
	public static void main(String[] arstring) {
		try {
			String keyStoreName = "D:\\git\\certificates\\sample.jks";
			String pwd = "123456";
			System.out.println("Starting server...");
			// System.setProperty("javax.net.ssl.keyStore",
			// "/Users/Pons/certficates/sample_keystore.jks");
			//System.setProperty("javax.net.ssl.keyStorePassword", "123456");
			SSLContext ctx = SSLContext.getInstance("SSL");
			KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
			keyStore.load(new FileInputStream(keyStoreName), pwd.toCharArray());
			KeyManagerFactory kmf = KeyManagerFactory
					.getInstance(KeyManagerFactory.getDefaultAlgorithm());
			kmf.init(keyStore, pwd.toCharArray()); // That's the key's password,
			ctx.init(kmf.getKeyManagers(), null, null);										// if different.
			SSLServerSocketFactory sslserversocketfactory = ctx.getServerSocketFactory();
			SSLServerSocket sslserversocket = (SSLServerSocket) sslserversocketfactory
					.createServerSocket(9999);

			SSLSocket sslsocket = (SSLSocket) sslserversocket.accept();
			System.out.println("Supported cipher suites - "
					+ Arrays.toString(sslsocket.getEnabledCipherSuites()));
			InputStream inputstream = sslsocket.getInputStream();
			InputStreamReader inputstreamreader = new InputStreamReader(
					inputstream);
			BufferedReader bufferedreader = new BufferedReader(
					inputstreamreader);

			String string = null;
			while ((string = bufferedreader.readLine()) != null) {
				System.out.println(string);
				System.out.flush();
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
}
