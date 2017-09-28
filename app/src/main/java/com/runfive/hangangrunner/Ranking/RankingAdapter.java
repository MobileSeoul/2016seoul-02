package com.runfive.hangangrunner.Ranking;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JunHo on 2016-07-31.
 */
public class RankingAdapter extends BaseAdapter {

    private Context mContext;
    private int whichCriteria;

    private List<RankingObject> mItems = new ArrayList<RankingObject>();

    public RankingAdapter(Context context, int whichCriteria) {
        mContext = context;
        this.whichCriteria = whichCriteria;
    }

    public void addItem(RankingObject it) {
        mItems.add(it);
    }

    public void setListItems(List<RankingObject> lit) {
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

        /**
         * 리스트뷰 효율적으로 보여주기 위해서 convertView를 이용해서 속도를 높인다
         */
        if(whichCriteria == 1) {
            RankingViewPoint itemView;
            if (convertView == null) {
                itemView = new RankingViewPoint(mContext, mItems.get(position));
            } else {
                itemView = (RankingViewPoint) convertView;
                itemView.setIcon(mItems.get(position).getIcon());
                itemView.setText(0, mItems.get(position).getData(0));
                itemView.setText(1, mItems.get(position).getData(1));
                itemView.setText(2, mItems.get(position).getData(2));
            }
            return itemView;

        } else if (whichCriteria == 2) {
            RankingViewDistance itemView;

            if (convertView == null) {
                itemView = new RankingViewDistance(mContext, mItems.get(position));
            } else {
                itemView = (RankingViewDistance) convertView;
                itemView.setIcon(mItems.get(position).getIcon());
                itemView.setText(0, mItems.get(position).getData(0));
                itemView.setText(1, mItems.get(position).getData(1));
                itemView.setText(2, mItems.get(position).getData(2));
            }
            return itemView;

        } else if(whichCriteria ==3){

            RankingViewMedal itemView;
            if (convertView == null) {
                itemView = new RankingViewMedal(mContext, mItems.get(position));
            } else {
                itemView = (RankingViewMedal) convertView;
                itemView.setIcon(mItems.get(position).getIcon());
                itemView.setText(0, mItems.get(position).getData(0));
                itemView.setText(1, mItems.get(position).getData(1));
                itemView.setText(2, mItems.get(position).getData(2));
            }
            return itemView;
        } else {

        }
        /**
         * 지워야되 널
         */
        return null;
    }
}
