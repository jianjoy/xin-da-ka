package com.dragon3.team.model;

import com.dragon3.member.model.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


@Entity
@Getter @Setter
public class JoinRecord {
    @Id
    @Column(length = 32)
    private String id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="team_id")
    private Team team;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;
    private Date time;
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Status status;

    public enum Status {
        WAIT_VERIFY("等待审核"),
        ENABLE("正常"),
        REFUSE("圈主拒绝"),
        EXIT("退出"),
        ;
        private final String label;
        Status(String label) {
            this.label = label;
        }
        public String getLabel() {
            return label;
        }
    }
}
