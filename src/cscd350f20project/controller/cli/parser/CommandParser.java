package cscd350f20project.controller.cli.parser;

import cs350f20project.controller.cli.parser.MyParserHelper;
// import cscd350f20project.ParserDo;
import cscd350f20project.ParserDo;
import cscd350f20project.ParserSysEnv;
import cscd350f20project.ParserCreate.Power;
import cscd350f20project.ParserCreate.Stock;
import cscd350f20project.ParserCreate.Track;

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
        } else if (Command.toLowerCase().startsWith("create power")) {
            Power powerParse = new Power(parserHelper);
            powerParse.process(Command);
        } else if (Command.toLowerCase().startsWith("create stock")) {
            Stock parseStock = new Stock(parserHelper);
            parseStock.process(Command);
        } else if (Command.toLowerCase().startsWith("create track")) {
            Track trackParse = new Track(parserHelper);
            trackParse.process(Command);
        } else {
            ParserSysEnv parseSysEnv = new ParserSysEnv(parserHelper);
            parseSysEnv.process(Command);
        }
    }
}
