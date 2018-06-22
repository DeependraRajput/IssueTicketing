package me.rajput.practice.it.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.transaction.annotation.Transactional;

import me.rajput.practice.it.TestBase;
import me.rajput.practice.it.model.IssueStatus;
import me.rajput.practice.it.model.db.Issue;
import me.rajput.practice.it.repositories.IssueRepository;
import me.rajput.practice.it.services.IssueService;

/**
 * 
 * Description: Test class for testing IssueService. 
 * 
 * @author Deependra Rajput
 * @date Jun 22, 2018
 *
 */
@Transactional
public class IssueServiceTest extends TestBase {
	
	@Autowired
	private IssueService service;
	
	@Autowired
	private IssueRepository repository;
	
	@Before
	public void initialiseData() {
		Issue issue = new Issue();
		issue.setReporterId(1L);
		issue.setAssigneeId(1L);
		issue.setTitle("Test Ticket 1");
		issue.setDescription("Description One");
		issue = service.saveIssue(issue);
		
		issue = new Issue();
		issue.setReporterId(1L);
		issue.setAssigneeId(2L);
		issue.setTitle("Test Ticket 2");
		issue.setDescription("Description Two");
		issue = service.saveIssue(issue);
		
		issue = new Issue();
		issue.setReporterId(1L);
		issue.setAssigneeId(1L);
		issue.setTitle("Test Ticket 3");
		issue.setDescription("Description Three");
		issue = service.saveIssue(issue);
		
		issue = new Issue();
		issue.setReporterId(1L);
		issue.setAssigneeId(2L);
		issue.setTitle("Test Ticket 4");
		issue.setDescription("Description Four");
		issue = service.saveIssue(issue);
	}
	
	/**
	 * Test to create an issue successfully.
	 */
	@Test
	public void testCreateIssue() {
		Issue issue = new Issue();
		issue.setReporterId(1L);
		issue.setTitle("Test Title");
		issue.setDescription("Test Description");
		issue = service.saveIssue(issue);
		
		Assert.assertNotNull("Issue Id returned is null", issue.getId());
		Assert.assertNotNull("Issue status returned is null", issue.getStatus());
		Assert.assertNotNull("Issue created date returned is null", issue.getCreatedAt());
		Assert.assertEquals("Issue Title does not match", "Test Title", issue.getTitle());
		Assert.assertEquals("Issue Description does not match", "Test Description", issue.getDescription());
	}
	
	/**
	 * Test to create an issue with constraint violation to fail.
	 */
	@Test(expected=ConstraintViolationException.class)
	public void testCreateIssueWithIllegalArguments() {
		Issue issue = new Issue();
		issue.setReporterId(null);
		issue.setTitle(null);
		issue.setDescription("Test Description");
		issue = service.saveIssue(issue);
	}
	
	/**
	 * Test to update an issue correctly.
	 */
	@Test
	public void testUpdateIssue() {
		Issue issue = new Issue();
		issue.setId(null);
		issue.setReporterId(1L);
		issue.setTitle("Test Title");
		issue.setDescription("Test Description");
		
		//Create first.
		issue = service.saveIssue(issue);
		
		issue.setStatus(IssueStatus.CLOSED);
		issue.setTitle("Test Update Title");
		issue.setDescription("Test Update Description");
		
		//Update it.
		issue = service.saveIssue(issue);
		
		Assert.assertNotNull("Issue Id returned is null", issue.getId());
		Assert.assertNotNull("Issue status returned is null", issue.getStatus());
		Assert.assertNotNull("Issue created date returned is null", issue.getCreatedAt());
		Assert.assertEquals("Issue Title does not match", "Test Update Title", issue.getTitle());
		Assert.assertEquals("Issue Description does not match", "Test Update Description", issue.getDescription());
		Assert.assertEquals("Issue Status does not match", IssueStatus.CLOSED, issue.getStatus());
	}
	
	/**
	 * Test find by reporter, assignee and status. Test pagination and sorting ascending order by created date.
	 */
	@Test
	public void testFindIssuesByReporterPageanationAndSortingUp() {
		List<Issue> issues = service.findIssues(1L, 1L, IssueStatus.NEW, PageRequest.of(0, 1, Direction.ASC, "createdAt"));
		Assert.assertNotNull("Issues list is null", issues);
		Assert.assertEquals("Issues list is empty", 1, issues.size());
		Assert.assertEquals("Issues found is not expected", "Test Ticket 1", issues.get(0).getTitle());
	}
	
	/**
	 * Test find by reporter, assignee and status. Test pagination and sorting descending order by created date.
	 */
	@Test
	public void testFindIssuesByReporterPageanationAndSortingDown() {
		List<Issue> issues = service.findIssues(1L, 1L, IssueStatus.NEW, PageRequest.of(0, 1, Direction.DESC, "createdAt"));
		Assert.assertNotNull("Issues list is null", issues);
		Assert.assertEquals("Issues list is empty", 1, issues.size());
		Assert.assertEquals("Issues found is not expected", "Test Ticket 3", issues.get(0).getTitle());
	}
	
	/**
	 * Test find by create date. Test pagination and sorting ascending order by created date.
	 */
	@Test
	public void testFindIssuesByCreatedPageanationAndSortingUp() {
		Date startDate = Date.from(LocalDate.now().minusDays(1L).atStartOfDay(ZoneId.systemDefault()).toInstant());
		Date endDate = Date.from(LocalDate.now().plusDays(1L).atStartOfDay(ZoneId.systemDefault()).toInstant());
		
		List<Issue> issues = service.findIssues(startDate, endDate, PageRequest.of(0, 1, Direction.ASC, "createdAt"));
		Assert.assertNotNull("Issues list is null", issues);
		Assert.assertEquals("Issues list is empty", 1, issues.size());
		Assert.assertEquals("Issues found is not expected", "Test Ticket 1", issues.get(0).getTitle());
	}
	
	/**
	 * Test find by create date. Test pagination and sorting descending order by created date.
	 */
	@Test
	public void testFindIssuesByCreatedPageanationAndSortingDown() {
		Date startDate = Date.from(LocalDate.now().minusDays(1L).atStartOfDay(ZoneId.systemDefault()).toInstant());
		Date endDate = Date.from(LocalDate.now().plusDays(1L).atStartOfDay(ZoneId.systemDefault()).toInstant());
		
		List<Issue> issues = service.findIssues(startDate, endDate, PageRequest.of(0, 1, Direction.DESC, "created"));
		Assert.assertNotNull("Issues list is null", issues);
		Assert.assertEquals("Issues list is empty", 1, issues.size());
		Assert.assertEquals("Issues found is not expected", "Test Ticket 4", issues.get(0).getTitle());
	}
	
	/**
	 * Test deletion of issue by Id.
	 */
	@Test
	public void testDeleteIssueById() {
		
		Issue issue = new Issue();
		issue.setReporterId(1L);
		issue.setAssigneeId(1L);
		issue.setTitle("Test Ticket 1");
		issue.setDescription("Description One");
		issue = service.saveIssue(issue);
		
		Optional<Issue> issueOptional = repository.findById(issue.getId());
		Assert.assertTrue("Issues before delete not found", issueOptional.isPresent());
		service.deleteIssue(issue.getId());
		issueOptional = repository.findById(issue.getId());
		Assert.assertFalse("Issues not deleted properly.", issueOptional.isPresent());
	}

}
