package hu.bhr.crm.model;

import java.sql.Timestamp;

public class Customer {

    private String id;
    private String firstName;
    private String lastName;
    private String nickname;
    private String email;
    private String phoneNumber;
    private String relationship;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    private Customer(Builder builder) {
        this.id = builder.id;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.nickname = builder.nickname;
        this.email = builder.email;
        this.phoneNumber = builder.phoneNumber;
        this.relationship = builder.relationship;
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String id;
        private String firstName;
        private String lastName;
        private String nickname;
        private String email;
        private String phoneNumber;
        private String relationship;
        private Timestamp createdAt;
        private Timestamp updatedAt;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder nickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder relationship(String relationship) {
            this.relationship = relationship;
            return this;
        }

        public Builder createdAt(Timestamp createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder updatedAt(Timestamp updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Customer build() {
            return new Customer(this);
        }
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getRelationship() {
        return relationship;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }
}
