package cscd350f20project.ParserCreate;

import cs350f20project.controller.cli.parser.MyParserHelper;
import cs350f20project.controller.command.A_Command;
import cs350f20project.controller.command.PointLocator;
import cs350f20project.controller.command.creational.*;
import cs350f20project.datatype.*;

public class Track {

    MyParserHelper myHelper;

    public Track(MyParserHelper myHelper) {
        this.myHelper = myHelper;
    }

    public void process(String command) {
        String[] words = command.split(" ");

        switch (words[2].toLowerCase()) {
            case "drawbridge" -> {
                handleDrawBridge(words);
            }

            case "bridge" -> {
                handleBridge(words);
            }

            case "crossing" -> {
                handleCrossing(words);
            }

            case "curve" -> {
                handleCurved(words);
            }

            case "end" -> {
                handleTrackEnd(words);
            }

            case "crossover" -> {
                handleCrossover(words);
            }

            case "roundhouse" -> {
                handleRoundhouse(words);
            }

            case "straight" -> {
                handleTrackStraight(words);
            }
        }

        switch (words[3].toLowerCase()) {
            case "switch" -> {
                handleTrackSwitchTurnout(words);
            }

            case "wye" -> {
                handleTrackSwitchWye(words);
            }
        }
    }

    private void handleDrawBridge(String[] command) {
        String id = command[4];
        CoordinatesWorld worldC;
        String[] deltaStart = command[9].split(":");
        String[] deltaEnd = command[11].split(":");
        CoordinatesDelta startDelta = new CoordinatesDelta(Double.parseDouble(deltaStart[0]),
                Double.parseDouble(deltaStart[1]));
        CoordinatesDelta endDelta = new CoordinatesDelta(Double.parseDouble(deltaEnd[0]),
                Double.parseDouble(deltaEnd[1]));
        Angle angle = new Angle(Double.parseDouble(command[13]));
        PointLocator pointLocator;

        if (command[6].charAt(0) == '$') {
            worldC = myHelper.getReference(command[6]);
        } else {
            worldC = handleWorldCoord(command[6]);
        }
        pointLocator = new PointLocator(worldC, startDelta, endDelta);
        A_Command c = new CommandCreateTrackBridgeDraw(id, pointLocator, angle);
        this.myHelper.getActionProcessor().schedule(c);

    }

    private void handleBridge(String[] command) {
        String id = command[3];
        CoordinatesWorld worldC;
        String[] deltaStart = command[8].split(":");
        String[] deltaEnd = command[10].split(":");
        CoordinatesDelta startDelta = new CoordinatesDelta(Double.parseDouble(deltaStart[0]),
                Double.parseDouble(deltaStart[1]));
        CoordinatesDelta endDelta = new CoordinatesDelta(Double.parseDouble(deltaEnd[0]),
                Double.parseDouble(deltaEnd[1]));
        PointLocator pointLocator;

        if (command[5].charAt(0) == '$') {
            worldC = myHelper.getReference(command[5]);
        } else {
            worldC = handleWorldCoord(command[5]);
        }
        pointLocator = new PointLocator(worldC, startDelta, endDelta);

        A_Command c = new CommandCreateTrackBridgeFixed(id, pointLocator);
        this.myHelper.getActionProcessor().schedule(c);
    }

    private void handleCrossing(String[] command) {
        String id = command[3];
        CoordinatesWorld worldC;
        String[] deltaStart = command[8].split(":");
        String[] deltaEnd = command[10].split(":");
        CoordinatesDelta startDelta = new CoordinatesDelta(Double.parseDouble(deltaStart[0]),
                Double.parseDouble(deltaStart[1]));
        CoordinatesDelta endDelta = new CoordinatesDelta(Double.parseDouble(deltaEnd[0]),
                Double.parseDouble(deltaEnd[1]));
        PointLocator pointLocator;

        if (command[5].charAt(0) == '$') {
            worldC = myHelper.getReference(command[5]);
        } else {
            worldC = handleWorldCoord(command[5]);
        }
        pointLocator = new PointLocator(worldC, startDelta, endDelta);

        A_Command c = new CommandCreateTrackCrossing(id, pointLocator);
        this.myHelper.getActionProcessor().schedule(c);
    }

