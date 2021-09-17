package eu.kamilsikora.financial.api.entity.list.todo;

import javax.persistence.AttributeConverter;

public class PriorityConverter implements AttributeConverter<Priority, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Priority priority) {
        return priority.ordinal();
    }

    @Override
    public Priority convertToEntityAttribute(Integer integer) {
        return Priority.values()[integer];
    }
}
