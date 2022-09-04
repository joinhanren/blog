package com.join.vo;

import lombok.Data;


/**
 * @author join
 * @Description
 * @date 2022/9/3 19:58
 */
@Data
public class CommentVo {
    private String id;
    private Long userId;
    //    private String ip;
//    private Integer likes;
//    private Integer forwarding;
    private String parentId;
    private Long articleId;
    private Long toUser;//给谁评论
//    private Long level;
}
