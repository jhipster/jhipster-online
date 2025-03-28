package io.github.jhipster.online.web.rest;

import io.github.jhipster.online.repository.UserRepository;
import io.github.jhipster.online.service.dto.UserDTO;
import io.github.jhipster.online.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.online.web.rest.errors.EmailAlreadyUsedException;
import io.github.jhipster.online.web.rest.errors.LoginAlreadyUsedException;

public class CompositeUserValidator {

    public void validate(UserDTO userDTO, UserRepository userRepository) {
        // Validate that the new user doesn't already have an ID.
        if (userDTO.getId() != null) {
            throw new BadRequestAlertException("A new user cannot already have an ID", "userManagement", "idexists");
        }
        // Validate that the login is unique.
        if (userRepository.findOneByLogin(userDTO.getLogin().toLowerCase()).isPresent()) {
            throw new LoginAlreadyUsedException();
        }
        // Validate that the email is unique.
        if (userRepository.findOneByEmailIgnoreCase(userDTO.getEmail()).isPresent()) {
            throw new EmailAlreadyUsedException();
        }
    }
}
