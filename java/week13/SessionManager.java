package week13;

import java.util.concurrent.ConcurrentHashMap; //A thread safe map that allows concurrent access
import java.util.concurrent.Executors; //Provides methods to create thread pools and schedulers.
import java.util.concurrent.TimeUnit; //Helps specify time units (seconds, milliseconds)

public class SessionManager { //Defines the class SessionManager that manages user sessions.
    private static final ConcurrentHashMap<String, Long> sessions = new ConcurrentHashMap<>(); //A thread safe map to store session IDs (String) and their last activity time (Long timestamp).
    private static final long SESSION_TIMEOUT = 10_000; //Timeout limit, If a session is inactive for over 10 seconds

    // Simulate user activity
    public static void updateSession(String sessionId) { //Simulate a user interacting with the system.
        sessions.put(sessionId, System.currentTimeMillis());//Add or updates the session in the map with the current timestamp.
        System.out.println("Updated session: " + sessionId);//Print confirmation that the session was updated
    }

    // Background task to clean up old sessions
    public static void startSessionCleanupTask() { // sets up a background cleanup task that removes expired sessions.
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> { //sets up a background cleanup task that removes expired sessions , schedules the task to repeat at a fixed interval
            long now = System.currentTimeMillis(); // Get the current system time in milliseconds to get compare against session activity timestamps.
            for (String sessionId : sessions.keySet()) { //Loop through all session IDs in the ConcurrentHashMap.
                long lastActive = sessions.get(sessionId);//Get the last activity time of the session.
                if (now - lastActive > SESSION_TIMEOUT) { //If the difference between now and lastActive is greater than the timeout (10 sec), the session is expired.
                    sessions.remove(sessionId); //Removes the session from the map.
                    System.out.println("Removed expired session: " + sessionId); // Print a message showing which session was removed.


                }
            }
        }, 0, 5, TimeUnit.SECONDS);// runs every 5 second
    }

    // Monitor active sessions
    public static void printSessions() {
        System.out.println("Active Sessions:");
        sessions.forEach((id, time) -> System.out.println(" - " + id + " (Last Active: " + time + ")"));//Prints all remaining active sessions and their last active time.
    }

    public static void main(String[] args) throws InterruptedException { //the main program
        startSessionCleanupTask();//Start the background cleanup thread.

        // Simulate user actions in different threads
        Thread user1 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                updateSession("user1");
                try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
            } //Create user1 thread to simulate activity every 2 seconds, 5 times.
        });

        Thread user2 = new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                updateSession("user2");
                try { Thread.sleep(4000); } catch (InterruptedException ignored) {}
            }//Create user2 thread to simulate activity every 4 seconds, 3 times.
        });

        user1.start();//Starts threads
        user2.start();//Starts threads

        user1.join();//waits for both to complete before moving on.
        user2.join();//waits for both to complete before moving on.

        // Final state
        Thread.sleep(12000); // wait for cleanup to run , wait 12 seconds to ensure expired sessions are removed.
        printSessions(); //display remaining active sessions.
    }
}
