package com.example.coolweather.gson;

import java.util.List;

public class WeatherBean {
    public List<HeWeathers> HeWeather;

    public List<HeWeathers> getHeWeather() {
        return HeWeather;
    }

    public void setHeWeather(List<HeWeathers> heWeather) {
        HeWeather = heWeather;
    }

    public class HeWeathers{
        public NowBean now;
        public SuggestionBean suggestion;
        public List<Baily> daily_forecast;
        public Update update;
        public Aqi aqi;

        public List<Baily> getDaily_forecast() {
            return daily_forecast;
        }

        public void setDaily_forecast(List<Baily> daily_forecast) {
            this.daily_forecast = daily_forecast;
        }

        public class Update{
            public String loc;

            public String getLoc() {
                return loc;
            }

            public void setLoc(String loc) {
                this.loc = loc;
            }
        }

        public class Aqi{
            public String aqi;
            public String pm25;

            public String getAqi() {
                return aqi;
            }

            public void setAqi(String aqi) {
                this.aqi = aqi;
            }

            public String getPm25() {
                return pm25;
            }

            public void setPm25(String pm25) {
                this.pm25 = pm25;
            }
        }

        //类
        public class NowBean{
            public String tmp;//温度
            public String cond_txt;//天气
            public String wind_dir;//风向
            public String wind_sc;//风级

            public String getTmp() {
                return tmp;
            }

            public void setTmp(String tmp) {
                this.tmp = tmp;
            }

            public String getCond_txt() {
                return cond_txt;
            }

            public void setCond_txt(String cond_txt) {
                this.cond_txt = cond_txt;
            }

            public String getWind_dir() {
                return wind_dir;
            }

            public void setWind_dir(String wind_dir) {
                this.wind_dir = wind_dir;
            }

            public String getWind_sc() {
                return wind_sc;
            }

            public void setWind_sc(String wind_sc) {
                this.wind_sc = wind_sc;
            }
        }

        //类
        public class SuggestionBean{
            public ComfBean comf;
            public SportBean sport;
            public CwBean cw;

            public ComfBean getComf() {
                return comf;
            }

            public void setComf(ComfBean comf) {
                this.comf = comf;
            }

            public SportBean getSport() {
                return sport;
            }

            public void setSport(SportBean sport) {
                this.sport = sport;
            }

            public CwBean getCw() {
                return cw;
            }

            public void setCw(CwBean cw) {
                this.cw = cw;
            }

            //类
            public class ComfBean{
                public String txt;

                public String getTxt() {
                    return txt;
                }

                public void setTxt(String txt) {
                    this.txt = txt;
                }
            }
            //类
            public class SportBean{
                public String txt;

                public String getTxt() {
                    return txt;
                }

                public void setTxt(String txt) {
                    this.txt = txt;
                }
            }
            //类
            public class CwBean{
                public String txt;

                public String getTxt() {
                    return txt;
                }

                public void setTxt(String txt) {
                    this.txt = txt;
                }
            }
        }
        //类 未来日期
        public class Baily{


                public String date;
                public Cond cond;
                public Tmp tmp;

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }

                public class Cond{
                    public String txt_d;

                    public String getTxt_d() {
                        return txt_d;
                    }

                    public void setTxt_d(String txt_d) {
                        this.txt_d = txt_d;
                    }
                }

                public class Tmp{
                    public String max;
                    public String min;

                    public String getMax() {
                        return max;
                    }

                    public void setMax(String max) {
                        this.max = max;
                    }

                    public String getMin() {
                        return min;
                    }

                    public void setMin(String min) {
                        this.min = min;
                    }
                }
        }

    }
}
