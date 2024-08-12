package com.mall.repository;

import com.mall.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TodoRepository extends JpaRepository<UUID, Todo> {
}
