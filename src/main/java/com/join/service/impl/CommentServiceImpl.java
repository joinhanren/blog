package com.join.service.impl;

import com.join.entity.Comment;
import com.join.entity.Result;
import com.join.service.CommentService;
import com.join.service.ThreadService;
import com.join.utils.ThreadLocalUser;
import com.join.vo.CommentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author join
 * @Description 文章评论功能
 * @date 2022/9/3 9:46
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ThreadService threadService;

    /**
     * 根据文章的id查询查询评论,文章父查询
     *
     * @param id
     * @param limit
     * @return
     */
    @Override
    public List<Comment> findCommentById(Long id, Integer limit) {
        Query query = new Query(Criteria.where("articleId").is(id).and("parentId").is("null"));
//        query.addCriteria(Criteria.where("parentId").is("null"));
        query.addCriteria(Criteria.where("deleted").is(0));
        //根据盖楼数进行升序排序
        query.with(Sort.by(Sort.Order.asc("level")));
        query.limit(limit);
        List<Comment> comments = mongoTemplate.find(query, Comment.class);
        return comments;
    }


    /**
     * 根据父评论的id查询子评论,并且根据评论的时间进行降序排序
     *
     * @param parentId
     * @return
     */

    public List<Comment> findChildComment(String parentId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("parentId").is(parentId).and("deleted").is(0));
        //按照评论的时间进行降序排列
        query.with(Sort.by(Sort.Order.desc("dateTime")));
        /**
         * 此处需要用到分页++++++++++++++++++++++++
         */
        query.limit(2);
        List<Comment> comments = mongoTemplate.find(query, Comment.class);
        return comments;
    }

    /**
     * 根据评论的父ID查询该条评论的回复条数
     *
     * @param commentVo
     * @return
     */

//    public Long countReply(CommentVo commentVo) {
//        Query query = new Query();
//        query.addCriteria(Criteria.where("parentId").is(commentVo.getParentId()));
//        query.addCriteria(Criteria.where("deleted").is(0));
//        long count = mongoTemplate.count(query, Long.class);
//        return count;
//    }


    /**
     * 添加一条评论
     *
     * @param comment
     * @return
     */
    @Override
    public Result addComment(Comment comment) {
        comment.setNickName(ThreadLocalUser.get().getNickname());
        comment.setAvatar(ThreadLocalUser.get().getAvatar());
        comment.setUserId(ThreadLocalUser.get().getId());
        comment.setCommentTime(LocalDateTime.now());
        comment.setLikes(0);
        comment.setForwarding(0);
        comment.setReply(0);
        comment.setDeleted(0);
        //判断是不是对文章的评论
        if (comment.getParentId() == null) {
            //对文章进行评论，父id设置为空
            comment.setParentId("null");
            comment.setToUser(0l);
            Query query = new Query();
            query.addCriteria(Criteria.where("parentId").is("null"));
            /**
             * 该处需要优化+++++++++++++++++，降低对mongodb数据库的压力，
             * 解决方法 ：让查询条数和插入只执行一次
             */
            Long count = mongoTemplate.count(query, Comment.class);
            comment.setLevel(count + 1);
            mongoTemplate.insert(comment);
            List<Comment> comments = this.findCommentById(comment.getArticleId(), 1);
            return Result.success(comments);
        }
        mongoTemplate.insert(comment);
        //插入成功后，对父评论数加一
        this.updateReply(comment.getId());
        List<Comment> childComments = this.findChildComment(comment.getParentId());
        return Result.success(childComments);
    }

    /**
     * 查询父评论的回复数
     *
     * @param commentID
     * @return
     */

//    private Integer replyCount(String commentID) {
//        Query query = new Query();
//        query.addCriteria(Criteria.where("_id").is(commentID));
//        //查处父评论的回复数
//        Integer reply = mongoTemplate.findById(commentID, Integer.class, "reply");
//        return reply;
//    }

    /**
     * 对父评论进行回复，回复数增加加一
     *
     * @param commentID
     */

    private void updateReply(String commentID) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(commentID));
        Update update = new Update();
        update.inc("reply");
        mongoTemplate.updateFirst(query, update, Comment.class);
    }

    /**
     * 删除一个评论
     *
     * @param commentVo
     * @return
     */
    @Override
    public Result deleteComment(CommentVo commentVo) {
        /**
         * 只有文章作者和写该条评论的人才可以删除
         */
        Long userID = ThreadLocalUser.get().getId();
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(commentVo.getId()).and("userId").is(userID));

//        query.addCriteria(new Criteria().orOperator(Criteria.where("userId").is(userID)
//                .orOperator(Criteria.where("authorId").is(""))));
//        mongoTemplate.remove(query);

//        Comment comment = mongoTemplate.findOne(query, Comment.class);
//        comment.setDeleted(1);

        Update update = new Update();
//        update.addToSet("deleted", 1);
        update.set("deleted",1);
        mongoTemplate.updateFirst(query, update, Comment.class);
        return Result.success(null);
    }

    /**
     * 点赞评论
     *
     * @param commentID
     * @return
     */
    @Override
    public Result addLikes(String commentID) {
        threadService.addLike(mongoTemplate, commentID);
        return Result.success(null);
    }


}
