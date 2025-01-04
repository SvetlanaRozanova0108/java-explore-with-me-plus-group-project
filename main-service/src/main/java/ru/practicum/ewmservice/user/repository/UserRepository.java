package ru.practicum.ewmservice.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewmservice.user.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
