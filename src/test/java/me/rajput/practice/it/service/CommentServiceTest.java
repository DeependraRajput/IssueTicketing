package me.rajput.practice.it.service;

import java.util.List;

import javax.validation.ConstraintViolationException;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.transaction.annotation.Transactional;

import me.rajput.practice.it.TestBase;
import me.rajput.practice.it.model.db.Comment;
import me.rajput.practice.it.model.db.Issue;
import me.rajput.practice.it.services.CommentService;
import me.rajput.practice.it.services.IssueService;

@Transactional
public class CommentServiceTest extends TestBase {

	@Autowired
	private CommentService commentService;
	
	@Autowired
	private IssueService issueService;
	
	/**
	 * Test to add a comment to an issue successfully.
	 */
	@Test
	public void testAddComment() {
		Issue newIssue = new Issue();
		newIssue.setTitle("Test Issue");
		newIssue.setDescription("Test Description");
		
		newIssue = issueService.saveIssue(newIssue);
		
		Comment newComment = new Comment();
		newComment.setCommentatorId(1L);
		newComment.setIssueId(newIssue.getId());
		newComment.setText("Sample Comment 1");
		newComment.setId(null);
		
		newComment = commentService.writeComment(newComment);
		
		Assert.assertNotNull("Comment ID is NULL.", newComment.getId());
		
	}
	
	/**
	 * Test to add an issue with constraint violation.
	 */
	@Test(expected=ConstraintViolationException.class)
	public void testAddCommentWithIllegalArguments() {
		Issue newIssue = new Issue();
		newIssue.setTitle("Test Issue");
		newIssue.setDescription("Test Description");
		
		newIssue = issueService.saveIssue(newIssue);
		
		Comment newComment = new Comment();
		newComment.setId(null);
		
		newComment = commentService.writeComment(newComment);
	}
	
	/**
	 * Test to update a comment successfully.
	 */
	@Test
	public void testUpdateComment() {
		Issue newIssue = new Issue();
		newIssue.setTitle("Test Issue");
		newIssue.setDescription("Test Description");
		
		newIssue = issueService.saveIssue(newIssue);
		
		Comment newComment = new Comment();
		newComment.setCommentatorId(1L);
		newComment.setIssueId(newIssue.getId());
		newComment.setText("Sample Comment 1");
		newComment.setId(null);
		
		newComment = commentService.writeComment(newComment);
		
		Assert.assertNotNull("Comment ID is NULL.", newComment.getId());
		Assert.assertEquals("Comment Text does not match.", "Sample Comment 1", newComment.getText());
		
		newComment.setText("sample Updated text.");
		
		newComment = commentService.writeComment(newComment);
		
		Assert.assertNotNull("Comment ID is NULL.", newComment.getId());
		Assert.assertEquals("Comment Text does not match.", "sample Updated text.", newComment.getText());
	}
	
	/**
	 * Test to delete a comment.
	 */
	@Test
	public void testDeleteComment() {
		Issue newIssue = new Issue();
		newIssue.setTitle("Test Issue");
		newIssue.setDescription("Test Description");
		
		newIssue = issueService.saveIssue(newIssue);
		
		Comment newComment = new Comment();
		newComment.setCommentatorId(1L);
		newComment.setIssueId(newIssue.getId());
		newComment.setText("Sample Comment 1");
		newComment.setId(null);
		
		newComment = commentService.writeComment(newComment);
		
		Assert.assertNotNull("Comment ID is NULL.", newComment.getId());
		
		commentService.deleteComment(newComment.getId());
		
		List<Comment> comments = commentService.getCommentsByIssueId(newIssue.getId(), PageRequest.of(0, 10, Direction.ASC, "createdAt"));
		
		Assert.assertNotNull("Comment ID is NULL.", comments);
		Assert.assertTrue("Comment not deleted", comments.isEmpty());
		
	}
	
	/**
	 * Test to get all comment for an issue.
	 */
	@Test
	public void testGetComments() {
		
		Issue newIssue = new Issue();
		newIssue.setTitle("Test Issue");
		newIssue.setDescription("Test Description");
		
		newIssue = issueService.saveIssue(newIssue);
		
		Comment newComment = new Comment();
		newComment.setCommentatorId(1L);
		newComment.setIssueId(newIssue.getId());
		newComment.setText("Sample Comment 1");
		newComment.setId(null);
		
		newComment = commentService.writeComment(newComment);
		
		List<Comment> comments = commentService.getCommentsByIssueId(newIssue.getId(), PageRequest.of(0, 10, Direction.ASC, "createdAt"));
		Assert.assertNotNull("Comment ID is NULL.", comments);
		Assert.assertEquals("Comment count mismatch", 1, comments.size());
		Assert.assertEquals("Comment mismatch", newComment.getId(), comments.get(0).getId());
		
		
	}
	
}
