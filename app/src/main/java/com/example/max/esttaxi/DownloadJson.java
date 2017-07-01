package com.example.max.esttaxi;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by max on 5/21/17.
 * Class extends from AsyncTask by connection to URL (params[0]) and download Json file/
 * Just now this class not use in this application
 */

public class DownloadJson extends AsyncTask<String, Integer , File> {
        URL url;
        File file;
        Button button;
        Activity activity;
    ProgressDialog pDialog;
    public DownloadJson(Activity activity){
        this.activity = activity;
    }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(activity);
            Log.d("myLog", "create MainActivity: " + pDialog.hashCode());
            pDialog.setMessage("Downloading file. Please wait...");
            Log.d("myLog", "Диалогу передали сообщение ");
            pDialog.setIndeterminate(false);
            pDialog.setMax(100);
            Log.d("myLog", "Диалогу передали максимальное значение ");
            pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pDialog.setCancelable(true);
            pDialog.show();
            Log.d("myLog", "Показали диалог ");
            button = (Button) activity.findViewById(R.id.btn_Download);
            button.setVisibility(View.INVISIBLE);
            Log.d("myLog", "Установили кнопку невидимой");
        }

     protected void onProgressUpdate(Integer... progress) {
            pDialog.setProgress(progress[0]);
         Log.d("myLog", "Обновили проргресс до "+ progress[0]);
    }

        @Override
        protected File doInBackground(String... params) {
            URLConnection connection;
            Log.d("myLog", "Начало действия background");
            int count=0;
            try {
                url = new URL(params[0]);
                Log.d("myLog", "Создали url");
                Log.d("myLog", "url = : " + url.toString());
                connection = url.openConnection();
                connection.connect();
                Log.d("myLog", "Подсоеденились к url");

                File sdPath = Environment.getExternalStorageDirectory();
                sdPath = new File (sdPath + "/" + "Json");
                sdPath.mkdirs();
                file = new File(sdPath, "Json.txt");
                InputStream is = connection.getInputStream();
                OutputStream os = new FileOutputStream(file);
                Log.d("myLog", "Путь" +file.getPath());
                byte data[] = new byte[1024];
                long total = 0;
                Log.d("myLog", "Читаем");
                int lenghtStream = connection.getContentLength();
                Log.d("myLog", "lenght =  " + lenghtStream);
                while ((count = is.read(data)) != -1) {
                    total += count;
                    int prog = (int) ((total * 100) / lenghtStream);
                    publishProgress(prog);
                    Log.d("myLog", "Отправили на upgrade  progress");
                    os.write(data, 0, count);
                }
                os.flush();
                os.close();
                is.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return file;
        }
        @Override
        protected void onPostExecute(File result) {
            super.onPostExecute(result);
            pDialog.dismiss();
            button.setVisibility(View.VISIBLE);
        }
}

