package view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.swing.plaf.metal.MetalBorders.Flush3DBorder;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class LevelDisplayer extends Canvas {

	char[][] levelData;
	private StringProperty firstFileName;
	private StringProperty wallFileName;
	private StringProperty boxFileName;
	private StringProperty targetFileName;
	private StringProperty charaFileName;
	private StringProperty floorFileName;

	public LevelDisplayer() {
		firstFileName = new SimpleStringProperty();
		wallFileName = new SimpleStringProperty();
		boxFileName = new SimpleStringProperty();
		targetFileName = new SimpleStringProperty();
		charaFileName = new SimpleStringProperty();
		floorFileName = new SimpleStringProperty();

	}

	public char[][] getLevelData() {
		return levelData;
	}

	public void setLevelData(char[][] levelData) {
		this.levelData = levelData;
		redraw();
	}

	public String getFirstFileName() {
		return firstFileName.get();
	}

	public String getWallFileName() {
		return wallFileName.get();
	}

	public String getBoxFileName() {
		return boxFileName.get();
	}

	public String getTargetFileName() {
		return targetFileName.get();
	}

	public String getCharaFileName() {
		return charaFileName.get();
	}

	public String getFloorFileName() {
		return floorFileName.get();
	}

	public void setFirstFileName(String firstFileName) {
		this.firstFileName.set(firstFileName);
	}

	public void setWallFileName(String wallFileName) {
		this.wallFileName.set(wallFileName);
	}

	public void setBoxFileName(String boxFileName) {
		this.boxFileName.set(boxFileName);
	}

	public void setTargetFileName(String targetFileName) {
		this.targetFileName.set(targetFileName);
	}

	public void setCharaFileName(String charaFileName) {
		this.charaFileName.set(charaFileName);
	}

	public void setFloorFileName(String floorFileName) {
		this.floorFileName.set(floorFileName);
	}

	public void setFirstScreen() {

		GraphicsContext aa = getGraphicsContext2D();
		try {

			Image image = new Image(new FileInputStream(getFirstFileName()));
			aa.drawImage(image, 0, 0, getWidth(), getHeight());

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

	}

	public void redraw() {

		if (levelData != null) {
			double W = getWidth();
			double H = getHeight();
			double w = W / levelData[0].length;

			double h = H / levelData.length;
			GraphicsContext gc = getGraphicsContext2D();
			Image box = null;
			Image wall = null;
			Image floor = null;
			Image character = null;
			Image target = null;

			try {
				floor = new Image(new FileInputStream(getFloorFileName()));
				box = new Image(new FileInputStream(getBoxFileName()));
				wall = new Image(new FileInputStream(getWallFileName()));
				character = new Image(new FileInputStream(getCharaFileName()));
				target = new Image(new FileInputStream(getTargetFileName()));

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			for (int i = 0; i < levelData.length; i++)
				for (int j = 0; j < levelData[i].length; j++) {

					try {
						character = new Image(new FileInputStream(charaFileName.get()));
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					switch (levelData[i][j]) {

					case '#':

						gc.drawImage(wall, j * w, i * h, w, h);

						break;
					case ' ':
						gc.drawImage(floor, j * w, i * h, w, h);

						break;
					case '@':

						gc.drawImage(box, j * w, i * h, w, h);

						break;
					case 'A':
						gc.drawImage(floor, j * w, i * h, w, h);
						gc.drawImage(character, j * w, i * h, w, h);

						break;
					case 'O':
						gc.drawImage(floor, j * w, i * h, w, h);
						gc.drawImage(target, j * w, i * h, w, h);

						break;
					default:
						break;

					}

				}

		}

	}

}
