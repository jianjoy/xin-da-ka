package com.dragon3.diary.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class Location {
    @Id
    @Column(length = 32)
    private String id;
    private String name; //位置名称
    private String address; //详细地址
    private double latitude; //纬度
    private double longitude; //经度
}
