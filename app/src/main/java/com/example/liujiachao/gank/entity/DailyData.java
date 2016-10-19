package com.example.liujiachao.gank.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by liujiachao on 2016/10/19.
 */
public class DailyData implements Serializable {
    List<String> category;
    boolean error;
    Map<String,List<NewsItem> > results;

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public Map<String, List<NewsItem>> getResults() {
        return results;
    }

    public void setResults(Map<String, List<NewsItem>> results) {
        this.results = results;
    }
}
