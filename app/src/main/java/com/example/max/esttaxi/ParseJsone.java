package com.example.max.esttaxi;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;

/**
 * Created by max on 5/25/17.
 * Downlod JSON from url and add and create points
 * this url from estTaxi company  http://test.www.estaxi.ru/route.txt
 */
class ParseJsone extends AsyncTask<String, Integer, LinkedList<Point>>{
    Button button;
    LinkedList<Point> pointsFromJson =new LinkedList<Point>();
    Activity activity;
    ProgressDialog pDialog;
    String resultJson = "";

    public ParseJsone(Activity activity){
    this.activity = activity;
        pointsFromJson = new LinkedList<Point>();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(activity);
        pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pDialog.setMax(100);
        pDialog.setMessage(activity.getString(R.string.gettings_ways));
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();
        button = (Button) activity.findViewById(R.id.btn_Download);
        button.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onPostExecute(LinkedList o) {
        super.onPostExecute(o);
        pDialog.dismiss();
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        pDialog.setProgress(progress[0]);
    }

    @Override
    protected LinkedList<Point> doInBackground(String... process) {
        StringBuffer result =new StringBuffer();
        String line,jsonData = "";
        URL url = null;
        try {
            url = new URL(process[0]);
            URLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            BufferedReader jsonReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                while((line = jsonReader.readLine())!=null){
                  result.append(line);
                }
            jsonData= result.toString();
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray jsonArray = jsonObject.getJSONArray("coords");
            for (int i = 0; i<jsonArray.length(); i++) {
                JSONObject jsonPoint  = jsonArray.getJSONObject(i);
                pointsFromJson.add(new Point(jsonPoint.getDouble("la"),jsonPoint.getDouble("lo")));
                int prog = (int) ((i * 100) / jsonArray.length());
                publishProgress(prog);
            }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
            e.printStackTrace();
        }
        return pointsFromJson;
    }
}
