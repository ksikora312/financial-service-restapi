package eu.kamilsikora.financial.api.dto.list;

import lombok.Data;

@Data
public class ResponseListOverview {
    private Long listId;
    private String name;
    private Boolean isPrimary;
}
