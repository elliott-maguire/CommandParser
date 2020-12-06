package cscd350f20project.ParserCreate;

import cs350f20project.controller.cli.TrackLocator;
import cs350f20project.controller.cli.parser.MyParserHelper;
import cs350f20project.controller.command.A_Command;
import cs350f20project.controller.command.creational.*;

public class Stock {
    private MyParserHelper myParserHelper;

    public Stock(MyParserHelper myParserHelper) {
        this.myParserHelper = myParserHelper;
    }

    public void process(String command) {
        if (command.startsWith("create stock")) {
            ParseCreate(command);
        } else {
            createDieselEngine(command);
        }
    }

    public void ParseCreate(String command) {
        String[] words = command.split(" ");
        String id = words[3];
        String type = words[5];

        A_Command c;
        switch (type.toLowerCase()) {
            case "box" -> {
                c = new CommandCreateStockCarBox(id);
                myParserHelper.getActionProcessor().schedule(c);
            }

            case "caboose" -> {
                c = new CommandCreateStockCarCaboose(id);
                myParserHelper.getActionProcessor().schedule(c);
            }

            case "flatbed" -> {
                c = new CommandCreateStockCarFlatbed(id);
                myParserHelper.getActionProcessor().schedule(c);
            }

            case "passenger" -> {
                c = new CommandCreateStockCarPassenger(id);
                myParserHelper.getActionProcessor().schedule(c);
            }

            case "tank" -> {
                c = new CommandCreateStockCarTank(id);
                myParserHelper.getActionProcessor().schedule(c);
            }

            case "tender" -> {
                c = new CommandCreateStockCarTender(id);
                myParserHelper.getActionProcessor().schedule(c);
            }

            case "diesel" -> this.createDieselEngine(command);

        }

    }

    private void createDieselEngine(String command) {
        String[] words = command.split(" ");
        String id1 = words[3];
        String id2 = words[8];
        double distance = Double.parseDouble(words[10]);
        boolean fromStartOrEnd;
        boolean facingStartOrEnd;

        A_Command c;

        fromStartOrEnd = ("start" == words[12].toLowerCase());

        facingStartOrEnd = ("start" == words[14].toLowerCase());

        c = new CommandCreateStockEngineDiesel(id1, new TrackLocator(id2, distance, fromStartOrEnd), facingStartOrEnd);

        myParserHelper.getActionProcessor().schedule(c);
    }
}
