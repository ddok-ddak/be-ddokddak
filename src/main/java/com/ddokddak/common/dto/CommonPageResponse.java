package com.ddokddak.common.dto;

import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Getter
public class CommonPageResponse<T> {
    private List<T> content;
    private boolean first;
    private boolean last;
    private boolean empty;
    private int numberOfElements;
    private long totalElements;
    private int totalPages;

    private int pageNumber;
    private int pageSize;
    private long offset;

    public CommonPageResponse(Page<T> page) {
        Pageable pageable = page.getPageable();

        this.content = page.getContent();
        this.last = page.isLast();
        this.first = page.isFirst();
        this.empty = page.isEmpty();
        this.numberOfElements = page.getNumberOfElements();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();

        this.pageNumber = pageable.getPageNumber();
        this.pageSize = pageable.getPageSize();
        this.offset = pageable.getOffset();
    }
}
