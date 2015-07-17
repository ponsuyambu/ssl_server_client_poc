# ssl_server_client_poc
Understanding of SSLServerSocket, SSLSocket, Keytool, TrustStore etc


### Generate a Java keystore and key pair
`keytool -genkey -alias mydomain -keyalg RSA -keystore sample.jks -storepass 123456`
### Generate a certificate signing request (CSR) for an existing Java keystore
`keytool -certreq -alias mydomain -keystore keystore.jks -storepass password -file mydomain.csr`
### Generate a keystore and self-signed certificate 
`keytool -genkey -keyalg RSA -alias selfsigned -keystore keystore.jks -storepass password -validity 360`
### Import Certificates
### Import a root or intermediate CA certificate to an existing Java keystore
`keytool -import -trustcacerts -alias root -file Thawte.crt -keystore keystore.jks -storepass password`
### Import a signed primary certificate to an existing Java keystore 
`keytool -import -trustcacerts -alias mydomain -file mydomain.crt -keystore keystore.jks -storepass password`
### Export Certificates
###Export a certificate from a keystore
`keytool -export -alias mydomain -file sample.crt -keystore sample.jks -storepass 123456`
###Check/List/View Certificates
###Check a stand-alone certificate
`keytool -printcert -v -file mydomain.crt`
### Check which certificates are in a Java keystore
`keytool -list -v -keystore keystore.jks -storepass password`
### Check a particular keystore entry using an alias
`keytool -list -v -keystore keystore.jks -storepass password -alias mydomain`
### Delete Certificates
###Delete a certificate from a Java Keytool keystore
`keytool -delete -alias mydomain -keystore keystore.jks -storepass password`
### Change Passwords
###Change a Java keystore password
`keytool -storepasswd -new new_storepass -keystore keystore.jks -storepass password`
###Change a private key password
`keytool -keypasswd -alias client -keypass old_password -new new_password -keystore client.jks -storepass password`
