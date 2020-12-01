package cscd350f20project.controller.cli.parser;

import cs350f20project.controller.cli.parser.MyParserHelper;
// import cscd350f20project.ParserDo;
import cscd350f20project.ParserDo;
import cscd350f20project.ParserCreate.Power;

public class CommandParser {
    private MyParserHelper myParserHelper;
    private String command;

    public CommandParser(MyParserHelper myParserHelper, String command) {
        this.myParserHelper = myParserHelper;
        this.command = command;
    }

    public void parse() {
        if (this.command.contains(";")) {
            String[] str = this.command.split(";");
            for (String s : str) {
                parse(s, this.myParserHelper);
            }
        } else {
            parse(this.command, this.myParserHelper);
        }
    }

    private static void parse(String Command, MyParserHelper parserHelper) {
        if (Command.toLowerCase().startsWith("do")) {
            ParserDo parseDo = new ParserDo(parserHelper);
            parseDo.process(Command);
        }

        if (Command.toLowerCase().startsWith("create power")) {
            Power powerParse = new Power(parserHelper);
            powerParse.process(Command);
        }
    }
}
