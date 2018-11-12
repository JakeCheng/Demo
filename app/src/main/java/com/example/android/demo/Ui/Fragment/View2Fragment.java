package com.example.android.demo.Ui.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.example.android.demo.Adapter.MovieCinameAdapter;
import com.example.android.demo.Adapter.MovieTodayAdapter;
import com.example.android.demo.Base.BaseFragment;
import com.example.android.demo.EventBus.CityChooseEventBus;
import com.example.android.demo.EventBus.PermissionEventBus;
import com.example.android.demo.Model.MovieCinameModel;
import com.example.android.demo.Model.MovieCityModel;
import com.example.android.demo.Model.MovieTodayModel;
import com.example.android.demo.MyView.ExpandTabView.ExpandTabView;
import com.example.android.demo.MyView.ExpandTabView.ViewArea;
import com.example.android.demo.MyView.ExpandTabView.ViewCity;
import com.example.android.demo.MyView.GallyPageTransformer;
import com.example.android.demo.Presenter.MoviePresenter;
import com.example.android.demo.R;
import com.example.android.demo.Ui.Activity.PermissionActivity;
import com.example.android.demo.Utils.ACache;
import com.example.android.demo.Utils.Constants;
import com.example.android.demo.Utils.DateUtils;
import com.example.android.demo.Utils.LocationUtils;
import com.example.android.demo.Utils.Logger;
import com.example.android.demo.Utils.NetWorkUtils;
import com.example.android.demo.Utils.ScreenUtils;
import com.example.android.demo.Utils.SharePreferenceUtil;
import com.example.android.demo.Utils.ToastUtils;
import com.example.android.demo.View.MovieView;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import pub.devrel.easypermissions.EasyPermissions;


