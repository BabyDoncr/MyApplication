package com.program.cherishtime.network;

import com.program.cherishtime.bean.WeatherData;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherApiClient {
    String END_POINT = "http://api.openweathermap.org/data/2.5/";

    // 查询城市天气
    @GET("weather?lang=zh_cn")
    Observable<WeatherData> getWeatherForCity(@Query("q") String cityName, @Query("APPID") String appId);
}
