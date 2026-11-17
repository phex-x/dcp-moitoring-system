package com.bank.dcp.monitoring.common;

import com.bank.dcp.monitoring.common.exception.InvalidRequestException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import java.util.Set;

public final class PageableValidator {
    private PageableValidator() {}

    public static Pageable validate(Pageable pageable, String defaultSort, String... allowedSorts) {
        if (pageable.getPageSize() < 1 || pageable.getPageSize() > 100) {
            throw new InvalidRequestException("Page size must be between 1 and 100");
        }
        Set<String> allowed = Set.of(allowedSorts);
        for (Sort.Order order : pageable.getSort()) {
            if (!allowed.contains(order.getProperty())) {
                throw new InvalidRequestException("Unsupported sort field: " + order.getProperty());
            }
        }
        Sort sort = pageable.getSort().isSorted() ? pageable.getSort() : Sort.by(defaultSort).ascending();
        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
    }
}
