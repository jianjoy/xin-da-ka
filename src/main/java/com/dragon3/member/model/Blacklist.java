package com.dragon3.member.model;

import com.dragon3.infrastructure.constants.EnableStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


@Entity
@Getter @Setter
public class Blacklist {
    @Id
    @Column(length = 32)
    private String id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="source_id")
    private Member source;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="target_id")
    private Member target; //source屏蔽了target
    private Date time;
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EnableStatus enableStatus;
}
