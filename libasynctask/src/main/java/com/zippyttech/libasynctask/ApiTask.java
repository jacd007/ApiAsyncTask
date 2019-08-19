package com.zippyttech.libasynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.annotation.NonNull;

import org.json.JSONException;

/**
 * Created by Darwin C on 5/9/19.
 */
public class ApiTask extends AsyncTask<String, String, String> {

    private Context context;
    private ApiCall call;
    private SharedPreferences settings;
    private SharedPreferences.Editor editor;
    private String endpoint;
    private String method;
    private OnTaskCompleted taskComplete;
    private ProgressDialog dialog;
    private String progressMessage;
    private boolean isShownProgress;
    private String body;
    private String url;


    public ApiTask(Context context) {
        this.context = context;
        this.call = new ApiCall(context);
        settings = context.getSharedPreferences(context.getString(R.string.shared_key), 0);
        dialog = new ProgressDialog(context);
        progressMessage = "";
        isShownProgress = false;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (isShownProgress) {
//            dialog.show();

            dialog.setMessage(progressMessage);
            dialog.setIndeterminate(true);

        }
    }

    @Override
    protected String doInBackground(String... strings) {
        if (method != null) {

            String resp = "";
            if (method.toUpperCase().equals("GET"))
                resp = call.callGet(url + endpoint + "");
            else if (method.toUpperCase().equals("POST"))
                resp = call.callPost(url + endpoint, body);
            else if (method.toUpperCase().equals("PUT"))
                resp = call.callPut(url + endpoint, body);
            else if (method.toUpperCase().equals("DELETE"))
                resp = call.callDelete(url + endpoint);

            return resp;
        } else
            return null;
    }


    @Override
    protected void onPostExecute(String response) {
        super.onPostExecute(response);
        dialog.dismiss();
        if (response != null) {
            try {
                taskComplete.onTaskCompleted(response);
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(context, "Format Response Error", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(context, "Not Response", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean isShownProgress() {
        return isShownProgress;
    }

    public void setShownProgress(boolean shownProgress) {
        isShownProgress = shownProgress;
    }

    public String getProgressMessage() {
        return progressMessage;
    }

    public void setProgressMessage(String progressMessage) {
        this.progressMessage = progressMessage;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(@NonNull String endpoint) {
        this.endpoint = endpoint;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(@NonNull String method) {
        this.method = method;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public OnTaskCompleted getTaskComplete() {
        return taskComplete;
    }

    public void setTaskComplete(OnTaskCompleted taskComplete) {
        this.taskComplete = taskComplete;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}


