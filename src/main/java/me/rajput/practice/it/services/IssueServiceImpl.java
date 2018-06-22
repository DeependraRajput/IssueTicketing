package me.rajput.practice.it.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lombok.extern.slf4j.Slf4j;
import me.rajput.practice.it.model.IssueStatus;
import me.rajput.practice.it.model.db.Issue;
import me.rajput.practice.it.model.dto.CommentDto;
import me.rajput.practice.it.model.dto.IssueDto;
import me.rajput.practice.it.repositories.IssueRepository;

@Service
@Slf4j
public class IssueServiceImpl implements IssueService {
	
	@Autowired
	private IssueRepository issueRepo;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private ModelMapper modelMapper;

	/* (non-Javadoc)
	 * @see me.rajput.practice.it.services.IssueService#saveIssue(me.rajput.practice.it.model.Issue)
	 */
	@Override
	public Issue saveIssue(Issue issue) {
		if(issue.getCreatedAt() == null) {
			issue.setCreatedAt(new Date());
		}
		if(issue.getStatus() == null || issue.getId() == null) {
			issue.setStatus(IssueStatus.NEW);
		}
		
		issue = issueRepo.save(issue);
		
		if(issue.getId() != null) {
			LOGGER.info("New Issue has been saved properly with id ["+issue.getId()+"]");
		}
		
		return issue;
	}

	/* (non-Javadoc)
	 * @see me.rajput.practice.it.services.IssueService#deleteIssue(java.lang.Long)
	 */
	@Override
	public void deleteIssue(Long issueId) {
		issueRepo.deleteById(issueId);
		LOGGER.info("Issue with id ["+issueId+"] has been deleted properly.");
	}
	
	/* (non-Javadoc)
	 * @see me.rajput.practice.it.services.IssueService#getIssue(java.lang.Long, org.springframework.data.domain.Pageable)
	 */
	@Override
	public IssueDto getIssue(Long id, Pageable pageable) {
		Optional<Issue> issueOp = issueRepo.findById(id);
		
		IssueDto issueDto = null;
		if(issueOp.isPresent()) {
			Issue issue = issueOp.get();
			issueDto = modelMapper.map(issue, IssueDto.class);
			issueDto.setReporter(userService.getUserDtoById(issue.getReporterId()));
			if(issue.getAssigneeId() != null) {
				issueDto.setAssignee(userService.getUserDtoById(issue.getAssigneeId()));
			}
			
			issueDto.setComments(commentService.getCommentsByIssueId(issue.getId(), pageable)
									.stream().map(c -> modelMapper.map(c, CommentDto.class))
									.collect(Collectors.toList()));
		}
		
		return issueDto;
	}

	/* (non-Javadoc)
	 * @see me.rajput.practice.it.services.IssueService#findIssues(java.lang.String, java.lang.String, me.rajput.practice.it.model.IssueStatus, org.springframework.data.domain.Pageable)
	 */
	@Override
	public List<Issue> findIssues(Long assigneeId, Long reporterId, IssueStatus status, Pageable pageable) {
		//TODO: check the requirement and usability of the method.
		return issueRepo.findIssueByAssigneeIdAndReporterIdAndStatus(assigneeId, reporterId, status, pageable);
	}
	
	/* (non-Javadoc)
	 * @see me.rajput.practice.it.services.IssueService#findIssues(java.util.Date, java.util.Date, org.springframework.data.domain.Pageable)
	 */
	@Override
	public List<Issue> findIssues(Date startDate, Date endDate, Pageable pageable) {
		Assert.notNull(startDate, "Start date must not be blank");
		Assert.notNull(endDate, "Etart date must not be blank");
		return issueRepo.findIssueByCreatedAtBetween(startDate, endDate, pageable);
	}
	

}