    private void handleCrossover(String[] command) {
        String id = command[3];
        CoordinatesWorld worldC;
        String[] deltaStart = command[8].split(":");
        String[] deltaEnd = command[10].split(":");
        String[] deltaStartSecond = command[12].split(":");
        String[] deltaEndSecond = command[14].split(":");
        CoordinatesDelta startDelta = new CoordinatesDelta(Double.parseDouble(deltaStart[0]),
                Double.parseDouble(deltaStart[1]));
        CoordinatesDelta endDelta = new CoordinatesDelta(Double.parseDouble(deltaEnd[0]),
                Double.parseDouble(deltaEnd[1]));
        CoordinatesDelta startSecondDelta = new CoordinatesDelta(Double.parseDouble(deltaStartSecond[0]),
                Double.parseDouble(deltaStartSecond[1]));
        CoordinatesDelta endSecondDelta = new CoordinatesDelta(Double.parseDouble(deltaEndSecond[0]),
                Double.parseDouble(deltaEndSecond[1]));

        if (command[5].charAt(0) == '$') {
            worldC = myHelper.getReference(command[5]);
        } else {
            worldC = handleWorldCoord(command[5]);
        }

        A_Command c = new CommandCreateTrackCrossover(id, worldC, startDelta, endDelta, startSecondDelta,
                endSecondDelta);
        this.myHelper.getActionProcessor().schedule(c);
    }

    private void handleCurved(String[] command) {
        String id = command[3];
        CoordinatesWorld worldC;
        String[] deltaStart = command[8].split(":");
        String[] deltaEnd = command[10].split(":");
        CoordinatesDelta startDelta = new CoordinatesDelta(Double.parseDouble(deltaStart[0]),
                Double.parseDouble(deltaStart[1]));
        CoordinatesDelta endDelta = new CoordinatesDelta(Double.parseDouble(deltaEnd[0]),
                Double.parseDouble(deltaEnd[1]));
        A_Command c;

        if (command[5].charAt(0) == '$') {
            worldC = myHelper.getReference(command[5]);
        } else {
            worldC = handleWorldCoord(command[5]);
        }

        if (command[11].equalsIgnoreCase("Distance")) {
            double dist = Double.parseDouble(command[13]);
            c = new CommandCreateTrackCurve(id, worldC, startDelta, endDelta, dist);
        } else {
            String[] deltaThird = command[10].split(":");
            CoordinatesDelta thirdDelta = new CoordinatesDelta(Double.parseDouble(deltaThird[0]),
                    Double.parseDouble(deltaThird[1]));
            c = new CommandCreateTrackCurve(id, worldC, startDelta, endDelta, thirdDelta);
        }

        this.myHelper.getActionProcessor().schedule(c);

    }

    private void handleTrackEnd(String[] command) {
        String id = command[3];
        CoordinatesWorld worldC;
        String[] deltaStart = command[8].split(":");
        String[] deltaEnd = command[10].split(":");
        CoordinatesDelta startDelta = new CoordinatesDelta(Double.parseDouble(deltaStart[0]),
                Double.parseDouble(deltaStart[1]));
        CoordinatesDelta endDelta = new CoordinatesDelta(Double.parseDouble(deltaEnd[0]),
                Double.parseDouble(deltaEnd[1]));
        PointLocator pointLocator;

        if (command[5].charAt(0) == '$') {
            worldC = myHelper.getReference(command[5]);
        } else {
            worldC = handleWorldCoord(command[5]);
        }
        pointLocator = new PointLocator(worldC, startDelta, endDelta);
        A_Command c = new CommandCreateTrackEnd(id, pointLocator);
        this.myHelper.getActionProcessor().schedule(c);
    }

