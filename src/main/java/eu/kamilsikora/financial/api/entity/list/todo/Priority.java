package eu.kamilsikora.financial.api.entity.list.todo;

public enum Priority {
    LOW,
    MEDIUM,
    HIGH,
    VERY_HIGH,
    NOT_SPECIFIED;

    public static Priority of(String priority) {
        if (priority == null) {
            return NOT_SPECIFIED;
        }
        return Priority.valueOf(priority.toUpperCase());
    }


}
