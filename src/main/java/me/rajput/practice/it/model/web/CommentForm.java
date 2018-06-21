package me.rajput.practice.it.model.web;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * Description: Data model representing a comment on an issue on the web page. 
 * 
 * @author Deependra Rajput
 * @date Jun 14, 2018
 *
 */
@Data
@EqualsAndHashCode(of="id")
public class CommentForm {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private Long issueId;
	
	@NotBlank
	@Size(max = 4096)
	private String text;
	
	private Long commentator; //Use actual User by @ManyToOne mapping.
	
	private Date created;

}
