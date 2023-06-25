package com.example.everymutsa.web.common;

import java.time.Instant;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
//다른 엔티티들이 상속받기 위한 기반 클래스
//공유 속성을 모아두는 상위 엔티티임을 나타내는 어노테이션
@EntityListeners(AuditingEntityListener.class)
//Entity의 변화를 지켜보는 클래스 정의
public class BaseEntity {
	@CreatedDate
	@Column(name = "created_at", updatable = false)
	private Instant createdAt;

	@LastModifiedDate
	@Column(name = "updated_at", updatable = true)
	private Instant updatedAt;
}
