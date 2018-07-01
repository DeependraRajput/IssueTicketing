package me.rajput.practice.it.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import me.rajput.practice.it.domain.Comment;
import me.rajput.practice.it.services.CommentService;
import me.rajput.practice.it.services.WebEntityService;

/**
 * 
 * Description: MVC Controller to handle all the REST calls for comments.
 * 
 * @author Deependra Rajput
 * @date Jun 14, 2018
 *
 */
@RestController
public class CommentController  extends BaseController<Comment> {
	
	/** URI mapping for this controller. */
	private static final String URI_MAPPING = "/comment";
	
	@Autowired
	private CommentService commentService;
	
	/**
	 * Default constructor.
	 */
	public CommentController() {
		super(URI_MAPPING, "Comment");
	}
	
	/**
	 * Retrieves a comment based on it's id.
	 * @param id
	 * @return
	 */
	@RequestMapping(value = URI_MAPPING + ID_PARAM, method = RequestMethod.GET)
	public ResponseEntity<?> getComment(@PathVariable("id") Long id, Pageable pageable) {
		return super.getEntity(id, pageable);
	}

	/**
	 * Create a new comment.
	 * @param comment
	 * @param ucBuilder
	 * @return the URI location of new comment.
	 */
	@RequestMapping(value = URI_MAPPING, method = RequestMethod.POST)
	public ResponseEntity<?> createComment(@RequestBody Comment comment, UriComponentsBuilder ucBuilder) {
		return super.createEntity(comment, ucBuilder);
	}

	/**
	 * Updates an existing comment.
	 * @param id
	 * @param comment
	 * @return new values of updated comment.
	 */
	@RequestMapping(value = URI_MAPPING + ID_PARAM, method = RequestMethod.PUT)
	public ResponseEntity<?> updateComment(@PathVariable("id") Long id, @RequestBody Comment comment) {
		return super.updateEntity(id, comment);
	}

	/**
	 * Deletes an existing comment.
	 * @param id
	 * @return
	 */
	@RequestMapping(value = URI_MAPPING + ID_PARAM, method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteComment(@PathVariable("id") Long id) {
		return super.deleteEntity(id);
	}
	
	/* (non-Javadoc)
	 * @see me.rajput.practice.it.controller.CommentController#getEntityService()
	 */
	@Override
	protected WebEntityService<Comment> getEntityService() {
		return this.commentService;
	}

}
