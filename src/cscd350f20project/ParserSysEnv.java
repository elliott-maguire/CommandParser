package cscd350f20project;

import cs350f20project.controller.cli.TrackLocator;
import cs350f20project.controller.cli.parser.MyParserHelper;
import cs350f20project.controller.command.A_Command;
import cs350f20project.controller.command.meta.CommandMetaDoExit;
import cs350f20project.controller.command.meta.CommandMetaDoRun;
import cs350f20project.controller.command.meta.CommandMetaViewDestroy;
import cs350f20project.controller.command.meta.CommandMetaViewGenerate;
import cs350f20project.controller.command.structural.CommandStructuralCommit;
import cs350f20project.controller.command.structural.CommandStructuralCouple;
import cs350f20project.controller.command.structural.CommandStructuralLocate;
import cs350f20project.controller.command.structural.CommandStructuralUncouple;
import cs350f20project.datatype.*;

public class ParserSysEnv {
    private final MyParserHelper myParserHelper;

    public ParserSysEnv(MyParserHelper myParserHelper) {
        this.myParserHelper = myParserHelper;
    }

    public void process(String command) {
        if (command.startsWith("@EXIT")) {
            ParseExit(command);
        } else if (command.startsWith("@RUN")) {
            ParseRun(command);
        } else if (command.startsWith("CLOSE VIEW")) {
            ParseCloseView(command);
        } else if (command.startsWith("OPEN VIEW")) {
            ParseOpenView(command);
        } else if (command.startsWith("COMMIT")) {
            ParseCommit(command);
        } else if (command.startsWith("COUPLE STOCK")) {
            ParseCoupleStock(command);
        } else if (command.startsWith("LOCATE STOCK")) {
            ParseLocateStock(command);
        } else if (command.startsWith("UNCOUPLE STOCK")) {
            ParseUncoupleStock(command);
        } else if (command.startsWith("USE")) {
            ParseUseIDAsReference(command);
        }
    }

    // @EXIT
    public void ParseExit(String input) {
        A_Command command = new CommandMetaDoExit();
        myParserHelper.getActionProcessor().schedule(command);
    }

    // @RUN string
    public void ParseRun(String input) {
        String[] words = input.split(" ");

        A_Command command = new CommandMetaDoRun(words[1]);
        myParserHelper.getActionProcessor().schedule(command);
    }

    // CLOSE VIEW id
    public void ParseCloseView(String input) {
        String[] words = input.split(" ");

        A_Command command = new CommandMetaViewDestroy(words[2]);
        myParserHelper.getActionProcessor().schedule(command);
    }

    // COMMIT
    public void ParseCommit(String input) {
        A_Command command = new CommandStructuralCommit();
        myParserHelper.getActionProcessor().schedule(command);
    }

    // COUPLE STOCK id1 AND id2
    public void ParseCoupleStock(String input) {
        String[] words = input.split(" ");

        A_Command command = new CommandStructuralCouple(words[2], words[4]);
        myParserHelper.getActionProcessor().schedule(command);
    }

    // UNCOUPLE STOCK id1 and id2
    public void ParseUncoupleStock(String input) {
        String[] words = input.split(" ");

        A_Command command = new CommandStructuralUncouple(words[2], words[4]);
        myParserHelper.getActionProcessor().schedule(command);
    }

    // LOCATE STOCK id1 ON TRACK id2 DISTANCE number FROM ( START | END )
    public void ParseLocateStock(String input) {
        String[] words = input.split(" ");

        String stockID = words[2];
        String trackID = words[5];
        double distance = Double.parseDouble(words[8]);
        boolean from = words[10].equals("START");

        TrackLocator locator = new TrackLocator(trackID, distance, from);

        A_Command command = new CommandStructuralLocate(stockID, locator);
        myParserHelper.getActionProcessor().schedule(command);
    }

    // OPEN VIEW id1 ORIGIN ( coordinates_world | ( '$' id2 ) ) WORLD WIDTH integer1
    // SCREEN WIDTH integer2 HEIGHT integer3
    public void ParseOpenView(String input) {
        String[] words = input.split(" ");

        String viewID = words[2];
        String coordsIn = words[4];
        int widthWorld = Integer.parseInt(words[7]);
        int widthScreen = Integer.parseInt(words[10]);
        int heightScreen = Integer.parseInt(words[12]);

        CoordinatesWorld origin;
        if (coordsIn.contains("$")) {
            origin = myParserHelper.getReference(coordsIn);
        } else {
            Longitude longitude;
            Latitude latitude;

            String[] coordsSplit = coordsIn.split("//");
            String[] latSplit = coordsSplit[0].split("/*|'|" + '"');
            String[] lonSplit = coordsSplit[1].split("/*|'|" + '"');

            latitude = new Latitude(Integer.parseInt(latSplit[0]), Integer.parseInt(latSplit[1]),
                    Integer.parseInt(latSplit[2]));
            longitude = new Longitude(Integer.parseInt(lonSplit[0]), Integer.parseInt(lonSplit[1]),
                    Integer.parseInt(lonSplit[2]));

            origin = new CoordinatesWorld(latitude, longitude);
        }

        CoordinatesScreen screen = new CoordinatesScreen(widthScreen, heightScreen);

        A_Command command = new CommandMetaViewGenerate(viewID, origin, widthWorld, screen);
        myParserHelper.getActionProcessor().schedule(command);
    }

    // USE id AS REFERENCE coordinates_world
    public void ParseUseIDAsReference(String input) {
        String[] words = input.split(" ");

        String id = words[1];
        String coordsIn = words[4];

        Longitude longitude;
        Latitude latitude;

        String[] coordsSplit = coordsIn.split("//");
        String[] latSplit = coordsSplit[0].split("/*|'|" + '"');
        String[] lonSplit = coordsSplit[1].split("/*|'|" + '"');

        latitude = new Latitude(Integer.parseInt(latSplit[0]), Integer.parseInt(latSplit[1]),
                Integer.parseInt(latSplit[2]));
        longitude = new Longitude(Integer.parseInt(lonSplit[0]), Integer.parseInt(lonSplit[1]),
                Integer.parseInt(lonSplit[2]));

        CoordinatesWorld coords = new CoordinatesWorld(latitude, longitude);

        myParserHelper.addReference(id, coords);
    }
}
