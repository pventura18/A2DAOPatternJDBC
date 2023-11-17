package test;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

import dao.DAOFactory;
import dao.IDAOManager;
import model.MatchPL;
import model.Team;

public class PremierLeagueTestInteractive {

	public static void main(String[] args) throws SQLException, IOException, ParseException {
		DAOFactory factory = new DAOFactory();
		IDAOManager dao = factory.createDAOManager();
		
		//I
		Team teamToAdd = new Team("Girona FC", "GIR", "#kkeoi32", "linkLogo");
		if(dao.AddTeam(teamToAdd)) System.out.println("I -> Team added!");
		
		//II
		dao.ImportTeams("clubs.csv");
		
		//III
		Team team = dao.GetTeam("BOU");
		System.out.println("III -> Selected team: " + team.getClubName() + " " + team.getAbreviation() + " " + team.getHexCode() + " " + team.getLogoLink());
		
		//IV
		String teamAbbreviation = dao.GetTeamAbbreviation("Nottingham");
		System.out.println("IV -> Team Abbreviation for Nottingham: " + teamAbbreviation);
		
		//V
		MatchPL matchToAdd = new MatchPL(new Date(), new Date(),
                "Arsenal", "Leicester", 2, 1, "W", 1, 0, "H", "RefName", 10, 8, 5, 3, 12, 8, 3, 4, 2, 3, 0, 1);
		if(dao.AddMatch(matchToAdd)) System.out.println("V -> Match added!");
		
		//VI
		dao.ImportMatches("results.csv");
		
		//VII
		String dateString = "2022-08-05";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = sdf.parse(dateString);
		
		MatchPL matchFound = dao.GetMatch(date, dao.GetTeam("CRY"), dao.GetTeam("ARS"));
		if(matchFound != null)System.out.println("VII -> Match found! -> " + matchFound.getHomeTeam() + " vs " + matchFound.getAwayTeam() + " | Result: " + matchFound.getFTHG() + ":" + matchFound.getFTAG());
		
		MatchPL matchNotFound = dao.GetMatch(new Date(), dao.GetTeam("ARS"), dao.GetTeam("GIR"));
		if(matchNotFound == null) System.out.println("VII -> Match not found!");
		
		//VIII
		int homeGoals = dao.HomeGoals();
		System.out.println("VIII -> Home goals -> " + homeGoals);
		
		//IX
		int awayGoals = dao.AwayGoals();
		System.out.println("IX -> Away goals -> " + awayGoals);
		
		//X
		ArrayList<MatchPL> matchesOfArsenal = dao.MatchesOfTeam(dao.GetTeam("ARS"));
		System.out.println("X -> Arsenal Matches: " + matchesOfArsenal.size());
		
		//XI
		Team crystalPalace = dao.GetTeam("CRY");
		System.out.println("XI -> " + crystalPalace.getClubName() + " has: " + dao.RedCards(crystalPalace));
		
		//XII
		ArrayList<Team> teamsRedCards = dao.TopRedCards();
		System.out.print("XII -> Top teams with red cards: ");
		for(Team teamFromList : teamsRedCards){
			System.out.print(teamFromList.getClubName() + " ");
		}
	}

}