public class View2Fragment extends BaseFragment<MoviePresenter> implements MovieView,AMapLocationListener{
    private static final String TAG = "View2Fragment";
    @BindView(R.id.iv_left)
    ImageView iv_left;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.mViewPager)
    ViewPager mViewPager;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.mExpandTabView)
    ExpandTabView mExpandTabView;

    private ArrayList<String> mTextArray = new ArrayList<>();
    private ArrayList<View> mViewArray = new ArrayList<>();

    private ViewCity viewCity;
    private ViewArea viewArea;
    private List<MovieCityModel.ResultBean> listCityDate = new ArrayList<>();

    private ACache mACache;
    private String cache,cityCache,cinemaCache;
    private LinearLayoutManager layoutManager_today;
    private MovieCinameModel cinameModel;
    private List<MovieCinameModel.ResultBean> listCinameDate = new ArrayList<>();
    private MovieCinameAdapter cinameAdapter;
    private MovieTodayModel todayBean;
    private List<MovieTodayModel.ResultBean> listTodayDate = new ArrayList<>();
    private MovieTodayAdapter todayAdapter;
    private int pagerWidth;
    private Intent intent;
    private MovieCityModel cityBean;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    public AMapLocationClientOption mLocationOption;
    private String radius = "0";
    private String latAndLon = "0,0";

    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler(){
        public void dispatchMessage(Message msg) {
            switch (msg.what) {
                //定位完成
                case LocationUtils.MSG_LOCATION_FINISH:
                    AMapLocation loc = (AMapLocation)msg.obj;
                    mLocationClient.stopLocation();
                    String result = LocationUtils.getLocationStr(loc);
                    if (loc.getErrorCode() == 0){
                        Logger.error(TAG, "dispatchMessage: 定位成功   " + loc.getErrorCode());
                        mExpandTabView.setTitle(result,0);
                    }
                    break;
            }
        }
    };
    @Override
    public int getRootViewId() {
        return R.layout.view_2_fragment;
    }

    @Override
    public void onLazyLoad() {
        if (SharePreferenceUtil.getChooseCity(mContext).isEmpty()) {
            getPermission();
        }

        cache = mACache.getAsString(Constants.MovieTodayDate);
        if (!TextUtils.isEmpty(cache)){
            todayBean = new Gson().fromJson(cache,MovieTodayModel.class);
            initShow();
        }else{
            if (NetWorkUtils.isConnected(mContext)){
                mPresenter.initMovieTodayDateGet(SharePreferenceUtil.getCityID(mContext));
            }
        }
    }

    private void initShow() {
        if (todayBean.getError_code() == 0) {
            if (listTodayDate.size()>0){
                listTodayDate.clear();
            }
            listTodayDate.addAll(todayBean.getResult());
            todayAdapter.notifyDataSetChanged();
            cinemaCache = mACache.getAsString(Constants.MovieCinemaDate+SharePreferenceUtil.getCityID(mContext));
            if (cinemaCache != null){
                cinameModel = new Gson().fromJson(cinemaCache,MovieCinameModel.class);
                initCinameShow();
            }else{
                if (NetWorkUtils.isConnected(mContext)){
                    mPresenter.initLocationDateGet(SharePreferenceUtil.getCityName(mContext));
                }
            }
        }else{
            ToastUtils.showPhone(mContext,ScreenUtils.showErrMovie(todayBean.getError_code()),3);
        }
    }

    private void initCinameShow() {
        if (listCinameDate.size()>0){
            listCinameDate.clear();
        }
        listCinameDate.addAll(cinameModel.getResult());
        cinameAdapter.notifyDataSetChanged();
    }


    @Override
    public void initView() {
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }

        mACache = ACache.get(mContext);
        iv_left.setVisibility(View.GONE);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        cinameAdapter = new MovieCinameAdapter(R.layout.item_movie_ciname,listCinameDate);
        mRecyclerView.setAdapter(cinameAdapter);


        tv_title.setText(getResources().getString(R.string.main_tab_movie));
        mLocationClient = new AMapLocationClient(mContext);
        mLocationOption = new AMapLocationClientOption();

        // 设置定位模式为低功耗模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        mLocationOption.setOnceLocation(true);
        // 设置定位监听
        mLocationClient.setLocationListener(this);
        initTodayDate();
    }

    /**
     * 下拉选择框数据
     */
    private void initExpandTabViewDate() {
        viewCity = new ViewCity(mContext);
        mViewArray.add(viewCity);
        mTextArray.add("城市");

        viewArea = new ViewArea(mContext);
        mViewArray.add(viewArea);
        mTextArray.add("周边");

        mExpandTabView.setValue(mTextArray,mViewArray);
        mExpandTabView.setTitle(SharePreferenceUtil.getCityName(mContext),0);
        mExpandTabView.setTitle("周边",1);
        mExpandTabView.setOnButtonClickListener(new ExpandTabView.OnButtonClickListener() {
            @Override
            public void onClick(int selectPosition) {
                switch (selectPosition){
                    case 0:
                        cityCache = mACache.getAsString(Constants.MovieCityDate);
                        if (cityCache != null){
                            cityBean = new Gson().fromJson(cityCache,MovieCityModel.class);
                            listCityDate = cityBean.getResult();
                            viewCity.upDate(listCityDate);
                        }else{
                            mPresenter.initMovieCityDateGet();
                        }
                        break;
                }
            }
        });

    }

    /**
     * 头部今日影片
     */
    private void initTodayDate() {
        layoutManager_today = new LinearLayoutManager(mContext);
        layoutManager_today.setOrientation(LinearLayoutManager.HORIZONTAL);
        mViewPager.setOffscreenPageLimit((listTodayDate == null || listTodayDate.size() == 0) ? 5:listTodayDate.size());
        pagerWidth = (int) (getResources().getDisplayMetrics().widthPixels * 2.0f / 5.0f);
        ViewGroup.LayoutParams lp = mViewPager.getLayoutParams();
        if (lp == null) {
            lp = new ViewGroup.LayoutParams(pagerWidth, ViewGroup.LayoutParams.MATCH_PARENT);
        } else {
            lp.width = pagerWidth;
        }
        mViewPager.setLayoutParams(lp);
        mViewPager.setPageMargin(15);
        mViewPager.setPageTransformer(true, new GallyPageTransformer());
        todayAdapter = new MovieTodayAdapter(mContext,listTodayDate);
        mViewPager.setAdapter(todayAdapter);
    }

    @Override
    public void initPresenter() {
        mPresenter = new MoviePresenter();
    }

    @Override
    public void initData() {
        initExpandTabViewDate();
        initListener();
    }

    private void initListener() {
        viewArea.setOnSelectListener(new ViewArea.OnSelectListener() {
            @Override
            public void getValue(String showText) {
                mExpandTabView.onPressBack();
                int position = getPositon(viewArea);
                if (position >= 0 && !mExpandTabView.getTitle(position).equals(showText)) {
                    if (showText.equals("不限")) showText = "周边";
                    mExpandTabView.setTitle(showText, position);
                }
                radius = showText;
                initMovieCinameGet();
            }
        });
    }

    /**
     * 今日影片信息
     * @param bean
     */
    @Override
    public void onMovieTodayDateGet(MovieTodayModel bean) {
        this.todayBean = bean;
        mACache.put(Constants.MovieTodayDate,new Gson().toJson(bean), DateUtils.getRemainingTime());
        initShow();
    }

    /**
     * 城市选择数据
     * @param bean
     */
    @Override
    public void onMovieCityDateGet(MovieCityModel bean) {
        this.cityBean = bean;
        mACache.put(Constants.MovieCityDate,new Gson().toJson(bean), 60*60*24*365*10);
        listCityDate = bean.getResult();
        viewCity.upDate(listCityDate);
    }

    /**
     * 周边影院
     * @param bean
     */
    @Override
    public void onMovieCinameDateGet(MovieCinameModel bean) {
        this.cinameModel = bean;
        mACache.put(Constants.MovieCinemaDate+SharePreferenceUtil.getCityID(mContext),new Gson().toJson(bean),60*60*24*365*10);
        initCinameShow();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(CityChooseEventBus event) {
        if (event == null) {
            return;
        }
        switch (event.getType()){
            case "1":
                if (event.getId() != null){
                    mExpandTabView.onPressBack();
                    cache = null;
                    cache = mACache.getAsString(Constants.MovieTodayDate);
                    if (!TextUtils.isEmpty(cache)){
                        todayBean = new Gson().fromJson(cache,MovieTodayModel.class);
                        initShow();
                    }else{
                        if (NetWorkUtils.isConnected(mContext)){
                            mPresenter.initMovieTodayDateGet(SharePreferenceUtil.getCityID(mContext));
                        }
                    }
                    mExpandTabView.setTitle(event.getCityName(),0);
                }
                break;
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(PermissionEventBus event) {
        if (event == null) {
            return;
        }
        switch (event.getType()){
            case Constants.View2FragmentToPermissionActivity:
                switch (event.getCode()){
                    case Constants.PermissionOK:
                        mLocationClient.startLocation();
                        break;
                    case Constants.PermissionCancal:
                        ScreenUtils.showPermission(mContext,getResources().getString(R.string.permission_location));
                        break;
                }
                break;
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(String event) {
        if (event == null) {
            return;
        }
        latAndLon = event;
        initMovieCinameGet();

    }

    private void initMovieCinameGet() {
        Log.e(TAG, "onEventMainThread: 打印经纬度："+latAndLon.split(",")[0]+"   "+latAndLon.split(",")[1]);
        mPresenter.initMovieCinameDateGet(latAndLon.split(",")[1],latAndLon.split(",")[0],radius);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
        if (null != mLocationClient) {
			/*
			  如果AMapLocationClient是在当前Activity实例化的，
			  在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
			 */
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (null != aMapLocation) {
            Message msg = mHandler.obtainMessage();
            msg.obj = aMapLocation;
            msg.what = LocationUtils.MSG_LOCATION_FINISH;
            mHandler.sendMessage(msg);
        }
    }
    public void getPermission() {
        if (EasyPermissions.hasPermissions(mContext, Manifest.permission.ACCESS_FINE_LOCATION)){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    mLocationClient.startLocation();
                }
            }).start();
        }else{
            intent = new Intent(mContext, PermissionActivity.class);
            startActivity(intent);
        }
    }
    private int getPositon(View tView) {
        for (int i = 0; i < mViewArray.size(); i++) {
            if (mViewArray.get(i) == tView) {
                return i;
            }
        }
        return -1;
    }
}
