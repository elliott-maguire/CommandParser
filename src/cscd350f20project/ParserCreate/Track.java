package cscd350f20project.ParserCreate;

import cs350f20project.controller.cli.parser.MyParserHelper;
import cs350f20project.controller.command.A_Command;
import cs350f20project.controller.command.PointLocator;
import cs350f20project.controller.command.creational.*;
import cs350f20project.datatype.*;

import java.util.ArrayList;

public class Track {

    MyParserHelper myHelper;

    public Track(MyParserHelper myHelper){
        this.myHelper = myHelper;
    }


    private void handleDrawBridge(String [] command){
        String id = command[4];
        CoordinatesWorld worldC;
        String[] deltaStart = command[9].split(":");
        String[] deltaEnd = command[11].split(":");
        CoordinatesDelta startDelta = new CoordinatesDelta(Double.parseDouble(deltaStart[0]),Double.parseDouble(deltaStart[1]));
        CoordinatesDelta endDelta = new CoordinatesDelta(Double.parseDouble(deltaEnd[0]),Double.parseDouble(deltaEnd[1]));
        Angle angle = new Angle(Double.parseDouble(command[13]));
        PointLocator pointLocator;

        if (command[6].charAt(0) == '$') {
            worldC = myHelper.getReference(command[6]);
        } else {
            worldC = handleWorldCoord(command[6]);
        }
        pointLocator = new PointLocator(worldC,startDelta,endDelta);
        A_Command c = new CommandCreateTrackBridgeDraw(id,pointLocator,angle);
        this.myHelper.getActionProcessor().schedule(c);

    }
    private void handleBridge(String [] command){
        String id = command[3];
        CoordinatesWorld worldC;
        String[] deltaStart = command[8].split(":");
        String[] deltaEnd = command[10].split(":");
        CoordinatesDelta startDelta = new CoordinatesDelta(Double.parseDouble(deltaStart[0]),Double.parseDouble(deltaStart[1]));
        CoordinatesDelta endDelta = new CoordinatesDelta(Double.parseDouble(deltaEnd[0]),Double.parseDouble(deltaEnd[1]));
        PointLocator pointLocator;

        if (command[5].charAt(0) == '$') {
            worldC = myHelper.getReference(command[5]);
        } else {
            worldC = handleWorldCoord(command[5]);
        }
        pointLocator = new PointLocator(worldC,startDelta,endDelta);

        A_Command c = new CommandCreateTrackBridgeFixed(id,pointLocator);
        this.myHelper.getActionProcessor().schedule(c);
    }

    private void handleCrossing(String [] command){
        String id = command[3];
        CoordinatesWorld worldC;
        String[] deltaStart = command[8].split(":");
        String[] deltaEnd = command[10].split(":");
        CoordinatesDelta startDelta = new CoordinatesDelta(Double.parseDouble(deltaStart[0]),Double.parseDouble(deltaStart[1]));
        CoordinatesDelta endDelta = new CoordinatesDelta(Double.parseDouble(deltaEnd[0]),Double.parseDouble(deltaEnd[1]));
        PointLocator pointLocator;

        if (command[5].charAt(0) == '$') {
            worldC = myHelper.getReference(command[5]);
        } else {
            worldC = handleWorldCoord(command[5]);
        }
        pointLocator = new PointLocator(worldC,startDelta,endDelta);

        A_Command c = new CommandCreateTrackCrossing(id,pointLocator);
        this.myHelper.getActionProcessor().schedule(c);
    }

