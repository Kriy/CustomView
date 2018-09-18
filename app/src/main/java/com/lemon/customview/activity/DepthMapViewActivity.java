package com.lemon.customview.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.lemon.customview.DepthMapView;
import com.lemon.customview.R;
import com.lemon.customview.bean.DepthData;
import com.lemon.customview.bean.DepthDataBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DepthMapViewActivity extends AppCompatActivity {

    @BindView(R.id.depth_view)
    DepthMapView mDepthView;
    @BindView(R.id.depth_view1)
    DepthMapView mDepthView1;

    private String jsonData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depthmap_view);
        ButterKnife.bind(this);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Random ran = new Random();
                int Value = ran.nextInt(7) + 1;
                switch (Value) {
                    case 1:
                        jsonData = DepthData.depthA;
                        break;
                    case 2:
                        jsonData = DepthData.depthB;
                        break;
                    case 3:
                        jsonData = DepthData.depthC;
                        break;
                    case 4:
                        jsonData = DepthData.depthD;
                        break;
                    case 5:
                        jsonData = DepthData.depthE;
                        break;
                    case 6:
                        jsonData = DepthData.depthF;
                        break;
                    case 7:
                        jsonData = DepthData.depthG;
                        break;
                }
                newDepth();
                handler.postDelayed(this, 5000);
            }
        }, 1000);
        jsonData = DepthData.depthB;

        new Thread(new Runnable() {
            @Override
            public void run() {
                newDepth();
            }
        }).start();
    }

    private void newDepth() {
        final List<DepthDataBean> listDepthBuy = new ArrayList<>();
        final List<DepthDataBean> listDepthSell = new ArrayList<>();

        final List<DepthDataBean> listDepthBuy1 = new ArrayList<>();
        final List<DepthDataBean> listDepthSell1 = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONObject jsonTick = jsonObject.optJSONObject("tick");
            JSONArray arrBids = jsonTick.optJSONArray("bids");
            JSONArray arrAsks = jsonTick.optJSONArray("asks");

            DepthDataBean obj;
            DepthDataBean obj1;
            String price;
            String volume;
            for (int i = 0; i < arrBids.length(); i++) {
                obj = new DepthDataBean();
                obj1 = new DepthDataBean();
                price = arrBids.getString(i).replace("[", "").replace("]", "").split(",")[0];
                volume = arrBids.getString(i).replace("[", "").replace("]", "").split(",")[1];
                obj.setVolume(Float.valueOf(volume));
                obj.setPrice(Float.valueOf(price));
                obj1.setVolume(Float.valueOf(volume));
                obj1.setPrice(Float.valueOf(price));
                listDepthBuy.add(obj);
                listDepthBuy1.add(obj1);
            }

            for (int i = 0; i < arrAsks.length(); i++) {
                obj = new DepthDataBean();
                obj1 = new DepthDataBean();
                price = arrAsks.getString(i).replace("[", "").replace("]", "").split(",")[0];
                volume = arrAsks.getString(i).replace("[", "").replace("]", "").split(",")[1];
                obj.setVolume(Float.valueOf(volume));
                obj.setPrice(Float.valueOf(price));
                obj1.setVolume(Float.valueOf(volume));
                obj1.setPrice(Float.valueOf(price));
                listDepthSell.add(obj);
                listDepthSell1.add(obj1);
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mDepthView.setData(listDepthBuy, listDepthSell);
                    mDepthView1.setData(listDepthBuy1, listDepthSell1);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
