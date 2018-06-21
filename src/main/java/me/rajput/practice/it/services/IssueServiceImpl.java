package me.rajput.practice.it.services;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import me.rajput.practice.it.model.IssueStatus;
import me.rajput.practice.it.model.Issue;
import me.rajput.practice.it.model.User;
import me.rajput.practice.it.repositories.IssueRepository;
import me.rajput.practice.it.repositories.UserRepository;

@Service
public class IssueServiceImpl implements IssueService {
	
	private static final Logger LOGGER = Logger.getLogger(IssueServiceImpl.class);
	
	@Autowired
	private IssueRepository repository;
	
	@Autowired
	private UserRepository userRepo;

	@Override
	public Issue saveIssue(Issue issue) {
		if(issue.getCreatedAt() == null) {
			issue.setCreatedAt(new Date());
		}
		if(issue.getStatus() == null || issue.getId() == null) {
			issue.setStatus(IssueStatus.NEW);
		}
		return repository.save(issue);
	}

	@Override
	public void deleteIssue(Long issueId) {
		repository.delete(issueId);
	}

	@Override
	public List<Issue> findIssues(String assignee, String reporter, IssueStatus status, Pageable pageable) {
		Long assigneeId = null;
		if(assignee != null) {
			try {
				assigneeId = Long.valueOf(assignee); 
			} catch (NumberFormatException nfe) {
				User user = userRepo.findByLoginId(assignee);
				if(user != null) {
					assigneeId = user.getId();
				} else {
					LOGGER.error("Assignee [" + assignee + "] is not found in the repository");
				}
			}
		}
		
		Long reporterId = null;
		if(reporter != null) {
			try {
				reporterId = Long.valueOf(reporter); 
			} catch (NumberFormatException nfe) {
				User user = userRepo.findByLoginId(reporter);
				if(user != null) {
					reporterId = user.getId();
				} else {
					LOGGER.error("Reporter [" + reporter + "] is not found in the repository");
				}	
			}
		}
		
		return repository.findIssueByAssigneeAndReporterAndStatus(assigneeId, reporterId, status, pageable);
	}
	
	@Override
	public List<Issue> findIssues(Date startDate, Date endDate, Pageable pageable) {
		Assert.notNull(startDate, "Start date must not be blank");
		Assert.notNull(endDate, "Etart date must not be blank");
		return repository.findIssueByCreatedAtBetween(startDate, endDate, pageable);
	}
}
