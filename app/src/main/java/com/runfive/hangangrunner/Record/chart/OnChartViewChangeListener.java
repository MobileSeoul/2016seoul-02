package com.runfive.hangangrunner.Record.chart;

public interface OnChartViewChangeListener {

    public void onChartViewScrolled(int offset);

    public void onChartViewScrollDirection(ScrollDirection scrollDirection);

    public void onItemSelected(int position, Object ject);

    public void onIndicatorClick(int position);
}
