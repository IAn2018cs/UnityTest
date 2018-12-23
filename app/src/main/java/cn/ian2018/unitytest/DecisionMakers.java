package cn.ian2018.unitytest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DecisionMakers {
    private final Decision decision;
    private Date christmasTime = null;
    private Date christmasEndTime = null;

    public DecisionMakers(Decision decision) {
        this.decision = decision;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 圣诞节
        try {
            christmasTime = simpleDateFormat.parse("2018-12-25 00:00:00");
            christmasEndTime = simpleDateFormat.parse("2018-12-25 23:59:59");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void doDecision() {
        if (System.currentTimeMillis() >= christmasTime.getTime() && System.currentTimeMillis() <= christmasEndTime.getTime()) {
            decision.christmasing();
        } else if (System.currentTimeMillis() > christmasEndTime.getTime()) {
            decision.christmas();
        } else {
            decision.hide();
        }
    }

    interface Decision {
        void christmas();
        void christmasing();
        void hide();
    }
}
