package com.imaginnovate.emp.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@SuperBuilder(toBuilder = true)
@Getter
@Setter
@MappedSuperclass
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class BaseEntity {

	@Column(name = "is_active")
	private Boolean isActive;

	@Column(name = "created_by")
	private Long createdBy;

	@Column(name = "created_date")
	private LocalDateTime createdDate;

	@Column(name = "modified_by")
	private Long modifiedBy;

	@Column(name = "modified_date")
	private LocalDateTime modifiedDate;

}
