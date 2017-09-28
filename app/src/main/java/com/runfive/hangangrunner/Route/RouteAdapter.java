package com.runfive.hangangrunner.Route;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jinwo on 2016-08-03.
 */
public class RouteAdapter extends BaseAdapter
{
    private Context mContext;
    private List<RouteObject> mItems = new ArrayList<RouteObject>();
    public RouteAdapter(Context context)
    {
        mContext = context;
    }

    public void addItem(RouteObject it)
    {
        mItems.add(it);
    }
    public void setListItems(List<RouteObject> lit) {
        mItems = lit;
    }

    public int getCount() {
        return mItems.size();
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

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        RouteView itemView;
        if (convertView == null) {
            itemView = new RouteView(mContext, mItems.get(position));
        } else {
            itemView = (RouteView) convertView;

            itemView.setIcon(mItems.get(position).getIcon());
            itemView.setText(0, mItems.get(position).getData(0));
            itemView.setText(1, mItems.get(position).getData(1));
            itemView.setText(2, mItems.get(position).getData(2));
        }

        return itemView;
    }

}
