package com.needayea.pay.lianlianpay.JsonUtils;

import java.io.Serializable;
import java.util.List;

public class Root implements Serializable {
    private String code;

    private String region;

    private List<RegionEntitys> regionEntitys ;

    public void setCode(String code){
        this.code = code;
    }
    public String getCode(){
        return this.code;
    }
    public void setRegion(String region){
        this.region = region;
    }
    public String getRegion(){
        return this.region;
    }
    public void setRegionEntitys(List<RegionEntitys> regionEntitys){
        this.regionEntitys = regionEntitys;
    }
    public List<RegionEntitys> getRegionEntitys(){
        return this.regionEntitys;
    }
}
