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
    private String nickName;///昵称
    private Long userId;//评论者的id
    private Long authorId;//文章的作者
    private String avatar;//头像
    private String content;//评论内容
    private LocalDateTime commentTime;//评论的时间
    private String ip;//用户的ip
    private Integer likes;//点赞
    private Integer forwarding;//转发
    private String parentId;//父评论的id
    private Long articleId;//文章的id
    private Long level;//楼层数
    private Integer deleted;//是否删除
    private Long toUser;//给谁评论
    private Integer reply;//该条评论的回复数
//    private Integer childrenComment;//子评论的个数
}
