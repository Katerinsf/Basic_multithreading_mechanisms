package ex01;

public class Program {
    public static boolean isPrintEgg = true;
    public static void main(String[] args) {
        if(args.length != 1 || !args[0].startsWith("--count=")) {
            System.err.println("The argument must begin with \"--count=*count of times*\"");
            System.exit(-1);
        } else {
            try {
                int count = Integer.parseInt(args[0].replace("--count=", ""));
                if (count > 0) {
                    Thread egg = new Thread(new Egg(count));
                    Thread hen = new Thread(new Hen(count));

                    egg.start();
                    hen.start();
                }
            } catch (RuntimeException exception) {
                System.err.println("Enter count > 0");
            }
        }
    }

    public static synchronized void PrintEgg() {
        if (!isPrintEgg) {
            try {
                Program.class.wait();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println("Egg");
        isPrintEgg = false;
        Program.class.notify();
    }

    public static synchronized void  PrintHen() {
        if (isPrintEgg) {
            try {
                Program.class.wait();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println("Hen");
        isPrintEgg = true;
        Program.class.notify();
    }
}
