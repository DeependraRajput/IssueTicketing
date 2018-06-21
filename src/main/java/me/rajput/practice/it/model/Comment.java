package me.rajput.practice.it.model.db;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * Description: Data model representing a comment on an issue in the database. 
 * 
 * @author Deependra Rajput
 * @date Jun 14, 2018
 *
 */
@Data
@EqualsAndHashCode(of="id")
@Entity
@Table(name="COMMENT", schema="TICKETING")
public class Comment {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private Long issueId;
	
	@NotBlank
	@Size(max = 255)
	private String text;
	
	@NotNull
	private Long commentator; //Use actual User by @ManyToOne mapping.
	
	@NotNull
	private Date created;

}
