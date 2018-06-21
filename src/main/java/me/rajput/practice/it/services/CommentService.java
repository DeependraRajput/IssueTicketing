package me.rajput.practice.it.services;

import java.util.List;

import me.rajput.practice.it.model.Comment;

/**
 * Description: This interface provides an commenting feature to the application. 
 * 
 * @author Deependra Rajput
 * @date Jun 14, 2018
 *
 */
public interface CommentService {
	
	/**
	 * Add or update a comment to the issue.
	 * @param coment
	 */
	Comment writeComment(Comment comment);
	
	/**
	 * This will delete an existing comment.
	 * @param commentId
	 */
	void deleteComment(Long commentId);
	
	/**
	 * Gets a list of all comments for an issue Id.
	 * @param issueId
	 * @return
	 */
	List<Comment> getCommentsByIssue(Long issueId);

}
