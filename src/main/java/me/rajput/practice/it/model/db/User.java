package me.rajput.practice.it.model.db;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.rajput.practice.it.model.UserType;

/**
 * 
 * Description: Data model representing a user in the database, web and application. 
 * The class must be a business entity not a replica of database table. Use AttributeConverters for custom fields.
 * 
 * @author Deependra Rajput
 * @date Jun 14, 2018
 *
 */
@Data
@EqualsAndHashCode(of="loginId") //Should be of the business equality not database id.
@Entity
@Table(name = "USER", schema="TICKETING")
@EntityListeners(AuditingEntityListener.class)
public class User implements Cloneable {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
	private Long id;
    
    @NotBlank
    @Column(unique = true)
    @Size(min = 1, max = 10)
	@Pattern(regexp = "[a-z]+\\d*")
	private String loginId;
    
    @NotBlank
    @Size(min = 3, max = 25)
    @Email
    private String email;
    
    @NotBlank
    @Size(min = 1, max = 15)
    @Pattern(regexp = "(?U)[\\\\p{Alpha}\\\\-'. ]+")	//Support international names.
    private String firstName;
    
    @Size(max = 15)
    @Pattern(regexp = "(?U)[\\\\p{Alpha}\\\\-'. ]+")
    private String middleName;
    
    @NotBlank
    @Size(min = 1, max = 15)
    @Pattern(regexp = "(?U)[\\\\p{Alpha}\\\\-'. ]+")
    private String lastName;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    private UserType type;
    
    @NotNull
    @LastModifiedDate
    private LocalDateTime updatedAt;
    
    @Override
    public User clone() {
    	try {
			return (User)super.clone();
		} catch (CloneNotSupportedException willNotOccur) {}
		return null;
    }

}
