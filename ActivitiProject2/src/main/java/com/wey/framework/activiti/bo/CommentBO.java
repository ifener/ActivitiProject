package com.wey.framework.activiti.bo;

import org.activiti.engine.task.Comment;

public class CommentBO {

	private String taskName;
	private Comment comment;

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}

}
