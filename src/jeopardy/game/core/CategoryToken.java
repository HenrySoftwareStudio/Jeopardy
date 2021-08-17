package jeopardy.game.core;

public class CategoryToken
{
	private String Name;
	public CategoryToken(final String Name)
	{
		this.Name=Name;
	}
	
	public String getName()
	{
		return Name;
	}
	
	@Override
	public String toString()
	{
		return "Name: " + Name + super.toString();
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(obj instanceof CategoryToken)
		{
			CategoryToken token=(CategoryToken) obj;
			if(token.getName().equals(this.Name))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}
}
