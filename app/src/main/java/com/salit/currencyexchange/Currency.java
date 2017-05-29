package com.salit.currencyexchange;

import org.xmlpull.v1.XmlPullParser;

/**
 * Created by salit on 18/04/2017.
 *
 */

public class Currency {


    String name;
    int unit;
    String currencyCode;
    String country;
    double rate;
    double change;

    public Currency() {
    }

    public Currency(String name, int unit, String currencyCode, String country, double rate, double change) {
        this.name = name;
        this.unit = unit;
        this.currencyCode = currencyCode;
        this.country = country;
        this.rate = rate;
        this.change = change;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getChange() {
        return change;
    }

    public void setChange(double change) {
        this.change = change;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "name='" + name + '\'' +
                ", unit=" + unit +
                ", currencyCode='" + currencyCode + '\'' +
                ", country='" + country + '\'' +
                ", rate=" + rate +
                ", change=" + change +
                '}';
    }


    //xml data
    /*
    <CURRENCY>
        <NAME>Dollar</NAME>
        <UNIT>1</UNIT>
        <CURRENCYCODE>USD</CURRENCYCODE>
        <COUNTRY>USA</COUNTRY>
        <RATE>3.647</RATE>
        <CHANGE>-0.192</CHANGE>
    </CURRENCY>
    */




}
