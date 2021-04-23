package kr.or.ddit.starcraft.ddit;

import kr.or.ddit.starcraft.building.Barrack;
import kr.or.ddit.starcraft.unit.FootSoldier;

public class BattleField {

	Barrack barrack;
	
	public static void main(String[] args) {
		BattleField field = new BattleField();
		field.play();
	}
	
	public void play() {
		FootSoldier soldier = barrack.generateFirebat(3);
		soldier.attack();
	}
}
