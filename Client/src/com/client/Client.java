package com.client;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Arrays;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

public class Client {
	public static void main(String[] arstring) {
		
		String keyStoreName = "D:\\git\\certificates\\sample.jks";
		String pwd = "123456";
		try {
			String url = Client.class.getResource("sample.crt").getPath();
			System.out.println("Client");
			System.out.println(url);
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			InputStream is = Client.class.getResourceAsStream("sample.crt");
			InputStream caInput = new BufferedInputStream(is);
			Certificate ca;
			try {
				ca = cf.generateCertificate(caInput);
				// System.out.println("ca=" + ((X509Certificate) ca).getSubjectDN());
			} finally {
			}
			
			// Create a KeyStore containing our trusted CAs
			String keyStoreType = KeyStore.getDefaultType();
			KeyStore keyStore = KeyStore.getInstance(keyStoreType);
			keyStore.load(null, null);
			keyStore.setCertificateEntry("ca", ca);
			
			
			/* Create keystore */
	        //KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
	        //keyStore.load(new FileInputStream(url), pwd.toCharArray());

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
