package com.dragon3.member.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


@Entity
@Getter @Setter
public class Complaint {
    @Id
    @Column(length = 32)
    private String id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="source_id")
    private Member source;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="target_id")
    private Member target; //source举报了target
    @Column(length = 100)
    private String text;
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Reason reason;
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Status status;
    private Date time;

    public enum Reason {
        AD("发布广告/垃圾信息"),
        UNFRIENDLY("不友善"),
        OTHER("其他"),
        ;
        private final String label;
        Reason(String label) {
            this.label = label;
        }
        public String getLabel() {
            return label;
        }
    }
    public enum Status {
        SUBMIT("提交"),
        CONFIRM("受理"),
        CANCEL("驳回"),
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
