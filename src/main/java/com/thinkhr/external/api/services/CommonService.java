package com.thinkhr.external.api.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import lombok.Data;

/**
 * Common Service to hold all general operations
 * 
 * @author Surabhi Bhawsar
 * @Since 2017-11-04
 *
 */
@Service
@Data
public class CommonService {

    public static final int DEFAULT_OFFSET = 0;
    public static final int DEFAULT_LIMIT = 50;

    public Pageable getPageable(Integer offset,Integer limit,String sortField) {
    	OffsetPageRequest pageable = null;
    	if (offset == null) {
    		offset = DEFAULT_OFFSET;
    	}
    	
    	if (limit == null) {
    		limit = DEFAULT_LIMIT;
    	}
    	
    	Sort.Direction sortDirection = Sort.Direction.ASC;
    	if (sortField == null || sortField.trim() == "") {
    		sortField = getDefaultSortField();
    	} else {
    		char directionIndicator = sortField.charAt(0);
    		if(directionIndicator == '+') {
    			sortDirection = Sort.Direction.ASC;
    			sortField = sortField.substring(1);
    		} else if (directionIndicator == '-' ) {
    			sortDirection = Sort.Direction.DESC ;
    			sortField = sortField.substring(1);
    		}
    	} 
    	
    	Sort sort = new Sort(sortDirection,sortField);
    	pageable = new OffsetPageRequest(offset/limit, limit,sort);
    	pageable.setOffset(offset);
    	return pageable;
    }
    
    public String getDefaultSortField()  {
    	return null;
    }
   
 
}
