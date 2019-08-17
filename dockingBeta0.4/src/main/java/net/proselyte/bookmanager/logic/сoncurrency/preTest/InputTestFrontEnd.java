package net.proselyte.bookmanager.logic.сoncurrency.preTest;

public class InputTestFrontEnd {
    long time;
    boolean state;

    public InputTestFrontEnd(long time) {
        this.time = time;
        state=false;
    }
    public long getTime() {
        return time;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}
