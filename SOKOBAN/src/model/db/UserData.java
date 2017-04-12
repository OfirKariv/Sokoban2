package model.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity(name = "user_data")

public class UserData {

	@Id
	@OneToMany
	@JoinColumn(name = "UserID")
	private long userID;

	@Id
	@OneToMany
	@JoinColumn(name = "LevelID")
	private long levelID;

	@Column(name = "TimeFinished")
	private long timeFinished;

	@Column(name = "steps")
	private int steps;

	public long getUserID() {
		return userID;
	}

	public void setUserID(long userID) {
		this.userID = userID;
	}

	public long getLevelID() {
		return levelID;
	}

	public void setLevelID(long levelID) {
		this.levelID = levelID;
	}

	public long getTimeFinished() {
		return timeFinished;
	}

	public void setTimeFinished(long timeFinished) {
		this.timeFinished = timeFinished;
	}

	public int getSteps() {
		return steps;
	}

	public void setSteps(int steps) {
		this.steps = steps;
	}

}
