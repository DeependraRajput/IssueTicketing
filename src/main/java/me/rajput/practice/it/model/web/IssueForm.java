package me.rajput.practice.it.model.web;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import me.rajput.practice.it.model.IssueStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * Description: Data model representing an issue on the web page. 
 * 
 * @author Deependra Rajput
 * @date Jun 14, 2018
 *
 */
@Data
@EqualsAndHashCode(of="id")
@Entity
@Table(name = "ISSUE", schema="TICKETING")
public class IssueForm {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Size(min = 1, max = 255)
	private String title;
	
	@NotBlank
	@Size(max = 255)
	private String description;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private IssueStatus status;
	
	private Long reporter;	//Use User object by @ManyToOne mapping.
	
	private Long assignee;  //Use User object by @ManyToOne mapping.
	
	@NotNull
	private Date created;
	
	@NotNull
	private Date completed;

}
