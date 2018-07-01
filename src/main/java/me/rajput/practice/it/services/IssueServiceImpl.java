package me.rajput.practice.it.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lombok.extern.slf4j.Slf4j;
import me.rajput.practice.it.domain.Issue;
import me.rajput.practice.it.domain.User;
import me.rajput.practice.it.exceptions.UnauthorizedOperationException;
import me.rajput.practice.it.model.IssueStatus;
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
	 * @see me.rajput.practice.it.services.IssueService#save(me.rajput.practice.it.model.Issue)
	 */
	@Override
	public Long create(Issue issue) {
		User currentUser = userService.currentUser();
		
		if(currentUser == null) {
			throw new UnauthorizedOperationException("Please login first"); 
		}
		
		issue.setId(null);
		issue.setStatus(IssueStatus.NEW);
		
		issue = issueRepo.save(issue);
		
		if(issue.getId() != null) {
			LOGGER.info("New issue has been created with id ["+issue.getId()+"]");
		}
		
		return issue.getId();
	}
	
	/* (non-Javadoc)
	 * @see me.rajput.practice.it.services.IssueService#save(me.rajput.practice.it.model.Issue)
	 */
	@Override
	public Issue update(Issue issue) {
		User currentUser = userService.currentUser();
		
		if(currentUser == null) {
			throw new UnauthorizedOperationException("Please login first"); 
		}
		
		Assert.notNull(issue.getId(), "Issue Id must not be null");
		Assert.isTrue(!currentUser.getId().equals(issue.getReporter().getId()), "You are not the reporter of this issue.");
		
		//TODO:Protect the JsonIgnore properties.
		
		issue = issueRepo.save(issue);
		
		if(issue.getId() != null) {
			LOGGER.info("Issue has been updated properly with id [{}]", issue.getId());
		}
		
		return issue;
	}

	/* (non-Javadoc)
	 * @see me.rajput.practice.it.services.IssueService#deleteById(java.lang.Long)
	 */
	@Override
	public void deleteById(Long issueId) {
		issueRepo.deleteById(issueId);
		LOGGER.info("Issue with id [{}] has been deleted properly.", issueId);
	}
	
	/* (non-Javadoc)
	 * @see me.rajput.practice.it.services.IssueService#getIssue(java.lang.Long, org.springframework.data.domain.Pageable)
	 */
	@Override
	public IssueDto getDtoById(Long id, Pageable pageable) {
		Optional<Issue> issueOp = issueRepo.findById(id);
		
		IssueDto issueDto = null;
//		if(issueOp.isPresent()) {
//			Issue issue = issueOp.get();
//			issueDto = modelMapper.map(issue, IssueDto.class);
//			issueDto.setReporter(userService.getUserDtoById(issue.getReporterId()));
//			if(issue.getAssigneeId() != null) {
//				issueDto.setAssignee(userService.getUserDtoById(issue.getAssigneeId()));
//			}
//			
//			issueDto.setComments(commentService.getCommentsByIssueId(issue.getId(), pageable)
//									.stream()
//									.map(c -> {
//										CommentDto dto = modelMapper.map(c, CommentDto.class);
//										dto.setCommentator(userService.getUserDtoById(c.getCommentatorId()));
//										return dto;
//									})
//									.collect(Collectors.toList()));
//		}
		
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
	public List<Issue> findIssues(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
		Assert.notNull(startDate, "Start date must not be blank");
		Assert.notNull(endDate, "Etart date must not be blank");
		return issueRepo.findIssueByCreatedAtBetween(startDate, endDate, pageable);
	}

	@Override
	public Issue getById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
