package me.rajput.practice.it.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import me.rajput.practice.it.model.IssueStatus;
import me.rajput.practice.it.model.db.Issue;
import me.rajput.practice.it.model.dto.IssueDto;
import me.rajput.practice.it.services.IssueService;

/**
 * 
 * Description: MVC controller to handle all REST calls for issues. 
 * 
 * @author Deependra Rajput
 * @date Jun 14, 2018
 *
 */
@RestController
public class IssueController {
	
	@Autowired
	private IssueService issueService;
	
    /**
     * Create or update an issue.
     * @param issue
     * @return
     */
	@RequestMapping(path= {"/issue/createIssue", "/issue/updateIssue"}, method=RequestMethod.GET)
	public Long createIssue(@ModelAttribute Issue issue) {
		 issue = issueService.saveIssue(issue);
		 return issue.getId();
	}
	
	/**
	 * Retrieves and issue based on it's id.
	 * @param issue
	 * @param pageable
	 * @return
	 */
	@RequestMapping("/issue/getIssue")
	public IssueDto getIssue(@RequestParam("id") Long id, Pageable pageable) {
		return issueService.getIssue(id, pageable);
	}
	
	/**
	 * Deletes an issue for the given id.
	 * @param id
	 */
	@RequestMapping("/issue/deleteIssue")
	public void deleteIssue(@RequestParam("id") Long id) {
		issueService.deleteIssue(id);
	}
	
	/**
	 * Finds all issues for the given parameters.
	 * @param assigneeId
	 * @param reporterId
	 * @param status
	 * @param pageable
	 * @return
	 */
	@RequestMapping(path="/issue/findIssues", params={"assigneeId", "reporterId", "status"})
	public List<Issue> findIssues(@RequestParam Long assigneeId, @RequestParam Long reporterId, @RequestParam IssueStatus status, Pageable pageable) {
		return issueService.findIssues(assigneeId, reporterId, status, pageable);
	}
	
	/**
	 * Finds all issues created between given dates.
	 * @param startDate
	 * @param endDate
	 * @param pageable
	 * @return
	 */
	@RequestMapping(path="/issue/findIssues", params={"startDate", "endDate"})
	public List<Issue> findIssues(@RequestParam LocalDateTime startDate, @RequestParam LocalDateTime endDate, Pageable pageable) {
		return issueService.findIssues(startDate, endDate, pageable);
	}

}
