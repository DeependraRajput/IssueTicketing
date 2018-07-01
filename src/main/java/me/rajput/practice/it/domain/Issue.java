package me.rajput.practice.it.domain;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
import me.rajput.practice.it.model.WebEntity;

/**
 * 
 * Description: Data model representing an issue in the application as per business. 
 * 
 * @author Deependra Rajput
 * @date Jun 14, 2018
 *
 */

@Data
@Entity
@EqualsAndHashCode(of="id")
@Table(name = "ISSUE", schema="TICKETING")
@EntityListeners(AuditingEntityListener.class)
public class Issue implements WebEntity {
	
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
	private IssueStatus status;		//Should be mapped by @Any.
	
	@NotNull
	@CreatedBy
	@ManyToOne
	private User reporter;
	
	@ManyToOne
	private User assignee;
	
	@NotNull
	@CreatedDate
	private LocalDateTime createdAt;
	
	private LocalDateTime completedAt;
	
	//Bi-directional parent-child relationship. For collection, eager fetching is required.
	@OneToMany(mappedBy="issue", fetch = FetchType.EAGER, cascade=CascadeType.REMOVE, orphanRemoval=true)
	private List<Comment> comments;

}
