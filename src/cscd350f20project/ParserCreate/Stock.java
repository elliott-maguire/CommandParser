package cscd350f20project.ParserCreate;

import cs350f20project.controller.cli.parser.MyParserHelper;
import cs350f20project.controller.command.A_Command;
import cs350f20project.controller.command.creational.CommandCreateStockCarBox;
import cs350f20project.controller.command.creational.CommandCreateStockCarCaboose;
import cs350f20project.controller.command.creational.CommandCreateStockCarFlatbed;
import cs350f20project.controller.command.creational.CommandCreateStockCarPassenger;
import cs350f20project.controller.command.creational.CommandCreateStockCarTank;
import cs350f20project.controller.command.creational.CommandCreateStockCarTender;

import java.lang.reflect.Array;

public class Stock {
    private MyParserHelper myParserHelper;

    public Stock(MyParserHelper myParserHelper) {
        this.myParserHelper = myParserHelper;
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
            
        }
    }
}
