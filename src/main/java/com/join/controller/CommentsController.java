package com.join.controller;

import com.join.annotation.LogAnnotation;
import com.join.entity.Comment;
import com.join.entity.Result;
import com.join.service.CommentService;
import com.join.vo.CommentVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author join
 * @Description
 * @date 2022/9/2 22:16
 */
@Api(tags = "文章评论的API")
@RestController
public class CommentsController {

    @Autowired
    private CommentService commentService;

    /**
     * 获取文章的评论列表
     * <p>
     * 此处可能存在问题，前端应该传入的是page页数，不应该是查询的条数，查询的条数应该由后端人员来控制
     *
     * @param id
     * @param limit
     * @return
     */
    @LogAnnotation
    @ApiOperation(value = "获取文章的评论列表", httpMethod = "GET")
    @GetMapping("/comments/{id}/{limit}")
    public Result comments(
            @ApiParam(name = "id", value = "文章的id", required = true) @PathVariable("id") Long id,
            @ApiParam(name = "limit", value = "显示评论的条数", required = true) @PathVariable("limit") Integer limit) {
        List<Comment> comments = commentService.findCommentById(id, limit);
        return Result.success(comments);
    }

    @LogAnnotation
    @ApiOperation(value = "提交一条评论", httpMethod = "POST")
    @PostMapping("/addComment")
    public Result addComment(@RequestBody Comment comment, HttpServletRequest request) {
        String requestRemoteAddr = request.getRemoteAddr();
        comment.setIp(requestRemoteAddr);
        return commentService.addComment(comment);
    }

    @LogAnnotation
    @DeleteMapping("/deleteComment")
    @ApiOperation(value = "删除一条评论", httpMethod = "DELETE")
    public Result deleteComment(@RequestBody CommentVo commentVo) {
        return commentService.deleteComment(commentVo);
    }


    @LogAnnotation
    @ApiOperation(value = "展开该条评论的子评论", httpMethod = "GET")
    @GetMapping("/getChildComment/{parentID}")
    public Result getChildComment(@ApiParam(name = "parentID", value = "该条评论的id")
                                  @PathVariable("parentID") String parentID) {
        List<Comment> childComments = commentService.findChildComment(parentID);
        return Result.success(childComments);
    }


    @LogAnnotation
    @ApiOperation(value = "对评论的点赞", httpMethod = "GET")
    @GetMapping("/addLike/{commentID}")
    public Result addLikes(@ApiParam(name = "commentID", value = "该条评论的id")
                           @PathVariable("commentID") String commentID) {

        return commentService.addLikes(commentID);
    }


}
