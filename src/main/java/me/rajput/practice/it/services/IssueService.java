package me.rajput.practice.it.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;

import me.rajput.practice.it.domain.Issue;
import me.rajput.practice.it.domain.IssueStatus;

/**
 * Interface defining the APIs to provided by the service on issues.
 * Description: 
 * 
 * @author Deependra Rajput
 * @date Jun 14, 2018
 *
 */
public interface IssueService extends WebEntityService<Issue> {
	
	/**
	 * This will provide a list of issues based on the provided filter criteria.
	 * @param assigneeId of tickets.
	 * @param reporterId  of tickets.
	 * @param status of tickets.
	 * @param pagenation and sorting criteria.
	 * @return
	 */
	List<Issue> findIssues(Long assigneeId, Long reporterId, IssueStatus status, Pageable pageable);
	
	/**
	 * This will provide a list of issues based on the provided filter criteria.
	 * @param startDate to find issues.
	 * @param endDate to find issues.
	 * @param pagenation and sorting criteria.
	 * @return
	 */
	List<Issue> findIssues(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
	
}
