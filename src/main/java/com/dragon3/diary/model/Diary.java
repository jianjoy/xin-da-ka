package com.dragon3.diary.model;

import com.dragon3.member.model.Member;
import com.dragon3.team.model.Team;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter @Setter
public class Diary {
    @Id
    @Column(length = 32)
    private String id;
    private String text;
    @OrderColumn
    @ElementCollection
    private List<String> picture;
    @Column(length=100)
    private String audio;
    @Column(length=100)
    private String video;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="team_id")
    private Team team;
    private int day;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="location_id")
    private Location location;
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private OpenLevel openLevel;
    private boolean top; //置顶
    private Date time;

    public enum OpenLevel {
        ALL("公开 其他成员可见"),
        MANAGERS ("仅圈主/管理员/点评师可见"),
        ;
        private final String label;
        OpenLevel(String label) {
            this.label = label;
        }
        public String getLabel() {
            return label;
        }
    }
}
