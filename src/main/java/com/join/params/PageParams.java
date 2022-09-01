package com.join.params;

import lombok.Data;

/**
 * @author join
 * @Description
 * @date 2022/8/25 16:26
 */
@Data
public class PageParams {
    private int page=1;
    private int pageSize=10;
}
