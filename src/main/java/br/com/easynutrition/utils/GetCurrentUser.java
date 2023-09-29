package br.com.easynutrition.utils;

import br.com.easynutrition.models.Users;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class GetCurrentUser {

    public Users getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return (Users) principal;
        }
        return null;
    }
}
