package me.rajput.practice.it.services;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import me.rajput.practice.it.model.db.Comment;
import me.rajput.practice.it.model.dto.CommentDto;
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
	 * @see me.rajput.practice.it.services.CommentService#writeComment(me.rajput.practice.it.model.Comment)
	 */
	@Override
	public Comment writeComment(Comment comment) {
		comment.setCreatedAt(new Date());
		comment = repository.save(comment);
		LOGGER.info("New comment to issue ["+comment.getIssueId()+"] has been saved");
		return comment;
	}

	/* (non-Javadoc)
	 * @see me.rajput.practice.it.services.CommentService#deleteComment(java.lang.Long)
	 */
	@Override
	public void deleteComment(Long commentId) {
		repository.deleteById(commentId);
		LOGGER.info("Comment with commentId ["+commentId+"] has been deleted");
	}

	/* (non-Javadoc)
	 * @see me.rajput.practice.it.services.CommentService#getCommentDtosByIssue(java.lang.Long, org.springframework.data.domain.Pageable)
	 */
	@Override
	public List<CommentDto> getCommentDtosByIssueId(Long issueId, Pageable pageable) {
		return 		
		this.getCommentsByIssueId(issueId, pageable)
		.stream().map(c -> modelMapper.map(c, CommentDto.class))
		.collect(Collectors.toList());
	}
	
	/* (non-Javadoc)
	 * @see me.rajput.practice.it.services.CommentService#getCommentsByIssue(java.lang.Long, org.springframework.data.domain.Pageable)
	 */
	@Override
	public List<Comment> getCommentsByIssueId(Long issueId, Pageable pageable) {
		List<Comment> comments = repository.findCommentByIssueId(issueId, pageable);
		return comments == null? Collections.emptyList(): comments;
	}


}
