package org.ballcat.sample.authorizationserver.userdetails;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author hccake
 */
@Data
@Accessors(chain = true)
public class SystemUser {

	/**
	 * 用户ID
	 */
	@Schema(title = "用户ID")
	private Integer userId;

	/**
	 * 登录账号
	 */
	@Schema(title = "登录账号")
	private String username;

	/**
	 * 昵称
	 */
	@Schema(title = "昵称")
	private String nickname;

	/**
	 * 密码
	 */
	@Schema(title = "密码")
	private String password;

	/**
	 * 手机号
	 */
	@Schema(title = "手机号")
	private String phoneNumber;

}
