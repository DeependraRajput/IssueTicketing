package me.rajput.practice.it.services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import me.rajput.practice.it.domain.Comment;
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
@Slf4j
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private CommentRepository repository;
	
	@Autowired
	private ModelMapper modelMapper;

	/* (non-Javadoc)
	 * @see me.rajput.practice.it.services.CommentService#create(me.rajput.practice.it.model.Comment)
	 */
	@Override
	public Long create(Comment comment) {
		comment.setId(null);
		comment = repository.save(comment);
		LOGGER.info("New comment to issue ["+comment.getIssue().getId()+"] has been saved");
		return comment.getId();
	}
	
	/* (non-Javadoc)
	 * @see me.rajput.practice.it.services.CommentService#update(me.rajput.practice.it.model.Comment)
	 */
	@Override
	public Comment update(Comment comment) {
		Optional<Comment> commentOp = repository.findById(comment.getId());
		Assert.assertTrue("Given comment Id does not exists", commentOp.isPresent());
		comment = repository.save(comment);
		LOGGER.info("New comment to issue [{}] has been saved", comment.getIssue().getId());
		return comment;
	}

	/* (non-Javadoc)
	 * @see me.rajput.practice.it.services.CommentService#deleteById(java.lang.Long)
	 */
	@Override
	public void deleteById(Long id) {
		repository.deleteById(id);
		LOGGER.info("Comment with commentId [{}] has been deleted", id);
	}
	
	/* (non-Javadoc)
	 * @see me.rajput.practice.it.services.CommentService#getCommentsByIssue(java.lang.Long, org.springframework.data.domain.Pageable)
	 */
	@Override
	public List<Comment> getCommentsByIssueId(Long issueId, Pageable pageable) {
		List<Comment> comments = repository.findCommentByIssueId(issueId, pageable);
		return comments == null? Collections.emptyList(): comments;
	}

	/* (non-Javadoc)
	 * @see me.rajput.practice.it.services.CommentService#getById(java.lang.Long)
	 */
	@Override
	public Comment getById(Long id) {
		return repository.findById(id).get();
	}

}
