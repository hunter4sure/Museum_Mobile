package com.redeemer.root.museum_mobile.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.redeemer.root.museum_mobile.R;
import com.redeemer.root.museum_mobile.model.Currency;
import com.redeemer.root.museum_mobile.services.CurrencyItemClickListener;

import java.util.List;


public class CurrencyAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<Currency> currencyList;
    private CurrencyItemClickListener currencyItemClickListener;

    public CurrencyAdapter(Context context, List<Currency> currencyList, CurrencyItemClickListener currencyItemClickListener) {
        this.context = context;
        this.currencyList = currencyList;
        this.currencyItemClickListener = currencyItemClickListener;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }



    @Override
    public int getCount() {
        return currencyList.size();
    }

    @Override
    public Object getItem(int position) {
        return currencyList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View currencyItemView = layoutInflater.inflate(R.layout.exchange_rates_layout, null);
        TextView curencyName = (TextView) currencyItemView.findViewById(R.id.currency);
        TextView currencyRate = (TextView) currencyItemView.findViewById(R.id.rate);

        final Currency c = currencyList.get(position);
        curencyName.setText(c.getName());
        currencyRate.setText(Double.toString(c.getRate()));

        currencyItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currencyItemClickListener.onCurrencyItemClick(c);
            }
        });
        return currencyItemView;
    }
}
