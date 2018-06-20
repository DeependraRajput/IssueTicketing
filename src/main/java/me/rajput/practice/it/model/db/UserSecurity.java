package me.rajput.practice.it.model.db;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of="id")
@Entity
@Table(name="USER_SECURITY", schema="TICKETING")
public class UserSecurity {
	
	@Id
	private Long id;
	
	private String password;
	
	@NotNull
	private Date createdAt;
	
	private Date lastLoginAt;

}
