package model.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "level")
public class LevelInfo {

	public LevelInfo(String s) {
		setLevelName(s);
	}

	@Id
	@Column(name = "LevelID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long levelID;

	@Column(name = "LevelName")
	private String levelName;

	public long getLevelID() {
		return levelID;
	}

	public void setLevelID(long levelID) {
		this.levelID = levelID;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

}
