package com.civ.model.Units;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.civ.Strategy.BuildStrategy;
import com.civ.model.Map.Country;
import com.civ.model.Map.Segment;
import com.civ.model.Settlements.Farm;
import com.civ.model.Settlements.Fort;
import com.civ.model.Settlements.Settlement;
import com.civ.utils.Assets;
import com.civ.view.GameScreen;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class Worker extends Civil implements BuildStrategy {
    int flag;
    int settlementFlag;
    boolean working;
    public static TextureRegion WORKER = Assets.textureAtlas.findRegion("w");
    public Worker(float x, float y, float w, float h) {
        super(WORKER, x, y, w, h);
        steps = 4;
        moveBounds = new Rectangle(x - steps, y - steps, 2 * steps, 2 * steps);
        flag = 0;
        settlementFlag = 0;
        working = false;
    }

    public void write(StringBuilder data) {
        data.append("\nworker");
        super.write(data);
    }


    public void read() {

    }

    public void progressBuilding(){
        flag++;
        build();
    }

    public boolean isWorking() {
        return working;
    }

    @Override
    public void buildSettlement(int type) {
        flag = 1;
        settlementFlag = type;
        working = true;
        canMove = false;
    }

    public void build(){
        int d = settlementFlag == BUILD_FARM?BUILD_FARM:BUILD_FORT;
        if(flag == d) {
            Segment s = forBuildSegment();
            if (settlementFlag == 1) {
                s.setSettlement(new Fort(Country.fort, bounds.getX(), bounds.getY(), bounds.getBoundingRectangle().width, bounds.getBoundingRectangle().height));
            }
            if (settlementFlag == 2) {
                s.setSettlement(new Farm(Country.farm, bounds.getX(), bounds.getY(), bounds.getBoundingRectangle().width, bounds.getBoundingRectangle().height));
            }
            flag = 0;
            working = false;
        }
    }

    @Override
    public void destroyObject(Settlement settlement) {
        settlement = null;
    }
}
