package com.bank.dcp.monitoring.collector;

import com.bank.dcp.monitoring.collector.dto.CollectorJobResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/collector/jobs")
@RequiredArgsConstructor
public class CollectorJobController {
    private final CollectorJobService collectorJobService;

    @GetMapping
    public Page<CollectorJobResponse> list(@RequestParam(required = false) UUID sourceId, @PageableDefault(size = 20) Pageable pageable) {
        return collectorJobService.list(sourceId, pageable);
    }
}
