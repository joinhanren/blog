package com.join.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

/**
 * @author join
 * @Description
 * @date 2022/9/3 8:44
 */
@Data
@Document("comments")
public class Comment {

    @Id
    private String id;
    @Field("nickName")  //映射的属性
    private String nickName;
    private Long userId;//评论者的id
    private Long authorId;//文章的作者
    private String avatar;
    private String content;
    private LocalDateTime commentTime;
    private String ip;
    private Integer likes;
    private Integer forwarding;
    private String parentId;
    private Long articleId;
    private Long level;
    private Integer deleted;
    private Long toUser;//给谁评论
    private Integer reply;//该条评论的回复数
    private Integer childrenComment;
}
