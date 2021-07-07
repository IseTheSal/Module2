package com.epam.esm.model.entity;

import com.epam.esm.model.entity.audit.AuditEntity;
import com.epam.esm.model.entity.audit.AuditListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

@Entity
@EntityListeners(AuditListener.class)
@Table(name = "users")
public class User extends AuditEntity<User> {

    @Column(name = "login")
    private String login;

    protected User() {
    }

    public User(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        User user = (User) o;

        if (getId() != user.getId()) return false;
        return login != null ? login.equals(user.login) : user.login == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (login != null ? login.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("id=").append(getId());
        sb.append(", login='").append(login).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
