package com.bank.dcp.monitoring.common;

import com.bank.dcp.monitoring.common.exception.InvalidRequestException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import java.util.Set;

public final class PageableValidator {
    private PageableValidator() {}

    public static Pageable validate(Pageable pageable, String defaultSort, String... allowedSorts) {
        if (pageable.getPageSize() < Constants.Pagination.MIN_PAGE_SIZE
                || pageable.getPageSize() > Constants.Pagination.MAX_PAGE_SIZE) {
            throw new InvalidRequestException(Constants.Messages.INVALID_PAGE_SIZE);
        }
        Set<String> allowed = Set.of(allowedSorts);
        for (Sort.Order order : pageable.getSort()) {
            if (!allowed.contains(order.getProperty())) {
                throw new InvalidRequestException(Constants.Messages.UNSUPPORTED_SORT_FIELD + order.getProperty());
            }
        }
        Sort sort = pageable.getSort().isSorted() ? pageable.getSort() : Sort.by(defaultSort).ascending();
        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
    }
}
