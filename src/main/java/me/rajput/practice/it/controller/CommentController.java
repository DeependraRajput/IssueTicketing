package me.rajput.practice.it.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import me.rajput.practice.it.model.db.Comment;
import me.rajput.practice.it.model.dto.CommentDto;
import me.rajput.practice.it.services.CommentService;

/**
 * 
 * Description: MVC Controller to handle all the REST calls for comments.
 * 
 * @author Deependra Rajput
 * @date Jun 14, 2018
 *
 */
@RestController
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@RequestMapping(path= {"/comment/addComment", "/comment/editComment"}, method=RequestMethod.POST)
	public Comment addComment(@Valid @ModelAttribute Comment comment) {
		return commentService.writeComment(comment);
	}
	
	@RequestMapping(path="/comment/getComments", params="issueId")
	public List<CommentDto> getCommentsByIssue(@RequestParam Long issueId, Pageable pageable) {
		return commentService.getCommentDtosByIssueId(issueId, pageable);
	}
	
	@RequestMapping("/comment/deleteComment")
	public void deleteComment(@RequestParam Long id) {
		commentService.deleteComment(id);
	}

}
