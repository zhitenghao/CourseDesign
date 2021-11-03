package com.swjt.community.common.Dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PassDto implements Serializable {

	@ApiModelProperty("新密码")
	private String password;

	@ApiModelProperty("旧密码")
	private String currentPass;

}
