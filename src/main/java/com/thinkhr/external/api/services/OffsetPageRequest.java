package com.thinkhr.external.api.services;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

public class OffsetPageRequest extends PageRequest{
	private static final long serialVersionUID = 1L;
	
	int offset;
	
	public OffsetPageRequest(int page, int size) {
		super(page, size);
	}
	
	public OffsetPageRequest(int page, int size, Direction direction, String... properties) {
		super(page, size, direction, properties);
	}

	public OffsetPageRequest(int page, int size, Sort sort) {
		super(page, size, sort);
	}

	@Override
	public int getOffset() {
		return offset;
	}
	
	public void setOffset(int offset) {
		this.offset = offset;
	}
}
