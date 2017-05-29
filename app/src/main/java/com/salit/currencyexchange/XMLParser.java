package com.salit.currencyexchange;


import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * Created by salit on 11/04/2017.
 */

public class XMLParser {

    private static String TAG = XMLParser.class.getName();

    public static final String CURRENCIES = "CURRENCIES";
    public static final String CURRENCY = "CURRENCY";
    public static final String LAST_UPDATE = "LAST_UPDATE";
    public static final String NAME = "NAME";
    public static final String UNIT = "UNIT";
    public static final String CURRENCY_CODE = "CURRENCYCODE";
    public static final String COUNTRY = "COUNTRY";
    public static final String RATE = "RATE";
    public static final String CHANGE = "CHANGE";

    private static final String ns = null;

    private static XMLParser instance;

    public static XMLParser getInstance() {
        if(instance == null){
            instance = new XMLParser();
        }
        return instance;
    }

    public CurrencyExchangeData parse(InputStream inputStream) throws XmlPullParserException, IOException
    {
       try {
           XmlPullParser xmlParser = Xml.newPullParser();
           xmlParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
           xmlParser.setInput(inputStream, null);
           xmlParser.nextTag();
           return readCurrencyExchangeData(xmlParser);
       }finally {
           inputStream.close();
       }
    }

    private CurrencyExchangeData readCurrencyExchangeData(XmlPullParser xmlParser) throws IOException, XmlPullParserException {
        List<Currency> currencies = new ArrayList<Currency>();
        Date lastUpdate = null;

        xmlParser.require(XmlPullParser.START_TAG, ns, CURRENCIES);
        while (xmlParser.next() != XmlPullParser.END_TAG){
            if(xmlParser.getEventType() != XmlPullParser.START_TAG){
                continue;
            }

            String name = xmlParser.getName();

            Log.d(TAG, "readCurrencies name = "+name);
            switch (name){
                case CURRENCY:
                    currencies.add(readCurrency(xmlParser));
                    break;
                case LAST_UPDATE:
                    lastUpdate = readLastUpdate(xmlParser);
                    break;
                default:
                    skip(xmlParser);
            }

        }
        CurrencyExchangeData currencyExchangeData = new CurrencyExchangeData();
        currencyExchangeData.setCurrencies(currencies);
        currencyExchangeData.setLastUpdate(lastUpdate);
        return currencyExchangeData;
    }


    private void skip(XmlPullParser xmlParser) throws XmlPullParserException, IOException {
        if (xmlParser.getEventType() != XmlPullParser.START_TAG){
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0){
            switch (xmlParser.next()){
                case XmlPullParser.END_TAG:
                    depth --;
                    break;
                case XmlPullParser.START_TAG:
                    depth ++;
                    break;
            }


        }
    }

    private Date readLastUpdate(XmlPullParser xmlParser) throws IOException, XmlPullParserException {

        String lastUpdate = readText(xmlParser, LAST_UPDATE);
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-M-dd", Locale.ENGLISH);
            Date date = dateFormat.parse(lastUpdate);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    public Currency readCurrency(XmlPullParser xmlParser) throws IOException, XmlPullParserException
    {
        xmlParser.require(XmlPullParser.START_TAG, ns, CURRENCY);

        String currencyName = null;
        int unit = -1;
        String currencyCode = null;
        String country = null;
        double rate = -1;
        double change = -1;


        while(xmlParser.next() != XmlPullParser.END_TAG)
        {
            if(xmlParser.getEventType() != XmlPullParser.START_TAG){
                continue;
            }
            String name = xmlParser.getName();
            Log.d(TAG, "readCurrency name = "+name);
            switch (name){
                case NAME:
                    currencyName = readText(xmlParser, NAME);
                    break;
                case UNIT:
                    unit = readNumber(xmlParser, UNIT).intValue();
                    break;
                case CURRENCY_CODE:
                    currencyCode = readText(xmlParser, CURRENCY_CODE);
                    break;
                case COUNTRY:
                    country = readText(xmlParser, COUNTRY);
                    break;
                case RATE:
                    rate = readNumber(xmlParser, RATE);
                    break;
                case CHANGE:
                    change = readNumber(xmlParser, CHANGE);
                    break;
                default:
                    skip(xmlParser);
            }
        }

        Currency currency = new Currency();
        currency.setName(currencyName);
        currency.setUnit(unit);
        currency.setCurrencyCode(currencyCode);
        currency.setCountry(country);
        currency.setRate(rate);
        currency.setChange(change);
        Log.d(TAG, "readCurrency new currency = "+currency);
        return currency;
    }

    private String readText(XmlPullParser xmlParser, String tagName) throws IOException, XmlPullParserException {
        xmlParser.require(XmlPullParser.START_TAG, ns, tagName);
        String text = null;
        if(xmlParser.next() == XmlPullParser.TEXT){
            text = xmlParser.getText();
            xmlParser.nextTag();
        }
        xmlParser.require(XmlPullParser.END_TAG, ns, tagName);
        Log.d(TAG, "readText text = "+text);
        return text;
    }

    private Double readNumber(XmlPullParser xmlParser, String tagName) throws IOException, XmlPullParserException {
        xmlParser.require(XmlPullParser.START_TAG, ns, tagName);
        Double number = null;
        if(xmlParser.next() == XmlPullParser.TEXT){
            String numberStr = xmlParser.getText();
            number = Double.valueOf(numberStr);
            xmlParser.nextTag();
        }
        xmlParser.require(XmlPullParser.END_TAG, ns, tagName);
        Log.d(TAG, "readNumber number = "+number);

        return number;
    }

    //TODO change this design
    public CurrencyExchangeData getCurrencyExchangeData(URL url){

        CurrencyExchangeData currencyExchangeData = null;

        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                currencyExchangeData = parse(in);
            }
            catch (Exception e)
            {
                Log.e(TAG, "Error e = " +e+"  "+e.getLocalizedMessage());
            }finally {
                urlConnection.disconnect();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return currencyExchangeData;
    }

    /*public static String getDataFromUrl(URL url){
        String xml = null;


        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                //TODO read from inputStream
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                StringBuilder stringBuilder = new StringBuilder();
                String line ;
                while ((line = br.readLine()) != null)
                {
                    stringBuilder.append(line).append('\n');
                }
                xml = stringBuilder.toString();
            }
            catch (Exception e)
            {
                Log.e(TAG, "Error e = " +e+"  "+e.getLocalizedMessage());
            }finally {
                urlConnection.disconnect();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return xml;
    }*/


}
