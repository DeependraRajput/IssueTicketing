package me.rajput.practice.it.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of="id")
@Entity
@Table(name="USER_SECURITY")
//This is in case it is ever used in controller to protect being exposed.
@JsonIgnoreProperties({"password", "createdAt", "lastLoginAt"}) 
@EntityListeners(AuditingEntityListener.class)
public class UserSecurity {
	
	@Id
	//Define no generator to go for default assigned strategy to let application set it.
	//This is the foreign key referenced to User.id
	private Long id;
	
	private String password;
	
	@NotNull
	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
	
	private Date lastLoginAt;

}
