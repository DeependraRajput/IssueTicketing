package me.rajput.practice.it.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
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
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
@EntityListeners(AuditingEntityListener.class)
public class Comment {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private Long issueId;
	
	@NotBlank
	@Size(min = 1, max = 4096)
	@Pattern(regexp = "^((?!<script>.*</script>).)*$") //Must not contain an HTML script tag.
	private String text;
	
	@NotNull
	private Long commentator; //Should use actual User by @ManyToOne mapping or not?
	
	@NotNull
	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
	
	@NotNull
	@LastModifiedDate
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastModifiedAt;

}
