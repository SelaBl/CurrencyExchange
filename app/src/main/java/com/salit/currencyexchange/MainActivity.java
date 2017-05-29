package com.salit.currencyexchange;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements OnDataChangedListener {

    private static String TAG = MainActivity.class.getName();
    ListView currenciesListView;
    ArrayAdapter<String> currenciesAdapter;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currenciesListView = (ListView) findViewById(R.id.currencies_list_view);
        List<String> currenciesStr = new ArrayList<String>();
        ArrayAdapter<String> currenciesAdapter =
                new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_list_item_1,
                        currenciesStr);
        textView = (TextView) findViewById(R.id.text_view);


        //TODO put it in singelton
        XMLParser xmlParser = new XMLParser();

        try {
            URL url = new URL("http://www.boi.org.il/currency.xml");
            new GetDataFromUrlAsyncTask(this).execute(url);

//            String xml = xmlParser.getDataFromUrl(url);
//            Log.d(TAG, xml);


        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDataChanged(CurrencyExchangeData currencyExchangeData) {

        Log.d(TAG, "data =  " + currencyExchangeData);

        if (currenciesListView != null && currencyExchangeData != null) {

            List<Currency> currencies = currencyExchangeData.getCurrencies();
            List<String> currenciesStr = new ArrayList<String>();
            for (Currency currency : currencies) {
                currenciesStr.add(currency.getCountry());
            }

            Log.d(TAG, "currenciesStr =  " + currenciesStr);
            if(currenciesAdapter != null) {
                currenciesAdapter.clear();
            }
            currenciesAdapter =
                    new ArrayAdapter<String>(this,
                            android.R.layout.simple_list_item_1,
                            currenciesStr);

            currenciesListView.setAdapter(currenciesAdapter);
            currenciesAdapter.notifyDataSetChanged();
        }






    }
}
