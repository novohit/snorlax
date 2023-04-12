package com.wyu.snorlax.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 抽取出基类之后的一个问题 查询数据返回的这三个字段为null，但是数据库是有值的
 * 解决方法 @MappedSuperclass声明这是一个Entry的基类 与数据库映射
 *
 * @author novo
 * @since 2023-03-29
 */
@Data
@MappedSuperclass
public class BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date createdAt;

    private Date updatedAt;

    private Date deletedAt;
}
