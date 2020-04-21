package com.dragon3.member.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


@Entity
@Getter @Setter
public class Letter {
    @Id
    @Column(length = 32)
    private String id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="source_id")
    private Member source;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="target_id")
    private Member target; //source写信给target
    @Column(length = 300)
    private String content;
    private Date time;
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Status status;

    public enum Status {
        UNREAD("未读"),
        HAVE_READ("已读"),
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
