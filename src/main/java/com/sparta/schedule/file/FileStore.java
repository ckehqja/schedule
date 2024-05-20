package com.sparta.schedule.file;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FileStore {

	@Value("${file.dir}")
	private String fileDir;

	public String getFullPath(String filename) {
		// return "/Users/dobam/Desktop/sparta/upload/" + filename;
		return fileDir + filename;
	}

	public String[] storeFile(MultipartFile multipartFile) throws IOException {
		if (multipartFile.isEmpty()) {
			return null;
		}
		log.info(fileDir);

		String originalFilename = multipartFile.getOriginalFilename();
		String storeFileName = createStoreFileName(originalFilename);
		multipartFile.transferTo(new File(getFullPath(storeFileName)));
		return new String[] {originalFilename, storeFileName};
	}

	//이름이 중복되더라고 uuid로 파일이름을 만드로 중복이 안되게 한다.
	private String createStoreFileName(String originalFilename) {

		String uuid = UUID.randomUUID().toString();
		String ext = extractExt(originalFilename);
		return uuid + "." + ext;
	}

	//확장자를  추출한다
	private String extractExt(String originalFilename) {

		int pos = originalFilename.lastIndexOf(".");
		return originalFilename.substring(pos + 1);
	}
}
