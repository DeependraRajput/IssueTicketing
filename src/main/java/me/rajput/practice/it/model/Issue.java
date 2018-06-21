package me.rajput.practice.it.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
@Table(name = "ISSUE")
@EntityListeners(AuditingEntityListener.class)
public class Issue {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Size(min = 1, max = 255)
	@Pattern(regexp = "^((?!<script>.*</script>).)*$") //Must not contain an HTML script tag.
	private String title;
	
	@NotBlank
	@Size(max = 255)
	@Pattern(regexp = "^((?!<script>.*</script>).)*$") //Must not contain an HTML script tag.
	private String description;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private IssueStatus status;
	
	@NotNull
	private Long reporter;	//Should use User object by @ManyToOne mapping or not?
	
	private Long assignee;  //Should use User object by @ManyToOne mapping or not?
	
	@NotNull
	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date completedAt;

}
