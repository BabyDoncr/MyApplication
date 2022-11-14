package com.program.cherishtime.bean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@SuppressWarnings("unused")
public class WeatherData {
    private static final String DATE_FORMAT = "EEEE, d MMM";  // 日期格式
    private static final int KELVIN_ZERO = 273;  // 开尔文0度
    private static final String FORMAT_TEMPERATURE_CELSIUS = "%d°";

    private String name;
    private Weather[] weather;
    private Main main;

    public String getCityName() {
        return name;
    }

    public String getWeatherDate() {
        return new SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).format(new Date());
    }

    public String getWeatherState() {
        return weather().main;
    }

    public String getWeatherDescription() {
        return weather().description;
    }

    public String getTemperatureCelsius() {
        return String.format(Locale.CHINA, FORMAT_TEMPERATURE_CELSIUS, (int) main.temp - KELVIN_ZERO);
    }

    public String getHumidity() {
        return String.format(Locale.CHINA, "%d%%", main.humidity);
    }

    private Weather weather() {
        return weather[0];
    }

    public void setWeather(Weather[] weather) {
        this.weather = weather;
    }

    private static class Weather {
        private int id;
        private String main;
        private String description;
        private String icon;
    }

    private static class Main {
        private float temp;
        private float feels_like;
        private float temp_min;
        private float temp_max;
        private int pressure;
        private int humidity;
    }
}