    // CREATE TRACK ROUNDHOUSE id1 REFERENCE ( coordinates_world | ( '$' id2 ) )
    // DELTA ORIGIN coordinates_delta1 ANGLE ENTRY angle START angle2 END angle3
    // WITH integer SPURS LENGTH number1 TURNTABLE LENGTH number2
    private void handleRoundhouse(String[] command) {
        String id1 = command[3];

        CoordinatesWorld coordinatesWorld;
        if (command[5].contains("$")) {
            coordinatesWorld = myHelper.getReference(command[5]);
        } else {
            coordinatesWorld = handleWorldCoord(command[5]);
        }

        CoordinatesDelta coordinatesDelta1 = new CoordinatesDelta(Integer.parseInt(command[8].split(":")[0]),
                Integer.parseInt(command[8].split(":")[1]));

        Angle angle1 = new Angle(Double.parseDouble(command[11]));
        Angle angle2 = new Angle(Double.parseDouble(command[13]));
        Angle angle3 = new Angle(Double.parseDouble(command[15]));

        int spurs = Integer.parseInt(command[17]);
        double length1 = Double.parseDouble(command[20]);
        double length2 = Double.parseDouble(command[23]);

        A_Command c = new CommandCreateTrackRoundhouse(id1, coordinatesWorld, coordinatesDelta1, angle1, angle2, angle3,
                spurs, length1, length2);
        this.myHelper.getActionProcessor().schedule(c);
    }

    // CREATE TRACK STRAIGHT id1 REFERENCE ( coordinates_world | ( '$' id2 ) )
    // DELTA START coordinates_delta1 END coordinates_delta2
    private void handleTrackStraight(String[] command) {
        String id1 = command[3];

        CoordinatesWorld coordinatesWorld;
        if (command[5].contains("$")) {
            coordinatesWorld = myHelper.getReference(command[5]);
        } else {
            coordinatesWorld = handleWorldCoord(command[5]);
        }

        String[] deltaStart = command[8].split(":");
        String[] deltaEnd = command[10].split(":");
        CoordinatesDelta startDelta = new CoordinatesDelta(Double.parseDouble(deltaStart[0]),
                Double.parseDouble(deltaStart[1]));
        CoordinatesDelta endDelta = new CoordinatesDelta(Double.parseDouble(deltaEnd[0]),
                Double.parseDouble(deltaEnd[1]));

        PointLocator pointLocator = new PointLocator(coordinatesWorld, startDelta, endDelta);

        A_Command c = new CommandCreateTrackStraight(id1, pointLocator);
        this.myHelper.getActionProcessor().schedule(c);
    }

    // CREATE TRACK SWITCH TURNOUT id1 REFERENCE ( coordinates_world | ( '$' id2 ) )
    // STRAIGHT DELTA START coordinates_delta1 END coordinates_delta2 CURVE DELTA
    // START coordinates_delta3 END coordinates_delta4 DISTANCE ORIGIN number
    private void handleTrackSwitchTurnout(String[] command) {
        String id1 = command[4];

        CoordinatesWorld coordinatesWorld;
        if (command[6].contains("$")) {
            coordinatesWorld = myHelper.getReference(command[6]);
        } else {
            coordinatesWorld = handleWorldCoord(command[6]);
        }

        String[] deltaStart1 = command[10].split(":");
        String[] deltaEnd1 = command[12].split(":");
        CoordinatesDelta startDelta1 = new CoordinatesDelta(Double.parseDouble(deltaStart1[0]),
                Double.parseDouble(deltaStart1[1]));
        CoordinatesDelta endDelta1 = new CoordinatesDelta(Double.parseDouble(deltaEnd1[0]),
                Double.parseDouble(deltaEnd1[1]));

        String[] deltaStart2 = command[14].split(":");
        String[] deltaEnd2 = command[16].split(":");
        CoordinatesDelta startDelta2 = new CoordinatesDelta(Double.parseDouble(deltaStart2[0]),
                Double.parseDouble(deltaStart2[1]));
        CoordinatesDelta endDelta2 = new CoordinatesDelta(Double.parseDouble(deltaEnd2[0]),
                Double.parseDouble(deltaEnd2[1]));

        Double distance = Double.parseDouble(command[19]);

        CoordinatesDelta delta = ShapeArc.calculateDeltaOrigin(coordinatesWorld, startDelta1, endDelta1, distance);
        A_Command c = new CommandCreateTrackSwitchTurnout(id1, coordinatesWorld, startDelta1, endDelta1, startDelta2,
                endDelta2, delta);
        this.myHelper.getActionProcessor().schedule(c);
    }

