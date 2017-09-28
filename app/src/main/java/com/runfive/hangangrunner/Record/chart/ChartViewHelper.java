package com.runfive.hangangrunner.Record.chart;

import android.graphics.PointF;

public class ChartViewHelper {

    public static void caculateController(PointValue a, PointValue b, PointValue l,PointValue n, PointValue[] controls) {
        /*//
        PointF cLA = center(l, a);
        PointF cAB =  center(a, b);
        PointF cBN =  center(b, n);

        float lenLA =  distance(l, a);
        float lenAB =  distance(a, b);
        float lenBN =  distance(b, n);

        PointF cLAB =  percent(cLA, cAB, lenLA / (lenLA + lenAB));
        PointF cABN =  percent(cAB, cBN, lenAB / (lenAB + lenBN));

        controls[0] =  translate(cAB, a.x - cLAB.x, a.y - cLAB.y);
        controls[1] =  translate(cAB, b.x - cABN.x, b.y - cABN.y);*/

        PointF cLA = center(l, a);
        PointF cAB =  center(a, b);
        PointF cBN =  center(b, n);

        PointValue pointValue = new PointValue(cLA.x,cLA.y);
        PointValue pointValue1 = new PointValue(cAB.x,cAB.y);
        PointF cLAB = center(pointValue,pointValue1);
        float deltaX = (a.x-cLAB.x);
        float deltaY = (a.y-cLAB.y);
        PointF percentOne = percent(cLA, cLAB, 0.4f);
        PointF percentTwo = percent(cAB, cLAB, 0.4f);

        PointValue value = new PointValue(percentOne.x+deltaX,percentOne.y+deltaY);
        controls[0] = new PointValue(value.x,value.y);
        PointValue value1 = new PointValue(percentTwo.x+deltaX,percentTwo.y+deltaY);
        controls[1] = new PointValue(value1.x,value1.y);


        PointValue pointValue2 = new PointValue(cBN.x,cBN.y);
        PointF cABN = center(pointValue1,pointValue2);
        deltaX = (b.x-cABN.x);
        deltaY = (b.y-cABN.y);
        PointF percentThree = percent(cLAB, cABN, 0.4f);
        PointF percentFour = percent(cABN, cABN, 0.4f);

        value = new PointValue(percentThree.x+deltaX,percentThree.y+deltaY);
        controls[2] = new PointValue(value.x,value.y);
         value1 = new PointValue(percentFour.x+deltaX,percentFour.y+deltaY);
        controls[3] = new PointValue(value1.x,value1.y);

    /*
        PointF cBN =  center(b, n);

        float lenLA =  distance(l, a);
        float lenAB =  distance(a, b);
        float lenBN =  distance(b, n);

        PointF cLAB =  percent(cLA, cAB, lenLA / (lenLA + lenAB));
        PointF cABN =  percent(cAB, cBN, lenAB / (lenAB + lenBN));

        controls[0] =  translate(cAB, a.x - cLAB.x, a.y - cLAB.y);
        controls[1] =  translate(cAB, b.x - cABN.x, b.y - cABN.y);*/


    }

    /**
     *
     */

    /**
     *
     */
    public static void caculateControllerPoint(PointValue a, PointValue b, PointValue c, PointValue d, PointValue[] controls) {
        /*//
        PointF cLA = center(l, a);
        PointF cAB =  center(a, b);
        PointF cBN =  center(b, n);

        float lenLA =  distance(l, a);
        float lenAB =  distance(a, b);
        float lenBN =  distance(b, n);

        PointF cLAB =  percent(cLA, cAB, lenLA / (lenLA + lenAB));
        PointF cABN =  percent(cAB, cBN, lenAB / (lenAB + lenBN));

        controls[0] =  translate(cAB, a.x - cLAB.x, a.y - cLAB.y);
        controls[1] =  translate(cAB, b.x - cABN.x, b.y - cABN.y);*/

    }

    public static PointF center(PointValue p1, PointValue p2) {
        return new PointF((p1.x + p2.x) / 2, (p1.y + p2.y) / 2);
    }

    public static float distance(PointValue p1, PointValue p2) {
        float dx = Math.abs(p2.x - p1.x);
        float dy = Math.abs(p2.y - p1.y);
        return (float) Math.hypot(dx, dy);
    }

    public static PointValue translate(PointF p, float x, float y) {
        return new PointValue(p.x + x, p.y + y);
    }

    public static PointF percent(PointF p1, PointF p2, float percent) {
        return percent(p1,percent,p2,percent);
    }

    public static PointF percent(PointF p1,float percent1, PointF p2, float percent2) {
        float x = (p2.x - p1.x) * percent1 + p1.x;
        float y = (p2.y - p1.y) * percent2 + p1.y;
        return new PointF(x, y);
    }
    
}
