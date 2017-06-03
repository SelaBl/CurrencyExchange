package com.salit.currencyexchange;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by salit on 03/06/2017.
 */

public class CurrencyAdaper extends ArrayAdapter<Currency> {

    private ArrayList<Currency> dataSet;
    private Context mContext;

    private static class ViewHolder{
        TextView currencyName;
        TextView currencyUnit;
        TextView currencyCode;
        TextView currencyCountry;
        TextView currencyRate;
        TextView currencyChange;

    }
    public CurrencyAdaper(ArrayList<Currency> data, Context context) {
        super(context, R.layout.currency_list_item, data);
        dataSet = data;
        mContext = context;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Currency currency = dataSet.get(position);
        ViewHolder viewHolder;
        final View result;
        int lastPosition;

        if(convertView == null)
        {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.currency_list_item,parent, false);
            viewHolder.currencyName = (TextView) convertView.findViewById(R.id.currency_name);
            viewHolder.currencyUnit = (TextView) convertView.findViewById(R.id.currency_unit);
            viewHolder.currencyCode = (TextView) convertView.findViewById(R.id.currency_code);
            viewHolder.currencyCountry = (TextView) convertView.findViewById(R.id.currency_country);
            viewHolder.currencyChange = (TextView) convertView.findViewById(R.id.currency_change);
            viewHolder.currencyRate = (TextView) convertView.findViewById(R.id.currency_rate);

            result = convertView;
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        lastPosition = position;

        viewHolder.currencyName.setText(currency.getName());
        viewHolder.currencyUnit.setText(String.valueOf(currency.getUnit()));
        viewHolder.currencyCode.setText(currency.getCurrencyCode());
        viewHolder.currencyCountry.setText(currency.getCountry());
        viewHolder.currencyChange.setText(String.valueOf(currency.getChange()));
        viewHolder.currencyRate.setText(String.valueOf(currency.getRate()));

        return convertView;
    }
}