    // CREATE TRACK SWITCH WYE id1 REFERENCE ( coordinates_world | ( '$' id2 ) )
    // DELTA START coordinates_delta1 END coordinates_delta2
    // DISTANCE ORIGIN number1 DELTA START coordinates_delta3 END coordinates_delta4
    // DISTANCE ORIGIN number2
    private void handleTrackSwitchWye(String[] command) {
        String id1 = command[4];

        CoordinatesWorld coordinatesWorld;
        if (command[6].contains("$")) {
            coordinatesWorld = myHelper.getReference(command[6]);
        } else {
            coordinatesWorld = handleWorldCoord(command[6]);
        }

        String[] deltaStart1 = command[9].split(":");
        String[] deltaEnd1 = command[11].split(":");
        CoordinatesDelta startDelta1 = new CoordinatesDelta(Double.parseDouble(deltaStart1[0]),
                Double.parseDouble(deltaStart1[1]));
        CoordinatesDelta endDelta1 = new CoordinatesDelta(Double.parseDouble(deltaEnd1[0]),
                Double.parseDouble(deltaEnd1[1]));

        String[] deltaStart2 = command[17].split(":");
        String[] deltaEnd2 = command[19].split(":");
        CoordinatesDelta startDelta2 = new CoordinatesDelta(Double.parseDouble(deltaStart2[0]),
                Double.parseDouble(deltaStart2[1]));
        CoordinatesDelta endDelta2 = new CoordinatesDelta(Double.parseDouble(deltaEnd2[0]),
                Double.parseDouble(deltaEnd2[1]));

        Double distance1 = Double.parseDouble(command[14]);
        Double distance2 = Double.parseDouble(command[22]);

        CoordinatesDelta delta1 = ShapeArc.calculateDeltaOrigin(coordinatesWorld, startDelta1, endDelta1, distance1);
        CoordinatesDelta delta2 = ShapeArc.calculateDeltaOrigin(coordinatesWorld, startDelta1, endDelta1, distance2);

        A_Command c = new CommandCreateTrackSwitchWye(id1, coordinatesWorld, startDelta1, endDelta1, delta1,
                startDelta2, endDelta2, delta2);
        this.myHelper.getActionProcessor().schedule(c);
    }

    private CoordinatesWorld handleWorldCoord(String coordIn) {

        Longitude lon;
        Latitude lat;

        String[] coordsSplit = coordIn.split("//");
        String[] latSplit = coordsSplit[0].split("/*|'|" + '"');
        String[] lonSplit = coordsSplit[1].split("/*|'|" + '"');

        lat = new Latitude(Integer.parseInt(latSplit[0]), Integer.parseInt(latSplit[1]), Integer.parseInt(latSplit[2]));
        lon = new Longitude(Integer.parseInt(lonSplit[0]), Integer.parseInt(lonSplit[1]),
                Integer.parseInt(lonSplit[2]));

        return new CoordinatesWorld(lat, lon);
    }
}
