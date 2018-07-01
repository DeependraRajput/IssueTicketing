package me.rajput.practice.it.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import me.rajput.practice.it.domain.Comment;

/**
 * 
 * Description: Repository to access the comments on issues in the database. 
 * 
 * @author Deependra Rajput
 * @date Jun 14, 2018
 *
 */
public interface CommentRepository extends CrudRepository<Comment, Long>{
	
	/**
	 * Find all comments for the given issue id.
	 * @param issueId
	 * @param pageable
	 * @return
	 */
	List<Comment> findCommentByIssueId(Long issueId, Pageable pageable);
	
	/**
	 * Delete all comments for the given issue id.
	 * @param issueId
	 */
	void deleteCommentByIssueId(Long issueId);

}
