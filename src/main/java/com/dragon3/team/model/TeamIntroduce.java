package com.dragon3.team.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Getter @Setter
public class TeamIntroduce {
    @Id
    @Column(length = 32)
    private String id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="team_id")
    private Team team;
    @Column(length = 300)
    private String content;
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Type type;
    private int seqNum;

    public enum Type {
        TEXT("文字"),
        PICTURE("图片"),
        AUDIO("音频"),
        VIDEO("视频"),
        ;
        private final String label;
        Type(String label) {
            this.label = label;
        }
        public String getLabel() {
            return label;
        }
    }
}
