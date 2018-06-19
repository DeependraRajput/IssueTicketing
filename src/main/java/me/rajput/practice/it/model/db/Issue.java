package me.rajput.practice.it.model.db;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import me.rajput.practice.it.model.IssueStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * Description: Data model representing an issue in the database. 
 * 
 * @author Deependra Rajput
 * @date Jun 14, 2018
 *
 */
@Data
@EqualsAndHashCode(of="id")
@Entity
@Table(name = "ISSUE", schema="TICKETING")
public class Issue {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Size(min = 1, max = 255)
	private String title;
	
	@NotBlank
	@Size(max = 255)
	private String description;
	
	private IssueStatus status;
	
	private Long reporter;
	
	private Long assignee;
	
	private Date created;
	
	private Date completed;

}
