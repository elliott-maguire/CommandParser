package cscd350f20project;

import cs350f20project.controller.cli.parser.MyParserHelper;
import cs350f20project.controller.command.A_Command;
import cs350f20project.controller.command.meta.CommandMetaDoExit;
import cs350f20project.controller.command.meta.CommandMetaDoRun;
import cs350f20project.controller.command.meta.CommandMetaViewDestroy;
import cs350f20project.controller.command.structural.CommandStructuralCommit;
import cs350f20project.controller.command.structural.CommandStructuralCouple;
import cs350f20project.controller.command.structural.CommandStructuralUncouple;

import javax.swing.text.html.parser.Parser;

public class ParserSysEnv {
    private MyParserHelper myParserHelper;

    public ParserSysEnv(MyParserHelper myParserHelper) {
        this.myParserHelper = myParserHelper;
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


}
