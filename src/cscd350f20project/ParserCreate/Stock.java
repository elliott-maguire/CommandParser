package cscd350f20project.ParserCreate;

import cs350f20project.controller.cli.parser.MyParserHelper;
import cs350f20project.controller.command.A_Command;
import cs350f20project.controller.command.creational.CommandCreateStockCarBox;
import cs350f20project.controller.command.creational.CommandCreateStockCarCaboose;

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
        }
    }
}
