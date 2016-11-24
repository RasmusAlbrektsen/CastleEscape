package util;

import castleescape.business.Configurations;

/**
 * Created by Alex on 17/11/2016.
 */
public class XMLConfugurationsBuilder implements IBuilder {
	@Override
	public void setDescription(String description) {

	}

	private int monsterMoveTime;
	private double monsterMoveChance;
	private String safeRoom;
	private String startRoom;
	private String monsterStartRoom;

	public void addConfiguration(String name,String data){
		switch (name.toLowerCase()){
			case "startroom":
				startRoom=data;
				break;
			case "monsterstartroom":
				monsterStartRoom=data;
				break;
			case "monstermovechance":
				try {
					monsterMoveChance = Double.valueOf(data);
				}catch (NumberFormatException ignored){
				}
				break;
			case "monstermovetime":
				try {
					monsterMoveTime = Integer.valueOf(data);
				}catch (NumberFormatException ignored){
				}
				break;
			case "saferoom":
				safeRoom=data;
				break;
		}
	}

	@Override
	public void build() {
		new Configurations(startRoom,safeRoom,monsterStartRoom,monsterMoveChance,monsterMoveTime);
	}
}
