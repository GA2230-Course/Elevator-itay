package frc.robot.utils;

public class GearsMath {
    public static double gearsToRatio(int[][] chains) {
        double gearRatio = 1.0;
        for (int[] chain : chains) {
            if (chain.length < 2) {
                throw new IllegalArgumentException("Each chain needs at least 2 meshing gears");
            }
            for (int j : chain) {
                if (j <= 0) throw new IllegalArgumentException("Tooth counts must be positive");
            }
            gearRatio *= (double) chain[chain.length - 1] / chain[0];
        }
        return gearRatio;
}
    
}