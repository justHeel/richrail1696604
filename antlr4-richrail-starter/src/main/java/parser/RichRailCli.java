package parser;

import com.richrail.services.TrainService;

public class RichRailCli extends RichRailBaseListener {
	@Override public void enterNewtraincommand(RichRailParser.NewtraincommandContext ctx) {
//		String trainName = ctx.getText();
//		String[] arrOfStr = trainName.split("newtrain",2);	
//		boolean canCreate = trainService.createTrain(arrOfStr[1]);	
	}
	
	@Override public void enterNewwagoncommand(RichRailParser.NewwagoncommandContext ctx) {
		System.out.println("new wagon command" + ctx.getText());
	}
	
	@Override public void enterAddcommand(RichRailParser.AddcommandContext ctx) { 
		System.out.println("add command" + ctx.getText());
	}
	
	@Override public void enterGetcommand(RichRailParser.GetcommandContext ctx) { 
		System.out.println("get command" + ctx.getText());
	}
	
	@Override public void enterRemcommand(RichRailParser.RemcommandContext ctx) {
		System.out.println("remove command" + ctx.getText());
	}
    // Override methods as desired...
}
