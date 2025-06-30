package hu.bhr.crm.model;

import java.sql.Timestamp;
import java.util.UUID;

public record Task(
        UUID id,
        UUID customerId,
        String title,
        String description,
        Timestamp reminder,
        Timestamp dueDate,
        TaskStatus status,
        Timestamp createdAt,
        Timestamp completedAt,
        Timestamp updatedAt
) {
    public static TaskBuilder builder() {
        return new TaskBuilder();
    }

    public static class TaskBuilder {
        private UUID id;
        private UUID customerId;
        private String title;
        private String description;
        private Timestamp reminder;
        private Timestamp dueDate;
        private TaskStatus status;
        private Timestamp createdAt;
        private Timestamp completedAt;
        private Timestamp updatedAt;

        public TaskBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public TaskBuilder customerId(UUID customerId) {
            this.customerId = customerId;
            return this;
        }

        public TaskBuilder title(String title) {
            this.title = title;
            return this;
        }

        public TaskBuilder description(String description) {
            this.description = description;
            return this;
        }

        public TaskBuilder reminder(Timestamp reminder) {
            this.reminder = reminder;
            return this;
        }

        public TaskBuilder dueDate(Timestamp dueDate) {
            this.dueDate = dueDate;
            return this;
        }

        public TaskBuilder status(TaskStatus status) {
            this.status = status;
            return this;
        }

        public TaskBuilder createdAt(Timestamp createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public TaskBuilder completedAt(Timestamp completedAt) {
            this.completedAt = completedAt;
            return this;
        }

        public TaskBuilder updatedAt(Timestamp updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Task build() {
            return new Task(id, customerId, title, description, reminder, dueDate, status, createdAt, completedAt, updatedAt);
        }
    }
}

