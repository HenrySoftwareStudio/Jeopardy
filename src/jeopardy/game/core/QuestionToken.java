package jeopardy.game.core;

public class QuestionToken
{
	private String Question;
	private String Answer;
	private int Value;
	private String Category;
	
	public QuestionToken(final String Question, final String Answer, final int Value, final String Category)
	{
		this.Question=Question;
		this.Answer=Answer;
		this.Value=Value;
		this.Category=Category;
	}
	
	public QuestionToken(final String Question, final String Answer, final String Category)
	{
		this.Question=Question;
		this.Answer=Answer;
		this.Value=-1;
		this.Category=Category;
	}

	public String getAnswer()
	{
		return Answer;
	}
	
	public String getCategory()
	{
		return Category;
	}
	
	public String getQuestion()
	{
		return Question;
	}
	
	public int getValue()
	{
		return Value;
	}
	
	@Override
	public String toString()
	{
		return String.format("Category: %s, Question: %s, Answer: %s, Value: %d", Category, Question, Answer, Value);
	}
}
