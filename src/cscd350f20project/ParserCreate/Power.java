package cscd350f20project.ParserCreate;

import cs350f20project.controller.cli.TrackLocator;
import cs350f20project.controller.cli.parser.MyParserHelper;
import cs350f20project.controller.command.A_Command;
import cs350f20project.controller.command.creational.*;
import cs350f20project.datatype.CoordinatesDelta;
import cs350f20project.datatype.CoordinatesWorld;
import cs350f20project.datatype.Latitude;
import cs350f20project.datatype.Longitude;

import java.util.ArrayList;

public class Power {

    private MyParserHelper myHelper;

    public Power(MyParserHelper myHelper){
        this.myHelper = myHelper;
    }

    public void process(String CMD) {
        String[] Command = CMD.split(" ");
        String type = Command[2];

<<<<<<< Updated upstream
        switch(type.toLowerCase()){
            case "pole"->{
                TrackLocator TL = new TrackLocator(Command[6],Double.parseDouble(Command[8]), Command[10].equals("true"));
                A_Command c = new CommandCreatePowerPole(Command[3],TL);
=======
        switch (type.toLowerCase()) {
            case "pole":
                TrackLocator TL = new TrackLocator(Command[6], Double.parseDouble(Command[8]),
                        Command[10].equals("true"));
                A_Command c = new CommandCreatePowerPole(Command[3], TL);
>>>>>>> Stashed changes
                this.myHelper.getActionProcessor().schedule(c);
                break;
            case "catenary":
                handleCatenary(Command);
                break;

<<<<<<< Updated upstream
            }
            case "catenary" ->{
                A_Command c = new CommandCreatePowerCatenary(Command[3],(Collection)Command[6]);
                this.myHelper.getActionProcessor().schedule(c);

            }
            case "station" -> {
                handleStation(Command);
            }
            case "substation" ->{
=======
            case "station":
                handleStation(Command);
                break;

            case "substation":
>>>>>>> Stashed changes
                handleSubstation(Command);
                break;
        }
    }

    private void handleCatenary(String[] Command) {
        ArrayList catenary = new ArrayList();
        for(int x = 6; x< Command.length;x++)
            catenary.add(Command[x]);

        A_Command c = new CommandCreatePowerCatenary(Command[3],catenary);
        this.myHelper.getActionProcessor().schedule(c);

    }

    private void handleStation(String[] command) {
        String id = command[3];
        CoordinatesWorld worldC;
<<<<<<< Updated upstream
        if( command[5].charAt(0) == '$'){
            myHelper.getReference(command[5]);
        }
        else{
            worldC = (CoordinatesWorld)command[5];
        }
        A_Command c = new CommandCreatePowerStation(command[])
=======
        String[] delta = command[7].split(":");
        CoordinatesDelta coordinatesDelta = new CoordinatesDelta(Double.parseDouble(delta[0]),Double.parseDouble(delta[1]));
        ArrayList catenary = new ArrayList();

        for(int x = 10; x< command.length; x++) {
            catenary.add(command[x]);
        }

        if (command[5].charAt(0) == '$') {
            worldC = myHelper.getReference(command[5]);
        } else {
            worldC = handleWorldCoord(command[5]);
        }
        A_Command c = new CommandCreatePowerSubstation(id,worldC,coordinatesDelta,catenary);
        this.myHelper.getActionProcessor().schedule(c);
>>>>>>> Stashed changes
    }

    private void handleSubstation(String[] command) {
        String id = command[3];
        CoordinatesWorld worldC;
<<<<<<< Updated upstream
        if( command[5].charAt(0) == '$'){
            myHelper.getReference(command[5]);
=======
        String[] delta = command[7].split(":");
        CoordinatesDelta coordinatesDelta = new CoordinatesDelta(Double.parseDouble(delta[0]),Double.parseDouble(delta[1]));
        ArrayList substations = new ArrayList();

        for(int x = 10; x< command.length; x++) {
            substations.add(command[x]);
        }

        if (command[5].charAt(0) == '$') {
           worldC = myHelper.getReference(command[5]);
        }
        else{
            worldC = handleWorldCoord(command[5]);
>>>>>>> Stashed changes
        }

        A_Command c = new CommandCreatePowerSubstation(id,worldC,coordinatesDelta,substations);
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
