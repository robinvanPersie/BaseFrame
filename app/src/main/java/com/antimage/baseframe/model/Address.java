package com.antimage.baseframe.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by xuyuming on 2018/10/31.
 */

@Entity(nameInDb = "T_ADDRESS")
public class Address {

    @Id(autoincrement = true)
    private Long id;

    private String userId;
    private String country;
    private String province;
    private String city;
    private String county;
    @Generated(hash = 2008989642)
    public Address(Long id, String userId, String country, String province,
            String city, String county) {
        this.id = id;
        this.userId = userId;
        this.country = country;
        this.province = province;
        this.city = city;
        this.county = county;
    }
    @Generated(hash = 388317431)
    public Address() {
    }
    public String getUserId() {
        return this.userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getCountry() {
        return this.country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public String getProvince() {
        return this.province;
    }
    public void setProvince(String province) {
        this.province = province;
    }
    public String getCity() {
        return this.city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getCounty() {
        return this.county;
    }
    public void setCounty(String county) {
        this.county = county;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
}
