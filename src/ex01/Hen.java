package ex01;

public class Hen implements Runnable {
    private final int count;

    public Hen(int count) {
        this.count = count;
    }

    @Override
    public void run() {
        for (int i = 0; i < count; i++) {
            Program.PrintHen();
        }
    }
}
