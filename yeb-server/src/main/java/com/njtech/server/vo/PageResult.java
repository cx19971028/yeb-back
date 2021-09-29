package com.njtech.server.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 *
 * 数据分页类 采用泛型
 *
 * @author chenxin
 * @date 2021/9/22 16:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult<T> {

    /**
     * 总条数
     */
    private Long totalSize;

    /**
     * 分页数据
     */
    private List<T> data;
}
