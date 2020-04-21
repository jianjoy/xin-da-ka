package com.dragon3.member.model;

import com.dragon3.infrastructure.constants.EnableStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


@Entity
@Getter @Setter
public class Member {
    @Id
    @Column(length = 32)
    private String id;
    @Column(length = 100)
    private String nickname;
    @Column(length = 100)
    private String photo;
    @Column(length = 100)
    private String wechat;
    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Sex sex;
    private Date birthday;
//    private String area;
    private Date registerTime;
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EnableStatus enableStatus;

    public enum Sex {
        MALE("男"), FEMALE("女");
        String label;
        Sex(String label) {
            this.label = label;
        }
        public String getLabel() {
            return label;
        }
    }
}
