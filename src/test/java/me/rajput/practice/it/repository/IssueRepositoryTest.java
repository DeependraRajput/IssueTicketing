package me.rajput.practice.it.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import me.rajput.practice.it.TestBase;
import me.rajput.practice.it.domain.Issue;
import me.rajput.practice.it.repositories.IssueRepository;

@Transactional
public class IssueRepositoryTest extends TestBase {
	
	@Autowired
	private IssueRepository issueRepository;
	
	@Test
	public void testReadIssue() {
		Long issueId = Long.valueOf(1L);
		Optional<Issue> iOp = issueRepository.findById(issueId);
		Assert.assertTrue("Issue not found", iOp.isPresent());
		Issue issue = iOp.get();
		Assert.assertEquals("Issue Id does not match", issueId, issue.getId());
		Assert.assertNotNull("Reporter must not be null", issue.getReporter());
		Assert.assertNotNull("Comments must not be null", issue.getComments());
		Assert.assertEquals("Comments must be four", 4, issue.getComments().size());
	}

}
