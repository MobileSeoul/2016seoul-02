package com.runfive.hangangrunner.Main;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by JunHo on 2016-09-02.
 */
public class WeeklyAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<WeeklyObject> mItems = new ArrayList<WeeklyObject>();
    public WeeklyAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        WeeklyObjectView itemView;
        if (convertView == null) {
            itemView = new WeeklyObjectView(mContext,mItems.get(position));
        } else {
            itemView = (WeeklyObjectView) convertView;
            itemView.setText(mItems.get(position).getData(), mItems.get(position).getDayandmonth(), mItems.get(position).getKm(),
                    mItems.get(position).getPoint(), mItems.get(position).getGold(), mItems.get(position).getSilver(), mItems.get(position).getBronze());
        }
        return itemView;
    }

    public void addItem(WeeklyObject it) {
        mItems.add(it);
    }

    public void setListItems(ArrayList<WeeklyObject> lit) {
        mItems = lit;
    }

    public Object getItem(int position) {
        return mItems.get(position);
    }

    public boolean areAllItemsSelectable() {
        return false;
    }

    public boolean isSelectable(int position) {
        try {
            return mItems.get(position).isSelectable();
        } catch (IndexOutOfBoundsException ex) {
            return false;
        }
    }



}
