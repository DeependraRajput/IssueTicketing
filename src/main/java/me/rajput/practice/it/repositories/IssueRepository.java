package me.rajput.practice.it.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import me.rajput.practice.it.domain.Issue;
import me.rajput.practice.it.domain.IssueStatus;

/**
 * 
 * Description: Repository to access issues in the database. 
 * 
 * @author Deependra Rajput
 * @date Jun 14, 2018
 *
 */
public interface IssueRepository extends CrudRepository<Issue, Long> {
	
	/**
	 * Find the issues for the given parameters if either of them matches.
	 * @param assigneeId
	 * @param reporterId
	 * @param status
	 * @param pageable
	 * @return
	 */
	List<Issue> findIssueByAssigneeIdAndReporterIdAndStatus(Long assigneeId, Long reporterId, IssueStatus status, Pageable pageable);
	
	/**
	 * Find the issues created between provided dates (exclusive). 
	 * @param startDate
	 * @param endDate
	 * @param pageable
	 * @return
	 */
	List<Issue> findIssueByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
	
}
