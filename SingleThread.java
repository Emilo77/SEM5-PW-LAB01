package przyklady01;

public class SingleThread {

    public static void main(String[] args) {
        Thread t = Thread.currentThread();
        t.setName("Główny");
        System.out.println("Wątek: " + t);
        System.out.println("Nazwa: " + t.getName());
        System.out.println("Priorytet: " + t.getPriority());
        System.out.println("Grupa: " + t.getThreadGroup().getName());
    }

}
