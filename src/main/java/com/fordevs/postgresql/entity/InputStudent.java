package com.fordevs.postgresql.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "student")
@Data
public class InputStudent {

	@Id
	private Long id;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	private String email;

	@Column(name = "dept_id")
	private Long deptId;

	@Column(name = "is_active")
	private String isActive;
}
