package com.damon.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 功能：
 *
 * @author Damon
 * @since 2018-12-17 11:28
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    @NotNull(message = "id不能为空")
    private Integer userId;

    @NotBlank(message = "名称不能为空")
    private String name;

    @NotNull(message = "日期不能为空")
    private Date date;
}
