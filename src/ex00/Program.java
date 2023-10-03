package ex00;

public class Program {
    public static void main(String[] args) {
        if(args.length != 1 || !args[0].startsWith("--count=")) {
            System.err.println("The argument must begin with \"--count=*count of times*\"");
            System.exit(-1);
        } else {
            try {
                int count = Integer.parseInt(args[0].replace("--count=", ""));
                if (count > 0) {
                    Egg egg = new Egg(count);
                    Hen hen = new Hen(count);

                    egg.start();
                    hen.start();

                    egg.join();
                    hen.join();

                    for (int i = 0; i < count; i++) {
                        System.out.println("Human");
                    }
                }
            } catch (RuntimeException exception) {
                System.err.println("Enter count > 0");
            } catch (InterruptedException e) {
                System.err.println("Threads error");
            }
        }

    }
}
