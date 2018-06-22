package me.rajput.practice.it.model.db;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.rajput.practice.it.model.IssueStatus;

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
@EntityListeners(AuditingEntityListener.class)
public class Issue {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Size(min = 1, max = 255)
	@Pattern(regexp = "(?i)^((?!<script>.*</script>).)*$") //Must not contain an HTML script tag.
	private String title;
	
	@NotBlank
	@Size(max = 255)
	@Pattern(regexp = "(?i)^((?!<script>.*</script>).)*$") //Must not contain an HTML script tag.
	private String description;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private IssueStatus status;
	
	@NotNull
	@CreatedBy
	private Long reporterId;	//Should use User object by @ManyToOne mapping or not?
	
	private Long assigneeId;  //Should use User object by @ManyToOne mapping or not?
	
	@NotNull
	@CreatedDate
	//Java 8 LocalDateTime supported is for TIMESTAMP, hence @Temporal(TemporalType.TIMESTAMP) is not required.
	private LocalDateTime createdAt;
	
	private LocalDateTime completedAt;

}
