package com.dragon3.diary.model;

import com.dragon3.infrastructure.constants.EnableStatus;
import com.dragon3.member.model.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


@Entity
@Getter @Setter
public class DiaryComment {
    @Id
    @Column(length = 32)
    private String id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="diary_id")
    private Diary diary;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;
    @Column(length=300)
    private String text;
    @Column(length=100)
    private String picture;
    @Column(length=100)
    private String audio;
    @Column(length=100)
    private String video;
    private Date time;
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EnableStatus enableStatus;
}
