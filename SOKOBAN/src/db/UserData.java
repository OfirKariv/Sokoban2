package db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity(name = "user_data")

public class UserData extends DbObject {
	@Id
	@JoinColumn(name = "UserName")
	private String userName;

	@JoinColumn(name = "LevelID")
	private int levelID;

	@Column(name = "TimeFinished")
	private long timeFinished;

	@Column(name = "steps")
	private int steps;

	public UserData() {
	};

	public UserData(String userName, int lvlID, int stepCount, int timer) {

		this.userName = userName;
		this.levelID = lvlID;
		this.steps = stepCount;
		this.timeFinished = timer;
	}

	public UserData(UserData ud) {
		this.userName = ud.getUserName();
		this.levelID = ud.getLevelID();

	}

	public String getUserName() {
		return userName;
	}

	public int getLevelID() {
		return levelID;
	}

	public void setLevelID(int levelID) {
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

	public String toString() {

		return "[UserName: " + userName + ", Level: " + levelID + ", steps: " + steps + ", time: " + timeFinished + "]";
	}

}
