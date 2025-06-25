package trackflight;

import java.util.Scanner;
import java.util.concurrent.locks.ReentrantLock;

// Rename enum to LightColor to avoid conflict with Junction class
enum LightColor {
    RED, YELLOW, GREEN;

    public static LightColor fromString(String s) {
        switch (s.toUpperCase()) {
            case "RED": return RED;
            case "YELLOW": return YELLOW;
            case "GREEN": return GREEN;
            default: return null;
        }
    }
}

class TrafficLight {
    private LightColor color;

    public TrafficLight() {
        this.color = LightColor.RED; // default red
    }

    public synchronized LightColor getColor() {
        return color;
    }

    public synchronized void setColor(LightColor color) {
        this.color = color;
        System.out.println("Traffic light set to " + color);
    }
}

public class Junction {
    private final String name;
    private final ReentrantLock lock = new ReentrantLock();
    private final TrafficLight trafficLight;

    public Junction(String name, TrafficLight trafficLight) {
        this.name = name;
        this.trafficLight = trafficLight;
    }

    public void enter(String vehicleName) throws InterruptedException {
        while (true) {
            LightColor current = trafficLight.getColor();

            if (current == LightColor.RED) {
                System.out.println(vehicleName + " sees RED at " + name + " and is STOPPING.");
                Thread.sleep(500);
            } else if (current == LightColor.YELLOW) {
                System.out.println(vehicleName + " sees YELLOW at " + name + " and is WAITING.");
                Thread.sleep(300);
            } else {
                lock.lock();
                System.out.println(vehicleName + " ENTERED junction " + name);
                break;
            }
        }
    }

    public void exit(String vehicleName) {
        System.out.println(vehicleName + " EXITED junction " + name);
        lock.unlock();
    }

    // Vehicle class moved inside same file for simplicity
    static class Vehicle extends Thread {
        private final String name;
        private final Junction[] path;

        public Vehicle(String name, Junction[] path) {
            this.name = name;
            this.path = path;
        }

        @Override
        public void run() {
            try {
                for (Junction junction : path) {
                    Thread.sleep((long) (Math.random() * 1000));
                    junction.enter(name);
                    Thread.sleep((long) (Math.random() * 1000));
                    junction.exit(name);
                }
            } catch (InterruptedException e) {
                System.out.println(name + " was interrupted.");
            }
        }
    }

    // Main method
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Create traffic lights
        TrafficLight tl1 = new TrafficLight();
        TrafficLight tl2 = new TrafficLight();
        TrafficLight tl3 = new TrafficLight();
        TrafficLight tl4 = new TrafficLight();

        // Create junctions
        Junction j1 = new Junction("J1", tl1);
        Junction j2 = new Junction("J2", tl2);
        Junction j3 = new Junction("J3", tl3);
        Junction j4 = new Junction("J4", tl4);

        // Create vehicles
        Vehicle v1 = new Vehicle("Vehicle-A", new Junction[]{j1, j2, j3});
        Vehicle v2 = new Vehicle("Vehicle-B", new Junction[]{j2, j3, j4});
        Vehicle v3 = new Vehicle("Vehicle-C", new Junction[]{j1, j4});

        // Start vehicle threads
        v1.start();
        v2.start();
        v3.start();

        // Control traffic lights interactively
        while (true) {
            System.out.println("\nSet traffic light colors (RED, YELLOW, GREEN)");
            System.out.print("Junction 1: ");
            String c1 = scanner.nextLine();
            LightColor color1 = LightColor.fromString(c1);

            System.out.print("Junction 2: ");
            String c2 = scanner.nextLine();
            LightColor color2 = LightColor.fromString(c2);

            System.out.print("Junction 3: ");
            String c3 = scanner.nextLine();
            LightColor color3 = LightColor.fromString(c3);

            System.out.print("Junction 4: ");
            String c4 = scanner.nextLine();
            LightColor color4 = LightColor.fromString(c4);

            if (color1 != null) tl1.setColor(color1);
            else System.out.println("Invalid input for Junction 1");
            if (color2 != null) tl2.setColor(color2);
            else System.out.println("Invalid input for Junction 2");
            if (color3 != null) tl3.setColor(color3);
            else System.out.println("Invalid input for Junction 3");
            if (color4 != null) tl4.setColor(color4);
            else System.out.println("Invalid input for Junction 4");
        }
    }
}

