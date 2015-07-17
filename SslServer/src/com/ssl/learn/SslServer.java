package com.ssl.learn;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

public class SslServer {

	String keyStorePath = "";
	String keyStorePwd = "";

	public SslServer() {
		keyStorePath = getClass().getResource("sample.jks").getPath();
		keyStorePwd = "123456";
	}

	public void startServer(int port) {
		try {
			System.out.println("Starting server...");
			SSLContext ctx = SSLContext.getInstance("SSL");
			//Creates key store object
			KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
			//Loads key store file into keystore object
			keyStore.load(new FileInputStream(keyStorePath),
					keyStorePwd.toCharArray());
			
			KeyManagerFactory kmf = KeyManagerFactory
					.getInstance(KeyManagerFactory.getDefaultAlgorithm());
			//Initializes the KeyManager  with the keystore
			kmf.init(keyStore, keyStorePwd.toCharArray());
			//Initializes SSL context the Key Manager
			ctx.init(kmf.getKeyManagers(), null, null);
			//Creates SSL server factory from the SSL context
			SSLServerSocketFactory sslserversocketfactory = ctx
					.getServerSocketFactory();
			SSLServerSocket sslserversocket = (SSLServerSocket) sslserversocketfactory
					.createServerSocket(port);
			//Opens the socket
			SSLSocket sslsocket = (SSLSocket) sslserversocket.accept();
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

	public static void main(String[] arstring) {
		new SslServer().startServer(9999);
	}
}
