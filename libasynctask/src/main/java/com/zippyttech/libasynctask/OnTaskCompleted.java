package com.zippyttech.libasynctask;

import org.json.JSONException;

/**
 * Created by Darwin C on 5/9/19.
 */
public interface OnTaskCompleted {
    void onTaskCompleted(String response) throws JSONException;
}
