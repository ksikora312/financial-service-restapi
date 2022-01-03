package eu.kamilsikora.financial.api.dto.list;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ResponseListCollectionOverview {
    private List<ResponseListOverview> lists;
}
