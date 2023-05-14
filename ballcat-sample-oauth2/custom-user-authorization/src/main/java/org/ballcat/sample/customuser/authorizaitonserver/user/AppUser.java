package org.ballcat.sample.customuser.authorizaitonserver.user;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * C 端用户
 *
 * @author hccake
 */
@Data
@Accessors(chain = true)
public class AppUser {

	private Long userId;

	private String username;

	private String password;

	private String nickname;

}
