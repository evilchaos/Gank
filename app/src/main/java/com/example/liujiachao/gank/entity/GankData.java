package com.example.liujiachao.gank.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liujiachao on 2016/10/12.
 */
public class GankData implements Serializable {
    boolean error;
    List<NewsItem> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<NewsItem> getResults() {
        return results;
    }

    public void setResults(List<NewsItem> results) {
        this.results = results;
    }
}
