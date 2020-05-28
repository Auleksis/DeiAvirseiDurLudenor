package com.civ.model.Map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import com.civ.control.MainGameRobot;
import com.civ.control.Player;
import com.civ.control.PlayerRobot;
import com.civ.model.GAmeObject;
import com.civ.model.Settlements.City;
import com.civ.model.Settlements.Farm;
import com.civ.model.Settlements.Fort;
import com.civ.model.Settlements.Settlement;
import com.civ.model.Units.FirstFighter;
import com.civ.model.Units.Poselenec;
import com.civ.model.Units.SecondFighter;
import com.civ.model.Units.Ship;
import com.civ.model.Units.ThirdFighter;
import com.civ.model.Units.Unit;
import com.civ.model.Units.Worker;
import com.civ.utils.Assets;
import com.civ.view.GameScreen;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoSerializable;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Map {
    ArrayList<Segment> map;
    public ArrayList<Country> countries;
    int WIDTH;
    int HEIGHT;

    //temp
    public Map(){
        WIDTH = 0;
        HEIGHT = 0;
        map = null;
        countries = null;
    }
    //temp

    public Map(ArrayList<Segment> map){
        this.map = map;
        WIDTH = 40;
        HEIGHT = 40;
    }

    public Map(int width, int height){
        WIDTH = width;
        HEIGHT = height;
        generateRightMap();
    }

    void generateRightMap(){
        map = new ArrayList<>();
        countries = new ArrayList<>();

        countries.add(new Country(new Color(Color.rgba8888(255, 45, 30, 1)), "Suran", new TextureRegion(Assets.textureAtlas.findRegion("0"))));
        GameScreen.players.add(new Player(new MainGameRobot(), countries.get(0)));

        countries.add(new Country(new Color(Color.rgba8888(136, 0, 21, 1)), "Nydhon", new TextureRegion(Assets.textureAtlas.findRegion("1"))));
        GameScreen.players.add(new Player(new MainGameRobot(), countries.get(1)));

        countries.add(new Country(new Color(Color.rgba8888(255, 242, 0, 1)), "Morndol", new TextureRegion(Assets.textureAtlas.findRegion("2"))));
        GameScreen.players.add(new Player(new MainGameRobot(), countries.get(2)));

        countries.add(new Country(new Color(Color.rgba8888(153, 217, 234, 1)), "Cinagon", new TextureRegion(Assets.textureAtlas.findRegion("3"))));
        GameScreen.players.add(new Player(new MainGameRobot(), countries.get(3)));

        countries.add(new Country(new Color(Color.rgba8888(255, 127, 39, 1)), "Sieben", new TextureRegion(Assets.textureAtlas.findRegion("4"))));
        GameScreen.players.add(new Player(new MainGameRobot(), countries.get(4)));

        countries.add(new Country(new Color(Color.rgba8888(34, 177, 76, 1)), "Irilin", new TextureRegion(Assets.textureAtlas.findRegion("5"))));
        GameScreen.players.add(new Player(new PlayerRobot(), countries.get(5)));

        countries.add(new Country(new Color(Color.rgba8888(239, 228, 176, 1)), "Reidhon", new TextureRegion(Assets.textureAtlas.findRegion("6"))));
        GameScreen.players.add(new Player(new MainGameRobot(), countries.get(6)));

        countries.add(new Country(new Color(Color.rgba8888(255, 201, 14, 1)), "Ruderan", new TextureRegion(Assets.textureAtlas.findRegion("7"))));
        GameScreen.players.add(new Player(new MainGameRobot(), countries.get(7)));

        countries.add(new Country(new Color(Color.rgba8888(163, 73, 164, 1)), "Telrani", new TextureRegion(Assets.textureAtlas.findRegion("8"))));
        GameScreen.players.add(new Player(new MainGameRobot(), countries.get(8)));

        countries.add(new Country(new Color(Color.rgba8888(237, 28, 36, 1)), "Luri", new TextureRegion(Assets.textureAtlas.findRegion("9"))));
        GameScreen.players.add(new Player(new MainGameRobot(), countries.get(9)));

        countries.add(new Country(new Color(Color.rgba8888(63, 72, 204, 1)), "Lodes", new TextureRegion(Assets.textureAtlas.findRegion("10"))));
        GameScreen.players.add(new Player(new MainGameRobot(), countries.get(10)));


        GameScreen.currentPlayer = GameScreen.players.get(5);

        generateNaturalMap(1);
    }

    public void fillCountries(){
        int direction = 0;
        int seedPos[] = new int[countries.size()];
        int numSeeds = countries.size();
        int numUpdates = 5;

        for (int i = 0; i < numSeeds; i++) {
            seedPos[i] = (int) (Math.random() * (WIDTH * HEIGHT));
            Segment segment = map.get(seedPos[i]);
            boolean countryFlag = true;
            while (segment.getPoint().getClass() == Water.class || countryFlag){
                do {
                    seedPos[i] = (int) (Math.random() * (WIDTH * HEIGHT));
                }while  (seedPos[i] < 0 || seedPos[i] > WIDTH * HEIGHT);
                segment = map.get(seedPos[i]);
                countryFlag = false;
            }
            Country c = countries.get(i);
            segment.setSettlement(new City(c.getCity(), segment.getPoint().getBounds().getX(), segment.getPoint().getBounds().getY(), segment.getPoint().width, segment.getPoint().height));
            c.addSegment(segment);
        }
        for (int i = 0; i < numSeeds; i++) {
            for (int j = 0; j < numUpdates; j++) {
                direction = (int)((Math.random() * 10000) % 4);
                if(direction == 0){
                    seedPos[i]+=WIDTH;
                }
                else if(direction == 1){
                    seedPos[i]++;
                }
                else if(direction == 2){
                    seedPos[i]-=WIDTH;
                }
                else if(direction == 3){
                    seedPos[i]--;
                }
                if(seedPos[i] < 0 || seedPos[i]>=map.size()){
                    break;
                }
                while (map.get(seedPos[i]).getPoint().getClass() == Water.class){
                    direction = (int)((Math.random() * 10000) % 4);
                    if(direction == 0){
                        seedPos[i]+=WIDTH;
                    }
                    else if(direction == 1){
                        seedPos[i]++;
                    }
                    else if(direction == 2){
                        seedPos[i]-=WIDTH;
                    }
                    else if(direction == 3){
                        seedPos[i]--;
                    }
                }
                if(seedPos[i] > 0 && seedPos[i] < (WIDTH * HEIGHT)){
                    Segment segment = map.get(seedPos[i]);
                    Country c = countries.get(i);
                    if(!c.hasSegment(segment))
                        c.addSegment(segment);
                }
            }
        }
        for (Country c:
             countries) {
            for (Segment s:
                 c.getCountry()) {
                if(s.getSettlement() != null){
                    Unit u = new FirstFighter(s.getPoint().getBounds().getX(), s.getPoint().getBounds().getY(), s.getPoint().width, s.getPoint().height);
                    s.addUnit(u);
                    c.getUnits().add(u);
                    continue;
                }
            }
        }
        for (int i = 0; i < countries.size(); i++) {
            Country c = countries.get(i);
            if(!c.hasCities()){
                countries.remove(c);
            }
        }
    }

    void generateNaturalMap(int type){
        int direction;
        int seedPos[]= null;
        int numSeeds = 0;
        int numUpdates = 0;

        if(type == 0) {
            seedPos = new int[32];
            numSeeds = 32;
            numUpdates = 800;
        }
        if(type == 1){
            seedPos = new int[28];
            numSeeds = 28;
            numUpdates = 500;
        }
        if(type == 2){
            seedPos = new int[3];
            numSeeds = 3;
            numUpdates = 1500;
        }
        if(type == 3){
            seedPos = new int[10];
            numSeeds = 10;
            numUpdates = 900;
        }
        clearMap();
        for (int i = 0; i < numSeeds; i++) {
            seedPos[i] = (int) (Math.random() * (WIDTH * HEIGHT));
            Segment segment = map.get(seedPos[i]);
            int tempID = segment.getId();
            segment = new Segment(new Earth(segment.getPoint().getBounds().getX(), segment.getPoint().getBounds().getY(), segment.getPoint().width, segment.getPoint().height));
            segment.setId(tempID);
            map.set(seedPos[i], segment);
        }

        for (int i = 0; i < numUpdates; i++) {
            for (int j = 0; j < numSeeds; j++) {
                direction = (int)((Math.random() * 10000) % 4);

                if(direction == 0){
                    seedPos[j] += WIDTH;
                }
                else if(direction == 1){
                    seedPos[j]++;
                }
                else if(direction == 2){
                    seedPos[j] -= WIDTH;
                }
                else if(direction == 3){
                    seedPos[j]--;
                }

                if(seedPos[j] <=0 || seedPos[j] >= (WIDTH * HEIGHT)){
                    seedPos[j] = (int)(Math.random() * (WIDTH * HEIGHT)) - 1;
                }
                Segment segment = map.get(seedPos[j]);
                int tempID = segment.getId();
                segment = new Segment(new Earth(segment.getPoint().getBounds().getX(), segment.getPoint().getBounds().getY(), segment.getPoint().width, segment.getPoint().height));
                segment.setId(tempID);
                map.set(seedPos[j], segment);
            }
        }
    }


    void clearMap(){
        float x = 0, y = 0;
        Segment s;
        Point p;
        map = new ArrayList<>();
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {

                p = new Water(x, y, 1f, 1f);

                s = new Segment(p);
                x += 1;
                map.add(s);
            }
            y += 1;
            x = 0;
        }
    }

    void generateRegion(float x, float y, float finishX, float finishY, int prefStartX, int prefStartY){

        Random rnd = new Random();

        float startX = 0;
        float startY = 0;
        //float startX = (int)(Math.floor(WIDTH / 4)) + x;
        //float startY = (int)(Math.floor(HEIGHT / 4)) + y;
        if(prefStartX == -1 && prefStartY == -1) {
            startX = rnd.nextInt(WIDTH / 4) + x;
            startY = rnd.nextInt(HEIGHT / 4) + y;
        }else {
            startX = prefStartX;
            startY = prefStartY;
        }

        int c = 1;
        int d = 0;
        Point p = new Earth(startX, startY, 1f, 1f);
        map.set(getSegmentNumber(startX, startY), new Segment(p));

        for (int i = 1; i <= ((WIDTH / 2) - 1); i++) {
            for (int j = 0; j < (i < ((HEIGHT / 2) - 1)? 2 : 3); j++) {
                for (int k = 0; k < c; k++) {

                    switch (d){
                        case 0:
                            startY++;
                            break;
                        case 1:
                            startX++;
                            break;
                        case 2:
                            startY--;
                            break;
                        case 3:
                            startX--;
                            break;
                    }
                    if(startX >= 0 && startY >= 0 && startX <= finishX && startY <= finishY) {
                        if (i <= WIDTH / 6)
                            p = new Earth(startX, startY, 1f, 1f);
                        if (i > WIDTH / 6 && i < WIDTH / 4) {
                            if (rnd.nextInt() % 7 == 0) {
                                p = new Water(startX, startY, 1f, 1f);
                            } else {
                                p = new Earth(startX, startY, 1f, 1f);
                            }
                        }
                        if (i >= WIDTH / 4 && i <= WIDTH / 3) {
                            if (rnd.nextInt() % 2 == 0) {
                                p = new Water(startX, startY, 1f, 1f);
                            } else {
                                p = new Earth(startX, startY, 1f, 1f);
                            }
                        }
                        if (i > WIDTH / 3 && i <= WIDTH / 2) {
                            if (rnd.nextInt() % 17 == 0) {
                                p = new Earth(startX, startY, 1f, 1f);
                            } else
                                p = new Water(startX, startY, 1f, 1f);
                        }

                        if (i > WIDTH / 2) {
                            p = new Water(startX, startY, 1f, 1f);
                        }

                        //map.add(new Segment(p));
                        if(existsSegment(startX, startY)) {
                            map.set(getSegmentNumber(startX, startY), new Segment(p));
                        }
                    }

                }
                d = (d + 1) % 4;
            }
            c++;
        }
    }

    boolean existsSegment(float x, float y){
        for (Segment s:
             map) {
            if(s.getPoint().getBounds().contains(x, y)){
                return true;
            }
        }
        return false;
    }

    int getSegmentNumber(float x, float y){
        for (int i = 0; i < map.size(); i++) {
            if(map.get(i).getPoint().getBounds().contains(x, y)){
                return i;
            }
        }
        return -1;
    }

    void generateMap(){
        //TODO переделать спрайты на цветы: то есть использовать один белый спрайт, но с разными окрасками.

        Random rnd = new Random();
        float x = 0, y = 0;
        Segment s;
        Point p;
        map = new ArrayList<>();
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if(rnd.nextInt() % 2 == 0){
                    p = new Water(x, y, 1f, 1f);
                }else{
                    p = new Earth(x, y, 1f, 1f);
                }
                s = new Segment(p);
                x += 1;
                map.add(s);
            }
            y += 1;
            x = 0;
        }


    }

    public Country getCountry(String name){
        for (Country c:
             countries) {
            if(c.getName().equals(name))
                return c;
        }
        return null;
    }

    public void draw(SpriteBatch batch){
        for (Segment i:
             map) {
            i.draw(batch);
        }

    }

    public ArrayList<Segment> getMap() {
        return map;
    }

    public void dispose(){
        map = null;
        countries = null;
    }


    public String write() {
        StringBuilder data = new StringBuilder();
        data.append(Integer.valueOf(WIDTH).toString());
        data.append("\n" + Integer.valueOf(HEIGHT).toString());
        for (Segment s:
             map) {
            s.write(data);
        }
        for (Country c:
             countries) {
            c.write(data);
        }
        data.append("\n" + "end");
        return data.toString();
    }


    public void read(String data) {
        Segment.reboot();
        GAmeObject.reboot();
        Scanner sc = new Scanner(data);
        WIDTH = Integer.valueOf(sc.nextLine());
        HEIGHT = Integer.valueOf(sc.nextLine());
        map = new ArrayList<>();
        countries = new ArrayList<>();

        countries.add(new Country(new Color(Color.rgba8888(255, 45, 30, 1)), "Suran", Assets.textureAtlas.findRegion("0")));
        countries.add(new Country(new Color(Color.rgba8888(136, 0, 21, 1)), "Nydhon", Assets.textureAtlas.findRegion("1")));
        countries.add(new Country(new Color(Color.rgba8888(255, 242, 0, 1)), "Morndol", Assets.textureAtlas.findRegion("2")));
        countries.add(new Country(new Color(Color.rgba8888(153, 217, 234, 1)), "Cinagon", Assets.textureAtlas.findRegion("3")));
        countries.add(new Country(new Color(Color.rgba8888(255, 127, 39, 1)), "Sieben", Assets.textureAtlas.findRegion("4")));
        countries.add(new Country(new Color(Color.rgba8888(34, 177, 76, 1)), "Irilin", Assets.textureAtlas.findRegion("5")));
        countries.add(new Country(new Color(Color.rgba8888(239, 228, 176, 1)), "Reidhon", Assets.textureAtlas.findRegion("6")));
        countries.add(new Country(new Color(Color.rgba8888(255, 201, 14, 1)), "Ruderan", Assets.textureAtlas.findRegion("7")));
        countries.add(new Country(new Color(Color.rgba8888(163, 73, 164, 1)), "Telrani", Assets.textureAtlas.findRegion("8")));
        countries.add(new Country(new Color(Color.rgba8888(237, 28, 36, 1)), "Luri", Assets.textureAtlas.findRegion("9")));
        countries.add(new Country(new Color(Color.rgba8888(63, 72, 204, 1)), "Lodes", Assets.textureAtlas.findRegion("10")));

        float x = 0, y = 0;
        Segment s = null;
        Point p = null;
        String temp;
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                temp = sc.nextLine();
                if(temp.equals("earth")){
                    p = new Earth(x, y, 1f, 1f);
                }
                else if(temp.equals("water")){
                    p = new Water(x, y, 1f, 1f);
                }
                s = new Segment(p);
                temp = sc.nextLine();
                if(!temp.equals("null")){
                    Settlement settlement = null;
                    if(temp.equals("city")){
                        settlement = new City(x, y, 1f, 1f);
                    }
                    else if(temp.equals("fort")){
                        settlement = new Fort(Country.fort, x, y, 1f, 1f);
                    }
                    else if(temp.equals("farm")){
                        settlement = new Farm(Country.farm, x, y, 1f, 1f);
                    }
                    temp = sc.nextLine();
                    settlement.setId(Integer.valueOf(temp));
                    s.setSettlement(settlement);
                }
                temp = sc.nextLine();
                if(!temp.equals("null")){
                    int unitsSize = Integer.valueOf(temp);
                    Unit unit = null;
                    for (int k = 0; k < unitsSize; k++) {
                        temp = sc.nextLine();
                        if(temp.equals("warriror I")){
                            unit = new FirstFighter(x, y, 1f, 1f);
                        }
                        else if(temp.equals("warriror II")){
                            unit = new SecondFighter(x, y, 1f, 1f);
                        }
                        else if(temp.equals("warriror III")){
                            unit = new ThirdFighter(x, y, 1f, 1f);
                        }
                        else if(temp.equals("poselenec")){
                            unit = new Poselenec(x, y, 1f, 1f);
                        }
                        else if(temp.equals("ship")){
                            unit = new Ship(x, y, 1f, 1f);
                        }
                        else if(temp.equals("worker")){
                            unit = new Worker(x, y, 1f, 1f);
                        }
                        temp = sc.nextLine();
                        unit.setId(Integer.valueOf(temp));
                        temp = sc.nextLine();
                        if(temp.equals("yes")){
                            unit.setCanMove(true);
                        }
                        else {
                            unit.setCanMove(false);
                        }
                        s.addUnit(unit);
                    }
                }

                x += 1;
                map.add(s);
            }
            y++;
            x = 0;
        }
        temp = sc.nextLine();
        for (Country c:
             countries) {
            if(c.name.equals(temp)){
                temp = sc.nextLine();
                int countrySize = Integer.valueOf(temp);
                temp = sc.nextLine();
                int unitSize = Integer.valueOf(temp);
                for (int i = 0; i < countrySize; i++) {
                    temp = sc.nextLine();
                    int segmentID = Integer.valueOf(temp);
                    Segment tempSegment = map.get(segmentID - 1);
                    if(tempSegment.getSettlement() != null) {
                        if (tempSegment.getSettlement().getClass() == City.class) {
                            tempSegment.getSettlement().setSprite(c.city);
                        }
                    }
                    c.addSegment(tempSegment);
                }
                for (int i = 0; i < unitSize; i++) {
                    temp = sc.nextLine();
                    int unitID = Integer.valueOf(temp);
                    for (Segment segment:
                         map) {
                        if(segment.hasUnit(unitID)){
                            c.getUnits().add(segment.getUnit(unitID));
                        }
                    }
                }
                temp = sc.nextLine();
            }
        }
    }

    public void falseActivationUnits(){
        for (Segment s:
             map) {
            for (Unit u:
                 s.getUnits()) {
                u.setActive(false);
            }
        }
    }

}
