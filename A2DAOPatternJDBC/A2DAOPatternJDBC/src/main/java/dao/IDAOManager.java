package dao;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.ArrayList;

import model.MatchPL;
import model.Team;

public interface IDAOManager extends AutoCloseable{
	
	public boolean AddTeam(Team oneTeam);
	
	public void ImportTeams(String fileTeams);
	
	public Team GetTeam(String teamAbbreviation);
	
	public String GetTeamAbbreviation(String teamName);
	
	public boolean AddMatch(MatchPL oneMatch);
	
	public void ImportMatches(String fileMatches);
	
	public MatchPL GetMatch(Date matchDay, Team home, Team away);
	
	public int HomeGoals();
	
	public int AwayGoals();
	
	public ArrayList<MatchPL> MatchesOfTeam(Team oneTeam);
	
	public int RedCards(Team oneTeam);
	
	public ArrayList<Team> TopRedCards();
}
