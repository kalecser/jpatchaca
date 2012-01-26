package jira.service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class HttpCheckerImpl implements HttpChecker {
    
    public boolean isAddressAvaiable(String address) {
        try {
            return getAddressResponseCode(address) == 200;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            return false;
        }
    }

    private int getAddressResponseCode(String address) throws IOException,
            MalformedURLException, ProtocolException {
        HttpURLConnection connection = (HttpURLConnection) new URL(address).openConnection();
        connection.setRequestMethod("HEAD");
        return connection.getResponseCode();
    }

}
