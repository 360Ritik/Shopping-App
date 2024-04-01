package com.Shopping.App.service.repo;

import com.Shopping.App.schema.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
