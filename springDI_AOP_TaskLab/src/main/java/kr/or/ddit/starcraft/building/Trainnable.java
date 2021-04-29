package kr.or.ddit.starcraft.building;

import kr.or.ddit.starcraft.unit.FootSoldier;

public interface Trainnable {
	FootSoldier traingSoldier(SoldierType type);
	FootSoldier[] traingSoldiers(SoldierType type, int num);
	
}
