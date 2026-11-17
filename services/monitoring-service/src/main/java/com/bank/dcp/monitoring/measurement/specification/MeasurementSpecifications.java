package com.bank.dcp.monitoring.measurement.specification;

import com.bank.dcp.monitoring.common.Constants;
import com.bank.dcp.monitoring.measurement.dto.MeasurementFilter;
import com.bank.dcp.monitoring.measurement.entity.EnergyMeasurement;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.stream.Stream;

public final class MeasurementSpecifications {
    private MeasurementSpecifications() {}

    public static Specification<EnergyMeasurement> byFilter(MeasurementFilter filter) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.and(
                Stream.of(
                        Objects.nonNull(filter.from())
                                ? criteriaBuilder.greaterThanOrEqualTo(root.<Instant>get(Constants.SortFields.MEASURED_AT), filter.from())
                                : null,
                        Objects.nonNull(filter.to())
                                ? criteriaBuilder.lessThanOrEqualTo(root.<Instant>get(Constants.SortFields.MEASURED_AT), filter.to())
                                : null,
                        Objects.nonNull(filter.dataCenterId())
                                ? criteriaBuilder.equal(root.get(Constants.JpaProperties.SERVER).get(Constants.JpaProperties.RACK).get(Constants.JpaProperties.DATA_CENTER).get(Constants.JpaProperties.ID), filter.dataCenterId())
                                : null,
                        Objects.nonNull(filter.rackId())
                                ? criteriaBuilder.equal(root.get(Constants.JpaProperties.SERVER).get(Constants.JpaProperties.RACK).get(Constants.JpaProperties.ID), filter.rackId())
                                : null,
                        Objects.nonNull(filter.serverId())
                                ? criteriaBuilder.equal(root.get(Constants.JpaProperties.SERVER).get(Constants.JpaProperties.ID), filter.serverId())
                                : null,
                        Objects.nonNull(filter.sourceId())
                                ? criteriaBuilder.equal(root.get(Constants.JpaProperties.SOURCE).get(Constants.JpaProperties.ID), filter.sourceId())
                                : null,
                        Objects.nonNull(filter.minPower())
                                ? criteriaBuilder.greaterThanOrEqualTo(root.<BigDecimal>get(Constants.SortFields.POWER_W), filter.minPower())
                                : null,
                        Objects.nonNull(filter.maxPower())
                                ? criteriaBuilder.lessThanOrEqualTo(root.<BigDecimal>get(Constants.SortFields.POWER_W), filter.maxPower())
                                : null,
                        Objects.nonNull(filter.qualityStatus())
                                ? criteriaBuilder.equal(root.get(Constants.SortFields.QUALITY_STATUS), filter.qualityStatus())
                                : null
                )
                .filter(Objects::nonNull)
                .toArray(Predicate[]::new)
        );
    }
}
