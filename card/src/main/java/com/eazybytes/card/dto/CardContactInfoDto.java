package com.eazybytes.card.dto;



import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@Schema(
        name = "Contacts",
        description = "Schema to hold contact information"
)
@ConfigurationProperties(prefix = "card")
@Getter @Setter
public class CardContactInfoDto{
    private String message;
    private Map<String,String> contactDetails;
    private List<String> onCallSupport;
}
