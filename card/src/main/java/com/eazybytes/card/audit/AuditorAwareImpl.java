package com.eazybytes.card.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditorAwareImpl implements AuditorAware<String> {

    /**
     * @return current auditor in the application
     */
    @Override
    public Optional getCurrentAuditor() {
        return Optional.of("CARD_MS");
    }
}
