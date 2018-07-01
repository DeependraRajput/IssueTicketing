package me.rajput.practice.it.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import me.rajput.practice.it.TestBase;
import me.rajput.practice.it.domain.Comment;
import me.rajput.practice.it.domain.Issue;
import me.rajput.practice.it.domain.User;
import me.rajput.practice.it.services.CommentService;
import me.rajput.practice.it.services.IssueService;
import me.rajput.practice.it.services.UserService;

@Transactional
public class CommentServiceTest extends TestBase {

	@Autowired
	private CommentService commentService;
	
	@Autowired
	private IssueService issueService;
	
	@Autowired
	private UserService userService;
	
	/**
	 * Test to add a comment to an issue successfully.
	 */
	@Test
	public void testAddComment() {
		Issue issue = issueService.getById(1L);
		User user = issue.getReporter();
		
		Comment newComment = new Comment();
		newComment.setCommentator(user);
		newComment.setIssue(issue);
		newComment.setText("Sample Comment 1");
		newComment.setId(null);
		
		Long commentId = commentService.create(newComment);
		
		Assert.assertNotNull("Comment ID is NULL.", commentId);
		Assert.assertEquals("User is not same as expected", user, newComment.getCommentator());
		Assert.assertEquals("Issue is not same as expected", issue, newComment.getIssue());
		
	}
	
//	/**
//	 * Test to add an issue with constraint violation.
//	 */
//	@Test(expected=ConstraintViolationException.class)
//	public void testAddCommentWithIllegalArguments() {
//		Issue newIssue = new Issue();
//		newIssue.setTitle("Test Issue");
//		newIssue.setDescription("Test Description");
//		
//		Long id = issueService.create(newIssue);
//		
//	}
//	
//	/**
//	 * Test to update a comment successfully.
//	 */
//	@Test
//	public void testUpdateComment() {
//		Issue newIssue = new Issue();
//		newIssue.setTitle("Test Issue");
//		newIssue.setDescription("Test Description");
//		
//		newIssue = issueService.saveIssue(newIssue);
//		
//		Comment newComment = new Comment();
//		newComment.setCommentatorId(1L);
//		newComment.setIssueId(newIssue.getId());
//		newComment.setText("Sample Comment 1");
//		newComment.setId(null);
//		
//		newComment = commentService.writeComment(newComment);
//		
//		Assert.assertNotNull("Comment ID is NULL.", newComment.getId());
//		Assert.assertEquals("Comment Text does not match.", "Sample Comment 1", newComment.getText());
//		
//		newComment.setText("sample Updated text.");
//		
//		newComment = commentService.writeComment(newComment);
//		
//		Assert.assertNotNull("Comment ID is NULL.", newComment.getId());
//		Assert.assertEquals("Comment Text does not match.", "sample Updated text.", newComment.getText());
//	}
//	
//	/**
//	 * Test to delete a comment.
//	 */
//	@Test
//	public void testDeleteComment() {
//		Issue newIssue = new Issue();
//		newIssue.setTitle("Test Issue");
//		newIssue.setDescription("Test Description");
//		
//		newIssue = issueService.saveIssue(newIssue);
//		
//		Comment newComment = new Comment();
//		newComment.setCommentatorId(1L);
//		newComment.setIssueId(newIssue.getId());
//		newComment.setText("Sample Comment 1");
//		newComment.setId(null);
//		
//		newComment = commentService.writeComment(newComment);
//		
//		Assert.assertNotNull("Comment ID is NULL.", newComment.getId());
//		
//		commentService.deleteComment(newComment.getId());
//		
//		List<Comment> comments = commentService.getCommentsByIssueId(newIssue.getId(), PageRequest.of(0, 10, Direction.ASC, "createdAt"));
//		
//		Assert.assertNotNull("Comment ID is NULL.", comments);
//		Assert.assertTrue("Comment not deleted", comments.isEmpty());
//		
//	}
//	
//	/**
//	 * Test to get all comment for an issue.
//	 */
//	@Test
//	public void testGetComments() {
//		
//		Issue newIssue = new Issue();
//		newIssue.setTitle("Test Issue");
//		newIssue.setDescription("Test Description");
//		
//		newIssue = issueService.saveIssue(newIssue);
//		
//		Comment newComment = new Comment();
//		newComment.setCommentatorId(1L);
//		newComment.setIssueId(newIssue.getId());
//		newComment.setText("Sample Comment 1");
//		newComment.setId(null);
//		
//		newComment = commentService.writeComment(newComment);
//		
//		List<Comment> comments = commentService.getCommentsByIssueId(newIssue.getId(), PageRequest.of(0, 10, Direction.ASC, "createdAt"));
//		Assert.assertNotNull("Comment ID is NULL.", comments);
//		Assert.assertEquals("Comment count mismatch", 1, comments.size());
//		Assert.assertEquals("Comment mismatch", newComment.getId(), comments.get(0).getId());
//		
//		
//	}
	
}
