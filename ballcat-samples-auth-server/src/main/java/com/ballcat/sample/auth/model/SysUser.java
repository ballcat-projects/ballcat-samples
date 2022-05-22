package com.ballcat.sample.auth.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hccake
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUser{

	private Integer userId;

	private String username;

	private String password;

	private String nickname;
}
