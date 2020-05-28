package civ.Strategy;

public interface BuildStrategy extends CommonStrategy {
    int BUILD_FORT = 3;
    int BUILD_FARM = 2;
    void buildSettlement(int type);
}
