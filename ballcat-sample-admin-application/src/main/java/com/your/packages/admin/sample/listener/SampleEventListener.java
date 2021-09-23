package com.your.packages.admin.sample.listener;

import com.hccake.ballcat.system.event.UserOrganizationChangeEvent;
import com.your.packages.admin.sample.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author hccake
 */
@Component
@RequiredArgsConstructor
public class SampleEventListener {

	private final DocumentService documentService;

	@EventListener(UserOrganizationChangeEvent.class)
	public void listener(UserOrganizationChangeEvent event) {
		// 更新用户文档的组织id
		documentService.updateUserOrganizationId(event.getUserId(), event.getOriginOrganizationId(),
				event.getCurrentOrganizationId());
	}

}
