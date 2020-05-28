package civ.control;


import civ.model.Map.Segment;
import civ.model.Units.Unit;
import civ.model.Units.Worker;
import civ.view.GameScreen;

public class PlayerRobot implements Robot {
    Player player;
    @Override
    public void update() {
        player.getCountry().resetCityAreas();
        for (Unit u:
             player.getCountry().getUnits()) {
            if(u.getClass() == Worker.class){
                Worker worker = (Worker)u;
                if(worker.isWorking()){
                    worker.progressBuilding();
                }
            }
        }
        player.getCountry().update();
        if(player.getCountry().getCost() < 0 && !GameScreen.mainPlayerKilled){
            GameScreen.mainPlayerKilled = true;
            GameScreen.mainUI.showExitDialog();
            GameScreen.stopGame = true;
        }
        player.getCountry().updateUnitsPowers();
    }

    @Override
    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public void freeSpacefromCountry() {
        for (Segment s:
                player.getCountry().getCountry()) {
            s.setColor(null);
            if(s.getUnits().size() != 0){
                for (int i = 0; i <s.getUnits().size() ; i++) {
                    s.getUnits().remove(i);
                }
            }
        }
    }
}
