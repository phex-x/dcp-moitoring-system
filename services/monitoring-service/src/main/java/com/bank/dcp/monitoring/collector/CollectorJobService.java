package com.bank.dcp.monitoring.collector;

import com.bank.dcp.monitoring.common.PageableValidator;
import com.bank.dcp.monitoring.collector.dto.CollectorJobResponse;
import com.bank.dcp.monitoring.collector.mapper.CollectorJobMapper;
import com.bank.dcp.monitoring.collector.repository.CollectorJobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CollectorJobService {
    private final CollectorJobRepository repository;
    private final CollectorJobMapper mapper;

    public Page<CollectorJobResponse> list(UUID sourceId, Pageable pageable) {
        Pageable safePageable = PageableValidator.validate(
                pageable,
                "startedAt",
                "startedAt",
                "finishedAt",
                "status"
        );

        if (Objects.nonNull(sourceId)) {
            return repository.getAllBySource_Id(sourceId, safePageable).map(mapper::toResponse);
        }

        return repository.findAll(safePageable).map(mapper::toResponse);
    }
}
