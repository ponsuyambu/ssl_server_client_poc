package com.client;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

public class Client {

	String certificatePath = null;

	public Client() {
		certificatePath = Client.class.getResource("sample.crt").getPath();

	}

	public void initializeClient(int port) {
		try {
			System.out.println("Initializing Client...");
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			InputStream is = Client.class.getResourceAsStream("sample.crt");
			InputStream caInput = new BufferedInputStream(is);
			Certificate ca;
			try {
				// Loads certificate into ca object
				ca = cf.generateCertificate(caInput);
			} finally {
				caInput.close();
			}

			// Create a KeyStore containing our trusted CAs
			String keyStoreType = KeyStore.getDefaultType();
			KeyStore keyStore = KeyStore.getInstance(keyStoreType);
			keyStore.load(null, null);
			keyStore.setCertificateEntry("ca", ca);

			/* Get factory for the given keystore */
			TrustManagerFactory tmf = TrustManagerFactory
					.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			tmf.init(keyStore);
			SSLContext ctx = SSLContext.getInstance("SSL");
			ctx.init(null, tmf.getTrustManagers(), null);

			SSLSocketFactory sslsocketfactory = ctx.getSocketFactory();

			SSLSocket sslsocket = (SSLSocket) sslsocketfactory.createSocket(
					"localhost", port);
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

	public static void main(String[] arstring) {
		new Client().initializeClient(9999);
	}
}
