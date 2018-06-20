package me.rajput.practice.it.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import me.rajput.practice.it.model.IssueStatus;
import me.rajput.practice.it.model.db.Issue;

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
	 * @param assignee
	 * @param reporter
	 * @param status
	 * @param pageable
	 * @return
	 */
	List<Issue> findIssueByAssigneeAndReporterAndStatus(Long assignee, Long reporter, IssueStatus status, Pageable pageable);
	
	/**
	 * Find the issues created between provided dates (exclusive). 
	 * @param startDate
	 * @param endDate
	 * @param pageable
	 * @return
	 */
	List<Issue> findIssueByCreatedAtBetween(Date startDate, Date endDate, Pageable pageable);
	
}
