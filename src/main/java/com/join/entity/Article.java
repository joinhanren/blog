package com.join.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author join
 * @Description
 * @date 2022/8/25 16:04
 */

/**
 * @Getter/@Setter: 作用类上，生成所有成员变量的getter/setter方法；
 * 	作用于成员变量上，生成该成员变量的getter/setter方法。
 *
 * @ToString: 作用于类，覆盖默认的toString()方法
 *
 * @EqualsAndHashCode: 作用于类，覆盖默认的equals和hashCode
 *
 * @NoArgsConstructor：生成无参构造器；
 *
 * @RequiredArgsConstructor：生成包含final和@NonNull注解的成员变量的构造器；
 *
 * @AllArgsConstructor：生成全参构造器
 *
 * @Data: 作用于类上，注解集合，使用它相当于使用下列注解：
 *        @ToString
 *    @EqualsAndHashCode
 *    @Getter
 *    @Setter
 *    @RequiredArgsConstructor
 *
 * @Builder: 作用于类上，将类转变为建造者模式
 *
 * @Log: 作用于类上，生成日志变量

 */
@Data
@TableName("tb_article")
public class Article {
    @TableId
    private Long id;
    private String title;
    private String summary;
    private Integer commentCounts;
    private Integer viewCounts;
    private Long authorId;
    private Long bodyId;
    private Long categoryId;
    private LocalDateTime createDate;
    private Integer weight;

}
