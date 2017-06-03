package com.salit.currencyexchange;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements OnDataChangedListener {

    private static String TAG = MainActivity.class.getName();
    ListView currenciesListView;
    CurrencyAdaper currencyAdapter;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currenciesListView = (ListView) findViewById(R.id.currencies_list_view);
        List<String> currenciesStr = new ArrayList<String>();
        currencyAdapter = new CurrencyAdaper(
                new ArrayList<Currency>(),getApplicationContext());

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

            ArrayList<Currency> currencies = new ArrayList<>(currencyExchangeData.getCurrencies());
            View header = getLayoutInflater().inflate(R.layout.currency_list_item, null);
            currenciesListView.addHeaderView(header);

            if(currencyAdapter != null) {
                currencyAdapter.clear();
            }
            currencyAdapter = new CurrencyAdaper(currencies, getApplicationContext());
            currenciesListView.setAdapter(currencyAdapter);
        }
    }
}
