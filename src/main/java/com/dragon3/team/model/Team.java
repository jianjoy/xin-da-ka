package com.dragon3.team.model;

import com.dragon3.infrastructure.constants.EnableStatus;
import com.dragon3.member.model.Member;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**圈子*/

@Entity
@Getter @Setter
public class Team {
    @Id
    @Column(length = 32)
    private String id;
    @Column(length = 20)
    private String name;
    @Column(length = 100)
    private String thumb;
    private boolean needVerify;
    @JsonProperty(access= JsonProperty.Access.WRITE_ONLY)
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "team")
    private List<TeamIntroduce> introduces;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="master_id")
    private Member master; //圈主
    @Column(length = 50)
    private String masterIntro;
    @Column(length = 100)
    private String masterWechat;
    private Date createTime;
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EnableStatus enableStatus;
}
