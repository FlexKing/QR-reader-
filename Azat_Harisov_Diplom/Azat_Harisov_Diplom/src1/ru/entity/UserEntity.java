package ru.entity;

import java.util.Objects;

public class UserEntity {
    private Integer id;
    private String email;
    private String name;
    private String password;

    public UserEntity(Integer id, String email, String name, String password) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public UserEntity(String email, String name, String password) {
        this(-1, email, name, password);
    }

    public UserEntity(Integer id, String name, String password) {
        this(id, "", name, password);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            UserEntity that = (UserEntity)o;
            return Objects.equals(this.id, that.id) && Objects.equals(this.email, that.email) && Objects.equals(this.name, that.name) && Objects.equals(this.password, that.password);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.id, this.email, this.name, this.password});
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("UserEntity{");
        sb.append("id=").append(this.id);
        sb.append(", email='").append(this.email).append('\'');
        sb.append(", name='").append(this.name).append('\'');
        sb.append(", password='").append(this.password).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}