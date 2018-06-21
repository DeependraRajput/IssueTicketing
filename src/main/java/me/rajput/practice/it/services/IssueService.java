package me.rajput.practice.it.services;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;

import me.rajput.practice.it.model.IssueStatus;
import me.rajput.practice.it.model.Issue;

/**
 * Interface defining the APIs to provided by the service on issues.
 * Description: 
 * 
 * @author Deependra Rajput
 * @date Jun 14, 2018
 *
 */
public interface IssueService {
	
	/**
	 * This will create new issues.
	 * @param issue
	 */
	Issue saveIssue(Issue issue);

	/**
	 * This will delete an existing issue based on the given Id.
	 * @param issueId
	 */
	void deleteIssue(Long issueId);
	
	/**
	 * This will provide a list of issues based on the provided filter criteria.
	 * @param assignee of tickets.
	 * @param reporter of tickets.
	 * @param status of tickets.
	 * @param pagenation and sorting criteria.
	 * @return
	 */
	List<Issue> findIssues(String assignee, String reporter, IssueStatus status, Pageable pageable);
	
	/**
	 * This will provide a list of issues based on the provided filter criteria.
	 * @param startDate to find issues.
	 * @param endDate to find issues.
	 * @param pagenation and sorting criteria.
	 * @return
	 */
	List<Issue> findIssues(Date startDate, Date endDate, Pageable pageable);
	
}
