package com.eazybytes.loan.audit;

import io.micrometer.common.lang.NonNullApi;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
@NonNullApi
public class AuditorAwareImpl implements AuditorAware<String> {
    /**
     * @return Returns the current auditor in the application
     */
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("LOAN_MS");
    }
}
