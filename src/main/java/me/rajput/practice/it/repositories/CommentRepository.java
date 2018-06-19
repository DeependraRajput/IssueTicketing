package me.rajput.practice.it.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import me.rajput.practice.it.model.db.Comment;

/**
 * 
 * Description: Repository to access the comments on issues in the database. 
 * 
 * @author Deependra Rajput
 * @date Jun 14, 2018
 *
 */
public interface CommentRepository extends CrudRepository<Comment, Long>{
	
	List<Comment> findCommentByIssue(Long issueId);

}
