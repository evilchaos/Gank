package com.example.liujiachao.gank.entity;

import io.realm.RealmObject;

/**
 * Created by liujiachao on 2016/10/12.
 */

    public class RealmString extends RealmObject {

        private String val;
        public RealmString() {

        }

        public RealmString(String val) {
            this.val = val;
        }

        public String getVal() {
            return val;
        }

        public void setVal(String val) {
            this.val = val;
        }

    }

