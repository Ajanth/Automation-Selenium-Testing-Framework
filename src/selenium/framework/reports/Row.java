package selenium.framework.reports;

public class Row {
	String ManualStep;
	String Locator;
	String Value;
	String Action;
	String Comment;
	String RowPattern = "\n\n<tr>\n"+
                        "<th scope=\"row\">%s</th>\n"+
                        "<td>%s</td>\n"+
                        "<td>%s</td>\n"+
                        "<td>%s</td>\n"+
                        "<td><span class=\"check\" /></td>\n"+
                        "</tr>";
	String FailedRowPattern = "\n\n<tr>\n"+
    "<th scope=\"row\">%s</th>\n"+
    "<td>%s</td>\n"+
    "<td>%s</td>\n"+
    "<td>%s</td>\n"+
    "<td><span class=\"redmark\" /></td>\n"+
    "</tr>";
	String CommentPattern = "\n\n<tr class=\"comment\">\n"+
							"<th colspan=\"5\">%s</th>\n"+
							"</tr>";
	public Row(String ManualStep,String Locator,String Value,String Action){
		this.ManualStep = ManualStep;
		this.Locator = Locator;
		this.Value = Value;
		this.Action = Action;
	}
	public Row(String Comment){
		this.Comment = Comment;
	}
	public String generateRow(){
		String row = String.format(RowPattern, ManualStep, Locator, Value, Action);
		return row;
	}
	public String generateFailedRow(){
		String row = String.format(FailedRowPattern, ManualStep, Locator, Value, Action);
		return row;
	}
	public String generateCommentRow(){
		String row = String.format(CommentPattern, Comment);
		return row;
	}
}
