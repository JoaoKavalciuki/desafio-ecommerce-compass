package com.compass.ecommnerce.events;

import com.compass.ecommnerce.entities.User;
import org.springframework.context.ApplicationEvent;

public class PasswordRecoveryEvent extends ApplicationEvent {
    private String recoveryURL;
    private User user;

    public PasswordRecoveryEvent(String recoveryUrl, User user){
        super(user);
        this.recoveryURL = recoveryUrl;
        this.user = user;

    }

    public String getRecoveryURL() {
        return recoveryURL;
    }

    public void setRecoveryURL(String recoveryURL) {
        this.recoveryURL = recoveryURL;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
