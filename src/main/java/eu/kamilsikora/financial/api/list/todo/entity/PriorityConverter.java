package eu.kamilsikora.financial.api.list.todo.entity;

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
