package cscd350f20project;

import cs350f20project.controller.cli.parser.MyParserHelper;
import cs350f20project.controller.command.A_Command;
import cs350f20project.controller.command.behavioral.*;
import cs350f20project.datatype.Angle;

public class ParserDo {
	private MyParserHelper myHelper;

	public ParserDo(MyParserHelper myHelper) {
		this.myHelper = myHelper;
	}

	public void process(String CMD) {
		String[] Command = CMD.split(" ");

		if (Command[1].equalsIgnoreCase("select")) {
			if (Command[2].equalsIgnoreCase("drawbridge")) {
				boolean isUp = Command[5].equals("true");
				A_Command c = new CommandBehavioralSelectBridge(Command[3], isUp);
				this.myHelper.getActionProcessor().schedule(c);
			} // end the check for drawbridge

			else if (Command[2].equalsIgnoreCase("roundhouse")) {
				Angle angle = new Angle(Double.parseDouble(Command[5]));
				boolean rotation = Command[6].equals("true");
				A_Command c = new CommandBehavioralSelectRoundhouse(Command[3], angle, rotation);
				this.myHelper.getActionProcessor().schedule(c);
			} // end check for round house

			else if (Command[2].equalsIgnoreCase("switch")) {
				boolean path = Command[5].equals("true");
				A_Command c = new CommandBehavioralSelectSwitch(Command[3], path);
				this.myHelper.getActionProcessor().schedule(c);
			} // end switch check
		} // end check for select

		else if (Command[1].equalsIgnoreCase("set")) {
			/*
			 * The following commands check for set at the index of [1] if it matches set,
			 * then it will check among the relevant commands and call the proper
			 * constructor
			 */

			if (Command[4].equalsIgnoreCase("direction")) {
				boolean direction = Command[5].equals("true");
				A_Command c = new CommandBehavioralSetDirection(Command[3], direction);
				this.myHelper.getActionProcessor().schedule(c);
			} // end direction check

			else if (Command[4].equalsIgnoreCase("speed")) {
				double speed = Double.parseDouble(Command[5]);
				A_Command c = new CommandBehavioralSetSpeed(Command[3], speed);
				this.myHelper.getActionProcessor().schedule(c);
			} // end check for speed

			else if (Command[3].equalsIgnoreCase("reference")) {
				A_Command c = new CommandBehavioralSetReference(Command[4]);
				this.myHelper.getActionProcessor().schedule(c);
			} // end check for reference
		} // end the checks for set
		else if (Command[1].equalsIgnoreCase("Brake")) {
			A_Command c = new CommandBehavioralBrake(Command[2]);
			this.myHelper.getActionProcessor().schedule(c);

		}
	}// end process method
}// end of the class
