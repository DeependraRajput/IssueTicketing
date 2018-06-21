package me.rajput.practice.it.model.db;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.rajput.practice.it.model.UserType;

/**
 * 
 * Description: Data model representing a user in the database. 
 * 
 * @author Deependra Rajput
 * @date Jun 14, 2018
 *
 */
@Data
@EqualsAndHashCode(of="id")
@Entity
@Table(name = "USER", schema="TICKETING")
public class User {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    
    @NotNull
    @Column(unique = true)
    @Size(min = 1, max = 10)
	private String loginId;
    
    @NotNull
    @Size(min = 3, max = 25)
    private String email;
    
    @NotNull
    @Size(min = 1, max = 15)
    private String firstName;
    
    @Size(max = 15)
    private String middleName;
    
    @NotNull
    @Size(min = 1, max = 15)
    private String lastName;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    private UserType type;
    
    @NotNull
    private Date updatedAt;

}
