package com.voyd.kclexchange;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Handle PHP Requests
 */
public class RequestHandler
{
    public String SendPostRequest(String RequestURL, HashMap<String, String> PostDataParams)
    {
        // Create URL.
        URL URL;

        // StringBuilder object to store the message retrieved from the server.
        StringBuilder SB = new StringBuilder();

        try {
            // Initializing the URL.
            URL = new URL(RequestURL);

            // Creating the HTTP url connection.
            HttpURLConnection Connection = (HttpURLConnection) URL.openConnection();

            // Configuring connection properties.
            Connection.setReadTimeout(15000);
            Connection.setConnectTimeout(15000);
            Connection.setRequestMethod("POST");
            Connection.setDoInput(true);
            Connection.setDoOutput(true);

            // Creating an output stream.
            OutputStream Output = Connection.getOutputStream();

            // Writing parameters to the reuqest.
            // We are using the method GetPostDataString defined below.
            BufferedWriter Writer = new BufferedWriter(new OutputStreamWriter(Output, "UTF-8"));
            Writer.write(GetPostDataString(PostDataParams));

            Writer.flush();
            Writer.close();
            Output.close();
            int ResponseCode = Connection.getResponseCode();

            if (ResponseCode == HttpsURLConnection.HTTP_OK) {
                BufferedReader BR = new BufferedReader(new InputStreamReader(Connection.getInputStream()));
                SB = new StringBuilder();
                String Response;

                // Reading server response.
                while ((Response = BR.readLine()) != null) {
                    SB.append(Response);
                }
            }

        }
        catch (Exception E)
        {
            E.printStackTrace();
        }

        return SB.toString();
    }















    public String sendRequestParam(String RequestURL, String ID)
    {
        StringBuilder SB = new StringBuilder();

        try
        {
            URL URL = new URL(RequestURL + ID);
            HttpURLConnection Connection = (HttpURLConnection) URL.openConnection();
            BufferedReader BufferedReader = new BufferedReader(new InputStreamReader(Connection.getInputStream()));

            String S;
            while((S = BufferedReader.readLine()) != null)
            {
                SB.append(S + "\n");
            }
        }

        catch (Exception E)
        {
            // Nothing...
        }

        return SB.toString();
    }








    public String SendGetRequestParam(String RequestURL, String ID)
    {
        StringBuilder sb =new StringBuilder();
        try
        {
            URL URL = new URL(RequestURL + ID);
            HttpURLConnection Connection = (HttpURLConnection) URL.openConnection();
            BufferedReader BufferedReader = new BufferedReader(new InputStreamReader(Connection.getInputStream()));

            String S;
            while((S = BufferedReader.readLine()) != null)
            {
                sb.append(S + "\n");
            }
        }catch(Exception e)
        {
            // Nothing...
        }

        return sb.toString();
    }









    private String GetPostDataString(HashMap <String, String> Params) throws UnsupportedEncodingException
    {
        StringBuilder Result = new StringBuilder();
        boolean First = true;

        for (Map.Entry <String, String> entry : Params.entrySet())
        {
            if (First)
            {
                First = false;
            }
            else
            {
                Result.append("&");
            }

            Result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            Result.append("=");
            Result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return Result.toString();
    }













}
