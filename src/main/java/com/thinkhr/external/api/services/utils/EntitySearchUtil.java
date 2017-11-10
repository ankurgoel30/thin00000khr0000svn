package com.thinkhr.external.api.services.utils;

import static com.thinkhr.external.api.ApplicationConstants.ASCENDING;
import static com.thinkhr.external.api.ApplicationConstants.DEFAULT_LIMIT;
import static com.thinkhr.external.api.ApplicationConstants.DEFAULT_OFFSET;
import static com.thinkhr.external.api.ApplicationConstants.DESENDING;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.thinkhr.external.api.services.OffsetPageRequest;

/**
 * Class is specific to keep utility methods for Entity Search feature. 
 * @author Surabhi Bhawsar
 * @since 2017-11-09
 *
 */
public class EntitySearchUtil {


    /**
     * Get Pageable instance
     * @param offset
     * @param limit
     * @param sortedBy
     * @return
     */
    public static Pageable getPageable(Integer offset,Integer limit,String sortedBy, String defaultSortedBy) {
    	OffsetPageRequest pageable = null;
    	offset = offset == null ? DEFAULT_OFFSET : offset;
    	limit = limit == null ? DEFAULT_LIMIT : limit;
    	sortedBy = StringUtils.isBlank(sortedBy) ? defaultSortedBy : sortedBy;

    	Sort.Direction sortDirection = getSortDirection(sortedBy);
    	
		sortedBy = extractSortDirection(sortedBy, sortDirection); // Extracted out + or - character from sortBy string

		Sort sort = new Sort(sortDirection,sortedBy);
		
    	pageable = new OffsetPageRequest(offset/limit, limit, sort);
    	pageable.setOffset(offset);
    	return pageable;
    }
    
    /**
     * It will extract + or - character those stands for sort direction from column field name
     * @param sortedBy
     * @param sortDirection
     * @return
     */
    public static String extractSortDirection(String sortedBy, Sort.Direction sortDirection) {
    	String directionIndicator = sortedBy.substring(0,1);
		if(directionIndicator.equals(ASCENDING) || directionIndicator.equals(DESENDING) ) {
			sortedBy = sortedBy.substring(1);
		} 
		
		return sortedBy;
	}

	/**
     * Get the first character out from sortedBy value. 
     * like +companyName
     * @param sortedBy
     * @return
     */
    public static Direction getSortDirection(String sortedBy) {
    	String sortDirection =  sortedBy.substring(0,1);
    	return DESENDING.equalsIgnoreCase(sortDirection) ? Direction.DESC : Direction.ASC;
	}
}
