package me.rajput.practice.it.model.web;

import java.util.Date;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.rajput.practice.it.model.IssueStatus;

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
	
	private Long reporter;
	
	private Long assignee;
	
	@NotNull
	private Date createdAt;
	
	@NotNull
	private Date completedAt;

}
