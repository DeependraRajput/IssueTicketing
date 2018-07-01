package me.rajput.practice.it.services;

import java.util.List;

import org.springframework.data.domain.Pageable;

import me.rajput.practice.it.domain.Comment;
import me.rajput.practice.it.model.dto.CommentDto;

/**
 * Description: This interface provides an commenting feature to the application. 
 * 
 * @author Deependra Rajput
 * @date Jun 14, 2018
 *
 */
public interface CommentService extends WebEntityService<Comment>{
	
	/**
	 * Gets a list of all comments for an issue Id.
	 * @param issueId
	 * @param pagenation and sorting criteria.
	 * @return
	 */
	List<Comment> getCommentsByIssueId(Long issueId, Pageable pageable);
	
	/**
	 * Gets a list of all comments for an issue Id.
	 * @param issueId
	 * @param pagenation and sorting criteria.
	 * @return
	 */
	List<CommentDto> getCommentDtosByIssueId(Long issueId, Pageable pageable);

}
