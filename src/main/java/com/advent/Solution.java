package com.advent;
import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

class LogEntry {

    /**
     * Represents an entry from a single log line. Log lines look like this in the file:
     *
     * 34400.409 SXY288 210E ENTRY
     *
     * Where:
     * * 34400.409 is the timestamp in seconds since the software was started.
     * * SXY288 is the license plate of the vehicle passing through the toll booth.
     * * 210E is the location and traffic direction of the toll booth. Here, the toll
     *     booth is at 210 kilometers from the start of the tollway, and the E indicates
     *     that the toll booth was on the east-bound traffic side. Tollbooths are placed
     *     every ten kilometers.
     * * ENTRY indicates which type of toll booth the vehicle went through. This is one of
     *     "ENTRY", "EXIT", or "MAINROAD".
     **/

/*
We are interested in how many people are using the highway, and so we would like to count how many complete journeys are taken in the log file.

A complete journey consists of:
* A driver entering the highway through an ENTRY toll booth.
* The driver passing through some number of MAINROAD toll booths (possibly 0).
* The driver exiting the highway through an EXIT toll booth.

For example, the following log lines represent a single complete journey:

90750.191 JOX304 250E ENTRY
91081.684 JOX304 260E MAINROAD
91483.251 JOX304 270E MAINROAD
91874.493 JOX304 280E EXIT

You may assume that the log only contains complete journeys, and there are no missing entries.

2-1) Write a function in LogFile named countJourneys() that returns how many
     complete journeys there are in the given LogFile.
*/

/*
We would like to catch people who are driving at unsafe speeds on the highway. To help us do that, we would like to identify journeys where a driver does either of the following:
* Drive an average of 130 km/h or greater in any individual 10km segment of tollway.
* Drive an average of 120 km/h or greater in any two 10km segments of tollway.

For example, consider the following journey:
1000.000 TST002 270W ENTRY
1275.000 TST002 260W EXIT

In this case, the driver of TST002 drove 10 km in 275 seconds. We can calculate
that this driver drove an average speed of ~130.91km/hr over this segment:

10 km * 3600 sec/hr
------------------- = 130.91 km/hr
      275 sec

Note that:
* A license plate may have multiple journeys in one file, and if they drive at unsafe speeds in both journeys, both should be counted.
* We do not mark speeding if they are not on the highway (i.e. for any driving between an EXIT and ENTRY event).
* Speeding is only marked once per journey. For example, if there are 4 segments 120km/h or greater, or multiple segments 130km/h or greater, the journey is only counted once.

3-1) Write a function catchSpeeders in LogFile that returns a collection of license plates that drove at unsafe speeds during a journey in the LogFile.
     If the same license plate drives at unsafe speeds during two different journeys, the license plate should appear twice (once for each journey they drove at unsafe speeds).
*/

    private final Float timestamp;
    private final String licensePlate;
    private final String boothType;
    private final int location;
    private final String direction;

    public LogEntry(String logLine) {
        String[] tokens = logLine.split(" ");
        this.timestamp = Float.parseFloat(tokens[0]);
        this.licensePlate = tokens[1];
        this.boothType = tokens[3];
        this.location =
                Integer.parseInt(tokens[2].substring(0, tokens[2].length() - 1));
        String directionLetter = tokens[2].substring(tokens[2].length() - 1);
        if (directionLetter.equals("E")) {
            this.direction = "EAST";
        } else if (directionLetter.equals("W")) {
            this.direction = "WEST";
        } else {
            throw new IllegalArgumentException();
        }
    }

    public Float getTimestamp() {
        return timestamp;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getBoothType() {
        return boothType;
    }

    public int getLocation() {
        return location;
    }

    public String getDirection() {
        return direction;
    }

    @Override
    public String toString() {
        return String.format(
                "<LogEntry timestamp: %f  license: %s  location: %d  direction: %s  booth type: %s>",
                timestamp,
                licensePlate,
                location,
                direction,
                boothType
        );
    }
}

class LogFile {

    /*
     * Represents a file containing a number of log lines, converted to LogEntry
     * objects.
     */

    List<LogEntry> logEntries;
    Map<String, LastSpeedDirection> lastSpeed;
    Map<String, LogEntry> lastLogEntries;
    Set<String> eastWardViolations;
    Set<String> westWardViolations;
    Integer countJourney;

    public LogFile(BufferedReader reader) throws IOException {
        this.logEntries = new ArrayList<>();
        this.lastLogEntries = new HashMap<>();
        this.lastSpeed = new HashMap<>();
        this.eastWardViolations = new HashSet<>();
        this.westWardViolations = new HashSet<>();
        this.countJourney = 0;
        String line = reader.readLine();
        while (line != null) {
            LogEntry logEntry = new LogEntry(line.strip());
            this.logEntries.add(logEntry);
            if(logEntry.getBoothType().equals("EXIT")) {
                this.countJourney += 1;
            }
            updateSpeed(logEntry);
            line = reader.readLine();
        }
    }

