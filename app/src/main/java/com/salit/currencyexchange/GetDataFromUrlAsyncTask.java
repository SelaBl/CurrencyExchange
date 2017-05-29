package com.salit.currencyexchange;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by salit on 16/05/2017.
 */

public class GetDataFromUrlAsyncTask  extends AsyncTask<URL, Void, CurrencyExchangeData> {
    public static final String TAG = "GetDataFromUrlAsyncTask";
    public OnDataChangedListener listener;

    public GetDataFromUrlAsyncTask(OnDataChangedListener listener) {
        this.listener = listener;
    }

    @Override
    protected CurrencyExchangeData doInBackground(URL... params) {
        CurrencyExchangeData currencyExchangeData = XMLParser.getInstance().getCurrencyExchangeData(params[0]);
        return currencyExchangeData;
    }


    @Override
    protected void onPostExecute(CurrencyExchangeData currencyExchangeData) {
        Log.d(TAG, "data =  "+currencyExchangeData.toString());
        if(listener != null) {
            listener.onDataChanged(currencyExchangeData);
        }
        super.onPostExecute(currencyExchangeData);
    }

}
