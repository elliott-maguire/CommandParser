package cscd350f20project.ParserCreate;

import cs350f20project.controller.cli.TrackLocator;
import cs350f20project.controller.cli.parser.MyParserHelper;
import cs350f20project.controller.command.A_Command;
import cs350f20project.controller.command.creational.*;
import cs350f20project.datatype.CoordinatesWorld;

public class Power {

    private MyParserHelper myHelper;

    public Power(MyParserHelper myHelper){
        this.myHelper = myHelper;
    }

    public void process(String CMD) {
        String[] Command = CMD.split(" ");
        String type = Command[2];

        switch(type.toLowerCase()){
            case "pole"->{
                TrackLocator TL = new TrackLocator(Command[6],Double.parseDouble(Command[8]), Command[10].equals("true"));
                A_Command c = new CommandCreatePowerPole(Command[3],TL);
                this.myHelper.getActionProcessor().schedule(c);

            }
            case "catenary" ->{
               // A_Command c = new CommandCreatePowerCatenary(Command[3],Command[6]);
               // this.myHelper.getActionProcessor().schedule(c);

            }
            case "station" -> {
                handleStation(Command);
            }
            case "substation" ->{
                handleSubstation(Command);
            }
        }
    }

    private void handleStation(String[] command) {
        CoordinatesWorld worldC;
        if( command[5].charAt(0) == '$'){
            myHelper.getReference(command[5]);
        }
        else{
         //   worldC =(CoordinatesWorld)command[5];
        }
       // A_Command c = new CommandCreatePowerStation(command[])
    }

    private void handleSubstation(String[] command) {
        CoordinatesWorld worldC;
        if( command[5].charAt(0) == '$'){
            myHelper.getReference(command[5]);
        }
    }
}
