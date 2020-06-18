/*
 * Copyright (c) 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package codes.recursive.cnms.user;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.GeneralSecurityException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.logging.LogManager;

import com.squareup.okhttp.OkHttpClient;
import io.helidon.common.configurable.Resource;
import io.helidon.common.pki.KeyConfig;
import io.helidon.config.Config;
import io.helidon.microprofile.server.Server;
import io.netty.handler.ssl.ApplicationProtocolConfig;

import javax.net.ssl.*;


/**
 * The application main class.
 */

public final class Main {

    /**
     * Cannot be instantiated.
     */
    private Main() {
    }

    /**
     * Application main entry point.
     * @param args command line arguments
     * @throws IOException if there are problems reading logging properties
     */
    public static void main(final String[] args) throws IOException {
        // load logging configuration
        setupLogging();
        try {
            trustCert();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }

        Server server = startServer();

        System.out.println("http://localhost:" + server.port() + "/greet");
        //SimpleHttpsServer httpsServer = new SimpleHttpsServer();
        //httpsServer.Start(8080);
        //System.out.println(httpsServer.getPath());
    }

    /**
     * Start the server.
     * @return the created {@link Server} instance
     */
    static Server startServer() {
        // Server will automatically pick up configuration from
        // microprofile-config.properties
        // and Application classes annotated as @ApplicationScoped
        //Config config = Config.create();
        //return Server.builder().config(config).build().start();
        return Server.create().start();


    }

    /**
     * Configure logging from logging.properties file.
     */
    private static void setupLogging() throws IOException {
        try (InputStream is = Main.class.getResourceAsStream("/logging.properties")) {
            LogManager.getLogManager().readConfiguration(is);
        }
    }

    private static void trustCert() throws NoSuchAlgorithmException, KeyManagementException, IOException {
        TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }
            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        }
        };

        // Install the all-trusting trust manager
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        // Create all-trusting host name verifier
        HostnameVerifier allHostsValid = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };

        // Install the all-trusting host verifier
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

        URL url = new URL("https://www.nakov.com:2083/");
        URLConnection con = url.openConnection();
        Reader reader = new InputStreamReader(con.getInputStream());
        while (true) {
            int ch = reader.read();
            if (ch==-1) {
                break;
            }
            System.out.print((char)ch);
        }
    }

}