    private void handleCrossover(String [] command){
        String id = command[3];
        CoordinatesWorld worldC;
        String[] deltaStart = command[8].split(":");
        String[] deltaEnd = command[10].split(":");
        String[] deltaStartSecond = command[12].split(":");
        String[] deltaEndSecond = command[14].split(":");
        CoordinatesDelta startDelta = new CoordinatesDelta(Double.parseDouble(deltaStart[0]),Double.parseDouble(deltaStart[1]));
        CoordinatesDelta endDelta = new CoordinatesDelta(Double.parseDouble(deltaEnd[0]),Double.parseDouble(deltaEnd[1]));
        CoordinatesDelta startSecondDelta = new CoordinatesDelta(Double.parseDouble(deltaStartSecond[0]),Double.parseDouble(deltaStartSecond[1]));
        CoordinatesDelta endSecondDelta = new CoordinatesDelta(Double.parseDouble(deltaEndSecond[0]),Double.parseDouble(deltaEndSecond[1]));

        if (command[5].charAt(0) == '$') {
            worldC = myHelper.getReference(command[5]);
        } else {
            worldC = handleWorldCoord(command[5]);
        }

        A_Command c = new CommandCreateTrackCrossover(id,worldC,startDelta,endDelta,startSecondDelta,endSecondDelta);
        this.myHelper.getActionProcessor().schedule(c);
    }


    private void handleCurved(String [] command) {
        String id = command[3];
        CoordinatesWorld worldC;
        String[] deltaStart = command[8].split(":");
        String[] deltaEnd = command[10].split(":");
        CoordinatesDelta startDelta = new CoordinatesDelta(Double.parseDouble(deltaStart[0]), Double.parseDouble(deltaStart[1]));
        CoordinatesDelta endDelta = new CoordinatesDelta(Double.parseDouble(deltaEnd[0]), Double.parseDouble(deltaEnd[1]));
        PointLocator pointLocator;
        A_Command c;

        if (command[5].charAt(0) == '$') {
            worldC = myHelper.getReference(command[5]);
        } else {
            worldC = handleWorldCoord(command[5]);
        }

        if(command[11].equalsIgnoreCase("Distance")){
            double dist =Double.parseDouble(command[13]);
           c = new CommandCreateTrackCurve(id,worldC,startDelta,endDelta,dist);
        }
        else{
            String[] deltaThird = command[10].split(":");
            CoordinatesDelta thirdDelta = new CoordinatesDelta(Double.parseDouble(deltaThird[0]), Double.parseDouble(deltaThird[1]));
            c = new CommandCreateTrackCurve(id,worldC,startDelta,endDelta,thirdDelta);
        }
        
        this.myHelper.getActionProcessor().schedule(c);


    }

    private void handleTrackEnd(String [] command) {
        String id = command[3];
        CoordinatesWorld worldC;
        String[] deltaStart = command[8].split(":");
        String[] deltaEnd = command[10].split(":");
        CoordinatesDelta startDelta = new CoordinatesDelta(Double.parseDouble(deltaStart[0]), Double.parseDouble(deltaStart[1]));
        CoordinatesDelta endDelta = new CoordinatesDelta(Double.parseDouble(deltaEnd[0]), Double.parseDouble(deltaEnd[1]));
        PointLocator pointLocator;

        if (command[5].charAt(0) == '$') {
            worldC = myHelper.getReference(command[5]);
        } else {
            worldC = handleWorldCoord(command[5]);
        }
        pointLocator = new PointLocator(worldC,startDelta,endDelta);
        A_Command c = new CommandCreateTrackEnd(id,pointLocator);
        this.myHelper.getActionProcessor().schedule(c);

    }

    private CoordinatesWorld handleWorldCoord(String coordIn) {

        Longitude lon;
        Latitude lat;

        String[] coordsSplit = coordIn.split("//");
        String[] latSplit = coordsSplit[0].split("/*|'|" + '"');
        String[] lonSplit = coordsSplit[1].split("/*|'|" + '"');

        lat = new Latitude(Integer.parseInt(latSplit[0]), Integer.parseInt(latSplit[1]),
                Integer.parseInt(latSplit[2]));
        lon = new Longitude(Integer.parseInt(lonSplit[0]), Integer.parseInt(lonSplit[1]),
                Integer.parseInt(lonSplit[2]));

        return new CoordinatesWorld(lat, lon);
    }
}
