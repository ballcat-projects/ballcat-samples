package org.ballcat.sample.customuser.authorizaitonserver.user;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author hccake
 */
@Mapper
public interface AppUserConverter {

	AppUserConverter INSTANCE = Mappers.getMapper(AppUserConverter.class);

	AppUserInfo toAppUserInfo(AppUser appUser);

}
