package com.example.mindful.api;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.mindful.BuildConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class GeoNameApiHandler {
    private static final String BASE_SEARCH_URL = "https://api.geonames.org/searchJSON";
    private static final String BASE_SEARCH_NEARBY_URL = "https://api.geonames.org/findNearbyPlaceNameJSON";
    private static final String USERNAME = BuildConfig.GEONAMES_API_USERNAME;
    private String TAG = "GeoNameApiHandler";
    private Executor executor = Executors.newSingleThreadExecutor();
    private Handler handler = new Handler(Looper.getMainLooper());
    public void fetchAllUSACities(final OnCitiesFetchedListener listener) {
        disableSSLVerification();
        String requestUrl = BASE_SEARCH_URL + "?country=US&featureCode=PPL&maxRows=20&username=" + USERNAME;

        executor.execute(() -> {
            ArrayList<String> cities = performFetchCities(requestUrl);
            handler.post(() -> {
                if (listener != null) {
                    listener.onCitiesFetched(cities);
                }
            });
        });
    }

    public void fetchAllCitiesNearby(final OnCitiesFetchedListener listener, double[] latLong, double searchRadius){
        disableSSLVerification();
        Log.d(TAG, "fetching nearby cities");
        String requestUrl = BASE_SEARCH_NEARBY_URL + "?lat=" + latLong[0] +
                "&lng=" + latLong[1] + "&radius="+searchRadius+ "&maxRows=20"+ "&username=" + USERNAME;

        executor.execute(() -> {
            ArrayList<String> cities = performFetchCities(requestUrl);
            handler.post(() -> {
                if (listener != null) {
                    listener.onCitiesFetched(cities);
                }else{
                    Log.d(TAG, "no listener for executor");
                }
            });
        });

    }

    private ArrayList<String> performFetchCities(String urlString) {
        ArrayList<String> cities = new ArrayList<>();

        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            InputStream inputStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                Log.d(TAG, line);
                response.append(line);
            }

            cities = parseCitiesResponse(response.toString());

            reader.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cities;
    }

    private ArrayList<String> parseCitiesResponse(String response) {
        ArrayList<String> cities = new ArrayList<>();
        try {
            JSONObject jsonResponse = new JSONObject(response);
            JSONArray citiesArray = jsonResponse.getJSONArray("geonames");

            for (int i = 0; i < citiesArray.length(); i++) {
                JSONObject cityObject = citiesArray.getJSONObject(i);
                String cityName = cityObject.getString("name");
                cities.add(cityName);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return cities;
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


    public interface OnCitiesFetchedListener {
        void onCitiesFetched(ArrayList<String> cities);
    }


}
