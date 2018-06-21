package me.rajput.practice.it.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.rajput.practice.it.model.Comment;
import me.rajput.practice.it.repositories.CommentRepository;

/**
 * 
 * Description: Implementation of the service to provide access to comments on issues. 
 * 
 * @author Deependra Rajput
 * @date Jun 14, 2018
 *
 */
@Service
public class CommentServiceImpl implements CommentService {
	
	
	@Autowired
	private CommentRepository repository;

	@Override
	public Comment writeComment(Comment comment) {
		comment.setCreatedAt(new Date());
		comment = repository.save(comment);
		return comment;
	}

	@Override
	public void deleteComment(Long commentId) {
		repository.delete(commentId);

	}

	@Override
	public List<Comment> getCommentsByIssue(Long issueId) {
		return repository.findCommentByIssueId(issueId);
	}

}
