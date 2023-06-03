package com.example.mindful.api;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class GeoNameApiHandler {
    private static final String BASE_URL = "https://api.geonames.org/searchJSON";
    private static final String USERNAME = "pettyfoot1"; // Replace with your GeoNames username
    private String TAG = "GeoNameApiHandler";

    public void fetchAllUSACities() {
        Log.d(TAG, "Clicked citites");
        // Call this method before making the API request
        disableSSLVerification();
        String requestUrl = BASE_URL + "?country=US&featureCode=PPL&maxRows=500&username=" + USERNAME;

        new FetchCitiesTask().execute(requestUrl);
    }

    private class FetchCitiesTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... urls) {
            String urlString = urls[0];

            try {
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                InputStream inputStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                parseCitiesResponse(response.toString());

                reader.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        private void parseCitiesResponse(String response) {
            try {
                JSONObject jsonResponse = new JSONObject(response);
                JSONArray citiesArray = jsonResponse.getJSONArray("geonames");

                for (int i = 0; i < citiesArray.length(); i++) {
                    JSONObject cityObject = citiesArray.getJSONObject(i);
                    String cityName = cityObject.getString("name");
                    Log.d("City", cityName);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    // Disable SSL certificate verification
    private void disableSSLVerification() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}
