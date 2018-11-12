package com.example.android.demo.View;

import com.example.android.demo.Base.IBaseView;
import com.example.android.demo.Model.MovieCinameModel;
import com.example.android.demo.Model.MovieCityModel;
import com.example.android.demo.Model.MovieTodayModel;

/**
 * Created by android on 2018/10/17.
 */

public interface MovieView extends IBaseView {
    void onMovieTodayDateGet(MovieTodayModel bean);

    void onMovieCityDateGet(MovieCityModel bean);

    void onMovieCinameDateGet(MovieCinameModel bean);
}
