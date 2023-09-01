package com.fordevs.config;

import com.fordevs.postgresql.entity.InputStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<InputStudent, Long> {
}
