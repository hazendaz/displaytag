/*
 * Copyright 2002-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.mock.web.portlet;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

/**
 * Mock implementation of the MockActionRequest.
 * 
 * <p>Useful for testing application controllers that access multipart uploads.
 * The {@link org.springframework.mock.web.MockMultipartFile} can be used to
 * populate these mock requests with files.
 *
 * @author Juergen Hoeller
 * @author Arjen Poutsma
 * @see org.springframework.mock.web.MockMultipartFile
 * @since 2.0
 */
public class MockMultipartActionRequest extends MockActionRequest {

	/** The multipart files. */
	private final MultiValueMap<String, MultipartFile> multipartFiles =
			new LinkedMultiValueMap<String, MultipartFile>();


	/**
	 * Add a file to this request. The parameter name from the multipart
	 * form is taken from the {@link org.springframework.web.multipart.MultipartFile#getName()}.
	 * @param file multipart file to be added
	 */
	public void addFile(MultipartFile file) {
		Assertions.assertNotNull(file, "MultipartFile must not be null");
		this.multipartFiles.add(file.getName(), file);
	}

	/**
	 * Gets the file names.
	 *
	 * @return the file names
	 */
	public Iterator<String> getFileNames() {
		return this.multipartFiles.keySet().iterator();
	}

	/**
	 * Gets the file.
	 *
	 * @param name the name
	 * @return the file
	 */
	public MultipartFile getFile(String name) {
		return this.multipartFiles.getFirst(name);
	}

	/**
	 * Gets the files.
	 *
	 * @param name the name
	 * @return the files
	 */
	public List<MultipartFile> getFiles(String name) {
		List<MultipartFile> multipartFiles = this.multipartFiles.get(name);
		if (multipartFiles != null) {
			return multipartFiles;
		}
		else {
			return Collections.emptyList();
		}
	}

	/**
	 * Gets the file map.
	 *
	 * @return the file map
	 */
	public Map<String, MultipartFile> getFileMap() {
		return this.multipartFiles.toSingleValueMap();
	}

	/**
	 * Gets the multi file map.
	 *
	 * @return the multi file map
	 */
	public MultiValueMap<String, MultipartFile> getMultiFileMap() {
		return new LinkedMultiValueMap<String, MultipartFile>(this.multipartFiles);
	}

	/**
	 * Gets the multipart content type.
	 *
	 * @param paramOrFileName the param or file name
	 * @return the multipart content type
	 */
	public String getMultipartContentType(String paramOrFileName) {
		MultipartFile file = getFile(paramOrFileName);
		if (file != null) {
			return file.getContentType();
		}
		else {
			return null;
		}
	}

}
