package com.example.jedi.cryptocurrent;

import android.content.Context;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by jedi on 12/17/2017.
 */

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.CurrencyAdapterViewHolder> implements Serializable {
    Context mContext;
    final private CurrencyAdapterOnClickHandler mClickHandler;
    private Map[] mCurrencyData;
    public CurrencyAdapter (Context context, CurrencyAdapterOnClickHandler clickHandler){
        mContext = context;
        mClickHandler = clickHandler;
    }

    public interface CurrencyAdapterOnClickHandler {
     void onClick(Map thisCurrency);
    }
    @Override
    public CurrencyAdapter.CurrencyAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        return null;

        View view = LayoutInflater.from(mContext).inflate(R.layout.card_list, parent, false);
        view.setFocusable(true);
        return new CurrencyAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CurrencyAdapter.CurrencyAdapterViewHolder holder, int position) {
        Map currencyForThisCountry = mCurrencyData[position];
        holder.conversionCurrency.setText("" + currencyForThisCountry.get("name"));
        holder.curr_price.setText("" + currencyForThisCountry.get("value"));
        holder.curr_time.setText("" + currencyForThisCountry.get("time"));
        holder.curr_date.setText("" + currencyForThisCountry.get("date"));

//        Log.i("CurrencyAdapter","" + currencyForThisCountry.get("name"));
    }

    @Override
    public int getItemCount() {
//        return 0;
        if(mCurrencyData == null){
            return 0;
        }
        return mCurrencyData.length;

    }

    public void swapMap(Map[] newMap){
        if(newMap != null) {
            mCurrencyData = newMap;
            notifyDataSetChanged();
//            for (Map map : mCurrencyData) {
//            Log.i("swapMap", "" + map.size());
//            Log.i("swapMap", map.get("name").toString());
//            }
            }
            else {
            Toast.makeText(mContext, "The swapMap parameter is null", Toast.LENGTH_LONG).show();
        }

    }

   public class CurrencyAdapterViewHolder extends RecyclerView.ViewHolder {
        final TextView conversionCurrency;
        final TextView curr_price;
        final TextView curr_date;
        final TextView curr_time;
       final ImageView share_image;

        public CurrencyAdapterViewHolder(View itemView) {
            super(itemView);
            conversionCurrency = (TextView)itemView.findViewById(R.id.conversionCurrency);
            curr_price = (TextView)itemView.findViewById(R.id.curr_price);
            curr_date = (TextView) itemView.findViewById(R.id.curr_date);
            curr_time = (TextView) itemView.findViewById(R.id.curr_time);
            share_image = (ImageView) itemView.findViewById(R.id.share_image);

            share_image.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    int adapterPosition = getAdapterPosition();
//                    Toast.makeText(mContext, "This is position " + adapterPosition, Toast.LENGTH_LONG).show();
                    Map thisCurrency = mCurrencyData[adapterPosition];
           mClickHandler.onClick(thisCurrency);
                }
            });
        }

   }


}
