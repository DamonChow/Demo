package com.damon;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 功能：
 *
 * @author Damon Chow
 * @since 2018/7/19 11:46
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

    private T t;

    private boolean success;

    private String msg;
}
