package me.rajput.practice.it.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import me.rajput.practice.it.domain.Issue;
import me.rajput.practice.it.model.IssueStatus;
import me.rajput.practice.it.services.WebEntityService;
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
public class IssueController extends BaseController<Issue> {
	
	/** URI mapping for this controller. */
	private static final String URI_MAPPING = "/issue";
	
	@Autowired
	private IssueService issueService;
	
	/**
	 * Default constructor.
	 */
	public IssueController() {
		super(URI_MAPPING, "Issue");
	}
	
	/**
	 * Retrieves a issue based on it's id.
	 * @param id
	 * @return
	 */
	@RequestMapping(value = URI_MAPPING + ID_PARAM, method = RequestMethod.GET)
	public ResponseEntity<?> getIssue(@PathVariable("id") Long id, Pageable pageable) {
		return super.getEntity(id, pageable);
	}

	/**
	 * Create a new issue.
	 * @param issue
	 * @param ucBuilder
	 * @return the URI location of new issue.
	 */
	@RequestMapping(value = URI_MAPPING, method = RequestMethod.POST)
	public ResponseEntity<?> createIssue(@RequestBody Issue issue, UriComponentsBuilder ucBuilder) {
		return super.createEntity(issue, ucBuilder);
	}

	/**
	 * Updates an existing issue.
	 * @param id
	 * @param issue
	 * @return new values of updated issue.
	 */
	@RequestMapping(value = URI_MAPPING + ID_PARAM, method = RequestMethod.PUT)
	public ResponseEntity<?> updateIssue(@PathVariable("id") Long id, @RequestBody Issue issue) {
		return super.updateEntity(id, issue);
	}

	/**
	 * Deletes an existing issue.
	 * @param id
	 * @return
	 */
	@RequestMapping(value = URI_MAPPING + ID_PARAM, method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteIssue(@PathVariable("id") Long id) {
		return super.deleteEntity(id);
	}
	
	/**
	 * Finds all issues for the given parameters.
	 * @param assigneeId
	 * @param reporterId
	 * @param status
	 * @param pageable
	 * @return
	 */
	@RequestMapping(path="/issue/findIssues", params={"assigneeId", "reporterId", "status"}, method=RequestMethod.GET)
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
	@RequestMapping(path="/issue/findIssues", params={"startDate", "endDate"}, method=RequestMethod.GET)
	public List<Issue> findIssues(@RequestParam LocalDateTime startDate, @RequestParam LocalDateTime endDate, Pageable pageable) {
		return issueService.findIssues(startDate, endDate, pageable);
	}
	
	/* (non-Javadoc)
	 * @see me.rajput.practice.it.controller.IssueController#getEntityService()
	 */
	@Override
	protected WebEntityService<Issue> getEntityService() {
		return this.issueService;
	}

}
