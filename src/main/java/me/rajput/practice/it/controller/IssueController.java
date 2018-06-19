package me.rajput.practice.it.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.util.StdDateFormat;

import me.rajput.practice.it.model.IssueStatus;
import me.rajput.practice.it.model.db.Issue;
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
	 * Initialize the data conversion for Date to String with given format.
	 * @param binder
	 */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new StdDateFormat(), false));
    }
	
    /**
     * Create or update an issue.
     * @param issue
     * @return
     */
	@RequestMapping(path= {"/createIssue", "/updateIssue"}, method=RequestMethod.POST)
	public Issue createIssue(@ModelAttribute Issue issue) {
		return issueService.saveIssue(issue);
	}
	
	/**
	 * Retrieves and issue based on it's id.
	 * @param issue
	 * @return
	 */
	@RequestMapping("/getIssue")
	public Issue getIssue(@RequestParam("id") Issue issue) {
		return issue;
	}
	
	/**
	 * Deletes an issue for the given id.
	 * @param id
	 */
	@RequestMapping("/deleteIssue")
	public void deleteIssue(@RequestParam("id") Long id) {
		issueService.deleteIssue(id);
	}
	
	/**
	 * Finds all issues for the given parameters.
	 * @param assignee
	 * @param reporter
	 * @param status
	 * @param pageable
	 * @return
	 */
	@RequestMapping(path="/findIssues", params={"assignee", "reporter", "status"})
	public List<Issue> findIssues(@RequestParam String assignee, @RequestParam String reporter, @RequestParam IssueStatus status, Pageable pageable) {
		return issueService.findIssues(assignee, reporter, status, pageable);
	}
	
	/**
	 * Finds all issues created between given dates.
	 * @param startDate
	 * @param endDate
	 * @param pageable
	 * @return
	 */
	@RequestMapping(path="/findIssues", params= {"startDate", "endDate"})
	public List<Issue> findIssues(@RequestParam Date startDate, @RequestParam Date endDate, Pageable pageable) {
		return issueService.findIssues(startDate, endDate, pageable);
	}

}
