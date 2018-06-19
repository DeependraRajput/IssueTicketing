package me.rajput.practice.it.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import me.rajput.practice.it.model.db.Comment;
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
	
	
	@RequestMapping(path= {"/addComment", "/editComment"}, method=RequestMethod.POST)
	public Comment addComment(@ModelAttribute Comment comment) {
		return commentService.writeComment(comment);
	}
	
	@RequestMapping(path="/getComments", params="issueId")
	public List<Comment> getCommentsByIssue(@RequestParam Long issueId) {
		return commentService.getCommentsByIssue(issueId);
	}
	
	@RequestMapping("/deleteComment")
	public void deleteIssue(Long id) {
		commentService.deleteComment(id);
	}

}
