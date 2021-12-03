package com.your.packages.admin.modules.test;

import com.hccake.ballcat.file.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author hccake
 */
@RequestMapping("/public/test")
@RestController
@RequiredArgsConstructor
public class FileUploadTestController {

	private final FileService fileService;

	@PostMapping("file")
	public String hello(@RequestParam("file") MultipartFile file) throws IOException {
		return fileService.upload(file.getInputStream(), "/test/" + file.getOriginalFilename(), file.getSize());
	}

}
