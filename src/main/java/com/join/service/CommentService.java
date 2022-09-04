package com.join.service;

import com.join.entity.Comment;
import com.join.entity.Result;
import com.join.vo.CommentVo;

import java.util.List;

/**
 * @author join
 * @Description
 * @date 2022/9/3 9:43
 */
public interface CommentService {
    /**
     * 根据文章的id查询查询评论
     * @param id
     * @param limit
     * @return
     */
    public List<Comment> findCommentById(Long id,Integer limit);

    /**
     * 添加一条评论
     * @param comment
     * @return
     */
    public Result addComment(Comment comment);

    /**
     * 根据父评论的id查询子评论,并且根据评论的时间进行降序排序
     * @param parentId
     * @return
     */

    public List<Comment> findChildComment(String parentId);

    /**
     * 删除一条评论
     * @param commentVo
     * @return
     */
    public Result deleteComment(CommentVo commentVo);

    /**
     * 点赞评论
     * @param commentID
     * @return
     */
    public Result addLikes( String commentID);
}
