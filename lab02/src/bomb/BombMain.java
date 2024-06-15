package bomb;

import common.IntList;

public class BombMain {
    public static void answers(String[] args) {
        int phase = 2;
        if (args.length > 0) {
            phase = Integer.parseInt(args[0]);
        }
        Bomb b = new Bomb();
        if (phase >= 0) {
            b.phase0("39391226");
        }
        if (phase >= 1) {
            b.phase1(new IntList(0, new IntList(9, new IntList(3, new IntList(0, new IntList(8, null))))));
        }
        if (phase >= 2) {
            b.phase2("-81201430");
        }
    }
}
