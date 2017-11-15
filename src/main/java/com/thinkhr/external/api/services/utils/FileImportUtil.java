package com.thinkhr.external.api.services.utils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.FilenameUtils;

public class FileImportUtil {
	/**
	 * Finds if any required header is missing from given set of headers
	 * 
	 * @param String[] headersInFile
	 * @param String[] requiredHeaders
	 * @return String[] Array of missing headers 
	 */
	public static String[] getMissingHeaders(String[] presentHeaders,String[] requiredHeaders) {
		Set<String> headersInFileSet=  new HashSet<String>(Arrays.asList(presentHeaders));
		Set<String> requiredHeadersSet = new HashSet<String>(Arrays.asList(requiredHeaders));
		requiredHeadersSet.removeAll(headersInFileSet);
		
		String[] missingHeaders = new String[requiredHeadersSet.size()];
		return requiredHeadersSet.toArray(missingHeaders);
	}

	public static boolean hasValidExtension(String fileName,String... validExtention) {
		return FilenameUtils.isExtension(fileName, validExtention);
	}
}
