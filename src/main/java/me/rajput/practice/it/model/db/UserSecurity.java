package me.rajput.practice.it.model.db;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreType;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of="id")
@Entity
@Table(name="USER_SECURITY", schema="TICKETING")
//This is in case it is ever used in controller to protect being exposed.
//Individual properties can be ignored by @JsonIgnoreProperties 
@JsonIgnoreType
@EntityListeners(AuditingEntityListener.class)
public class UserSecurity {
	
	@Id
	//Define no generator to go for default assigned strategy to let application set it.
	//This is the foreign key referenced to User.id
	private Long id;
	
	private String password;
	
	@NotNull
	@CreatedDate
	private LocalDateTime createdAt;
	
	@LastModifiedDate
	private LocalDateTime lastLoginAt;

}