    private void updateSpeed(LogEntry logEntry) {
        if(this.lastLogEntries.containsKey(logEntry.getLicensePlate())) {
            LogEntry lastLogEntry = this.lastLogEntries.get(logEntry.getLicensePlate());
            Float timeDelta = logEntry.getTimestamp() - lastLogEntry.getTimestamp();
            float speed = (10f * 3600f)/timeDelta;
            if(speed > 130f) {
                violationDirection(logEntry.getDirection()).add(logEntry.getLicensePlate());
            } else if(speed > 120f) {
                if(this.lastSpeed.containsKey(logEntry.getLicensePlate()))  {
                    LastSpeedDirection lastSpeedDirection = this.lastSpeed.get(logEntry.getLicensePlate());
                    if(lastSpeedDirection.speed() >= 120f
                            && lastSpeedDirection.direction().equals(logEntry.getDirection())) {
                        violationDirection(logEntry.getDirection()).add(logEntry.getLicensePlate());
                    }
                }
            }
            this.lastSpeed.put(logEntry.getLicensePlate(), new LastSpeedDirection(speed, logEntry.getDirection()));
        }
        this.lastLogEntries.put(logEntry.getLicensePlate(), logEntry);
    }

    private Set<String> violationDirection(String direction) {
        return direction.equals("EAST") ? eastWardViolations : westWardViolations;
    }

    public List<String> catchSpeeders() {
        List<String> violations = new ArrayList<>();
        violations.addAll(this.eastWardViolations);
        violations.addAll(this.westWardViolations);
        return violations;
    }
    public LogEntry get(int index) {
        return this.logEntries.get(index);
    }

    public int size() {
        return this.logEntries.size();
    }
    public int countJourneys() {
        return countJourney;
    }
    
    private record LastSpeedDirection(Float speed, String direction){};
}
public class Solution {

    public static void main(String[] argv) throws IOException {
        testLogFile();
        testLogEntry();
        testCountJourneys();
        testCatchSpeeders();
    }

    public static void testLogFile() throws IOException {
        System.out.println("Running testLogFile");
        try (
                BufferedReader reader = new BufferedReader(
                        new FileReader("/content/test/tollbooth_small.log")
                );
        ) {
            LogFile log_file = new LogFile(reader);
            assertEquals(13, log_file.size());
            for (LogEntry entry : log_file.logEntries) {
                assert (entry instanceof LogEntry);
            }
        }
    }

    public static void testLogEntry() {
        System.out.println("Running testLogEntry");
        String log_line = "44776.619 KTB918 310E MAINROAD";
        LogEntry log_entry = new LogEntry(log_line);
        assertEquals(44776.619f, log_entry.getTimestamp(), 0.0001);
        //assertEquals(44776.619, log_entry.getTimestamp());
        assertEquals("KTB918", log_entry.getLicensePlate());
        assertEquals(310, log_entry.getLocation());
        assertEquals("EAST", log_entry.getDirection());
        assertEquals("MAINROAD", log_entry.getBoothType());
        log_line = "52160.132 ABC123 400W ENTRY";
        log_entry = new LogEntry(log_line);
        assertEquals(52160.132f, log_entry.getTimestamp(), 0.0001);
        //assertEquals(52160.132, log_entry.getTimestamp());
        assertEquals("ABC123", log_entry.getLicensePlate());
        assertEquals(400, log_entry.getLocation());
        assertEquals("WEST", log_entry.getDirection());
        assertEquals("ENTRY", log_entry.getBoothType());
    }
    public static void testCountJourneys() throws IOException {
        System.out.println("Running testCountJourneys");
        try (BufferedReader reader = new BufferedReader(new FileReader("/content/test/tollbooth_small.log"))) {
            LogFile logFile = new LogFile(reader);
            assertEquals(3, logFile.countJourneys());
        }

        try (BufferedReader reader = new BufferedReader(new FileReader("/content/test/tollbooth_medium.log"))) {
            LogFile logFile = new LogFile(reader);
            assertEquals(63, logFile.countJourneys());
        }
    }
    public static void testCatchSpeeders() throws IOException {
        System.out.println("Running testCatchSpeeders");
        try (BufferedReader reader = new BufferedReader(new FileReader("/content/test/tollbooth_speeders.log"))) {
            LogFile logFile = new LogFile(reader);
            List<String> ticketList = logFile.catchSpeeders();
            // ticketList should be a list similar to
            // ["TST002", "TST003", "TST003"]
            // In this case, TST002 had one journey with unsafe driving, and
            // TST003 had two journeys with unsafe driving. The license plates
            // may be in any order.
            Map<String, Integer> ticketCounts = new HashMap<>();
            for (String ticket : ticketList) {
                ticketCounts.put(ticket, ticketCounts.getOrDefault(ticket, 0) + 1);
            }
            assertEquals(1, (int) ticketCounts.get("TST002"));
            assertEquals(2, (int) ticketCounts.get("TST003"));
            assertEquals(2, ticketCounts.size());
        }

        try (BufferedReader reader = new BufferedReader(new FileReader("/content/test/tollbooth_medium.log"))) {
            LogFile logFile = new LogFile(reader);
            List<String> ticketList = logFile.catchSpeeders();
            assertEquals(10, ticketList.size());
        }

        try (BufferedReader reader = new BufferedReader(new FileReader("/content/test/tollbooth_long.log"))) {
            LogFile logFile = new LogFile(reader);
            List<String> ticketList = logFile.catchSpeeders();
            assertEquals(129, ticketList.size());
        }
    }
}
