package org.ballcat.sample.customuser.authorizaitonserver.user;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 用于 token 返回时携带的参数，取消 password 属性。
 * <p>
 * 这里只是一个示例，实际项目中可以根据业务需要返回更多的参数
 *
 * @author hccake
 */
@Data
@Accessors(chain = true)
public class AppUserInfo {

	private Long userId;

	private String username;

	private String nickname;

}
