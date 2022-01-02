package eu.kamilsikora.financial.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PageDto {
    private int pageNumber;
    private int pageSize;
}
