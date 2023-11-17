package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import model.MatchPL;
import model.Team;

public class DAOManagerJDBCImpl implements IDAOManager {
	
	Connection conn = null;
	
	public DAOManagerJDBCImpl() throws SQLException {
		String connectionUrl = "jdbc:mysql://localhost:3306/premier2223?serverTimezone=UTC";
		conn = DriverManager.getConnection(connectionUrl, "root", "");
	}

	public boolean AddTeam(Team oneTeam) {
		boolean added = true;
		CallableStatement cS = null;
		
		try {
			String storedProcedureCall = "{call AddTeamProcedure(?,?,?,?)}";
			
			cS = conn.prepareCall(storedProcedureCall);
			cS.setString(1, oneTeam.getClubName());
			cS.setString(2, oneTeam.getAbreviation());
			cS.setString(3, oneTeam.getHexCode());
			cS.setString(4, oneTeam.getLogoLink());
			
			cS.executeUpdate();
			
		} catch(Exception e) {
			System.out.println("Equip no afegit: " + e);
			added = false;
		} finally {
			if(cS != null) {
				try {
					cS.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return added;
	}

	public void ImportTeams(String fileTeams) {
		BufferedReader br = null;
		try {
			File file = new File("clubs.csv");
			br = new BufferedReader(new FileReader(file));
			
			br.readLine();
			String line;
				
			while((line = br.readLine()) != null) {
				String[] fields = line.split(",");
				String clubName =  fields[0];
				String abreviation = fields[1];
				String hexCode = fields[2];				
				String logoLink = fields[3];
					
				Team team = new Team(clubName, abreviation, hexCode, logoLink);
					
				AddTeam(team);
			}	
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			if(br != null)
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}	
		
	


	public Team GetTeam(String teamAbbreviation) {
		CallableStatement cS = null;
		Team team = null;
		try {
			String storedProcedureCall = "{call GetTeamByAbbreviation(?)}";
			
			cS = conn.prepareCall(storedProcedureCall);
			cS.setString(1, teamAbbreviation);
			
			boolean hasResults = cS.execute();
			if(hasResults) {
				ResultSet resultSet = cS.getResultSet();
				while(resultSet.next()) {
					team = new Team(
							resultSet.getString("club_name"),
							resultSet.getString("abbreviation"),
							resultSet.getString("hex_code"),
							resultSet.getString("logo_link")
						);
				}				
			}
		} catch(Exception ex) {
			System.out.println("Equip no seleccionat: " + ex);
		} finally {
			if(cS != null) {
				try {
					cS.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return team;
	}

	public String GetTeamAbbreviation(String teamName) {
		CallableStatement cS = null;
		String abbreviation = null;
		try {
			String storedProcedureCall = "{call GetAbbreviationByTeamName(?, ?)}";
			
			cS = conn.prepareCall(storedProcedureCall);
			cS.setString(1, teamName);
			
			cS.registerOutParameter(2, Types.VARCHAR);
			
			cS.execute();
			
			abbreviation = cS.getString(2);
		} catch(Exception ex) {
			System.out.println("Equip no seleccionat: " + ex);
		} finally {
			if(cS != null) {
				try {
					cS.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return abbreviation;
	}

	public boolean AddMatch(MatchPL oneMatch) {
		boolean added = true;
		CallableStatement cS = null;
		
		try {
			String storedProcedureCall = "{call AddMatchProcedure(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
			cS = conn.prepareCall(storedProcedureCall);
			
			java.sql.Date sqlDate = new java.sql.Date(oneMatch.getDateOfMatch().getTime());
			java.sql.Time sqlTime = new java.sql.Time(oneMatch.getTimeOfMatch().getTime());
			
			cS.setDate(1, sqlDate);
			cS.setTime(2, sqlTime);
			cS.setString(3, oneMatch.getHomeTeam());
			cS.setString(4, oneMatch.getAwayTeam());
			cS.setInt(5, oneMatch.getFTHG());
			cS.setInt(6, oneMatch.getFTAG());
			cS.setString(7, oneMatch.getFTR());
			cS.setInt(8, oneMatch.getHTHG());
			cS.setInt(9, oneMatch.getHTAG());
			cS.setString(10, oneMatch.getHTR());
			cS.setString(11, oneMatch.getReferee());
			cS.setInt(12, oneMatch.getHS());
			cS.setInt(13, oneMatch.getAS());
			cS.setInt(14, oneMatch.getHST());
			cS.setInt(15, oneMatch.getAST());
			cS.setInt(16, oneMatch.getHF());
			cS.setInt(17, oneMatch.getAF());
			cS.setInt(18, oneMatch.getHC());
			cS.setInt(19, oneMatch.getAC());
			cS.setInt(20, oneMatch.getHY());
			cS.setInt(21, oneMatch.getAY());
			cS.setInt(22, oneMatch.getHR());
			cS.setInt(23, oneMatch.getAR());
			
			
			cS.executeUpdate();
			
		} catch(Exception e) {
			System.out.println("Equip no afegit: " + e);
			added = false;
		} finally {
			if(cS != null) {
				try {
					cS.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return added;
	}

	public void ImportMatches(String fileMatches){
		BufferedReader br = null;
		try {
			File file = new File("results.csv");
			br = new BufferedReader(new FileReader(file));
			
			br.readLine();
			String line;
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
			
			
				
			while((line = br.readLine()) != null) {
				String[] fields = line.split(",");
				
				MatchPL match = new MatchPL(
						dateFormat.parse(fields[1]),
						timeFormat.parse(fields[2]),
						fields[3],
						fields[4],
						Integer.parseInt(fields[5]),
						Integer.parseInt(fields[6]),
						fields[7],
						Integer.parseInt(fields[8]),
						Integer.parseInt(fields[9]),
						fields[10],
						fields[11],
						Integer.parseInt(fields[12]),
						Integer.parseInt(fields[13]),
						Integer.parseInt(fields[14]),
						Integer.parseInt(fields[15]),
						Integer.parseInt(fields[16]),
						Integer.parseInt(fields[17]),
						Integer.parseInt(fields[18]),
						Integer.parseInt(fields[19]),
						Integer.parseInt(fields[20]),
						Integer.parseInt(fields[21]),
						Integer.parseInt(fields[22]),
						Integer.parseInt(fields[23])
				);
				
				
				AddMatch(match);
			}	
		} catch(IOException | ParseException e) {
			e.printStackTrace();
		} finally {
			if(br != null)
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
		
	}

	public MatchPL GetMatch(Date matchDay, Team home, Team away) {
		MatchPL selectedMatch = null;
		PreparedStatement pS = null;
		try {
			pS = conn.prepareStatement("SELECT * FROM match_pl "
					+ "WHERE date_of_match = ? AND home_team = ? AND away_team = ?");
			
			java.sql.Date sqlDate = new java.sql.Date(matchDay.getTime());
			pS.setDate(1, sqlDate);
			pS.setString(2, home.getClubName());
			pS.setString(3, away.getClubName());
			
			ResultSet resultSet = pS.executeQuery();
			while(resultSet.next()) {
				java.util.Date date = new java.util.Date(resultSet.getDate("date_of_match").getTime());
				java.util.Date time = new java.sql.Date(resultSet.getTime("time_of_match").getTime());
				
				return new MatchPL(
                        date,
                        time,
                        resultSet.getString("home_team"),
                        resultSet.getString("away_team"),
                        resultSet.getInt("full_time_home_goals"),
                        resultSet.getInt("full_time_away_goals"),
                        resultSet.getString("full_time_result"),
                        resultSet.getInt("halftime_home_goals"),
                        resultSet.getInt("halftime_away_goals"),
                        resultSet.getString("halftime_result"),
                        resultSet.getString("referee"),
                        resultSet.getInt("home_team_shots"),
                        resultSet.getInt("away_team_shots"),
                        resultSet.getInt("home_team_shots_on_goal"),
                        resultSet.getInt("away_team_shots_on_goal"),
                        resultSet.getInt("home_team_fouls"),
                        resultSet.getInt("away_team_fouls"),
                        resultSet.getInt("home_team_corners"),
                        resultSet.getInt("away_team_corners"),
                        resultSet.getInt("home_team_yellow_cards"),
                        resultSet.getInt("away_team_yellow_cards"),
                        resultSet.getInt("home_team_red_cards"),
                        resultSet.getInt("away_team_red_cards")
                );
			}
			
		} catch (SQLException e) {
			System.out.println("Partit no trobat: " + e);
		} finally {
			if(pS != null)
				try {
					pS.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
		return selectedMatch;
	}

	public int HomeGoals() {
		int totalHomeGoals = 0;
		CallableStatement cS = null;
		
		try {
			String storedProcedureCall = "{call SumAllHomeGoals(?)}";
			cS = conn.prepareCall(storedProcedureCall);
			cS.registerOutParameter(1, Types.INTEGER);
			
			cS.execute();
			
			totalHomeGoals = cS.getInt(1);
		} catch(Exception e) {
			System.out.println("HomeGoals() -> Operació no completada: " + e);
		} finally {
			if(cS != null) {
				try {
					cS.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return totalHomeGoals;
	}

	
	public int AwayGoals() {
		int awayHomeGoals = 0;
		CallableStatement cS = null;
		
		try {
			String storedProcedureCall = "{call SumAllAwayGoals(?)}";
			cS = conn.prepareCall(storedProcedureCall);
			cS.registerOutParameter(1, Types.INTEGER);
			
			cS.execute();
			
			awayHomeGoals = cS.getInt(1);
		} catch(Exception e) {
			System.out.println("HomeGoals() -> Operació no completada: " + e);
		} finally {
			if(cS != null) {
				try {
					cS.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return awayHomeGoals;
	}

	public ArrayList<MatchPL> MatchesOfTeam(Team oneTeam) {
		ArrayList<MatchPL> matches = null;
		CallableStatement cS = null;
		
		try {
			String teamName = oneTeam.getClubName();
			String storedProcedureCall = "{call GetMatchesOfTeam(?)}";
			cS = conn.prepareCall(storedProcedureCall);
			cS.setString(1,teamName);
			
			boolean hasResults = cS.execute();
			if(hasResults) {
				matches = new ArrayList<MatchPL>();
				ResultSet resultSet = cS.getResultSet();
				while(resultSet.next()) {
					java.util.Date date = new java.util.Date(resultSet.getDate("date_of_match").getTime());
					java.util.Date time = new java.sql.Date(resultSet.getTime("time_of_match").getTime());
					
					matches.add (new MatchPL(
	                        date,
	                        time,
	                        resultSet.getString("home_team"),
	                        resultSet.getString("away_team"),
	                        resultSet.getInt("full_time_home_goals"),
	                        resultSet.getInt("full_time_away_goals"),
	                        resultSet.getString("full_time_result"),
	                        resultSet.getInt("halftime_home_goals"),
	                        resultSet.getInt("halftime_away_goals"),
	                        resultSet.getString("halftime_result"),
	                        resultSet.getString("referee"),
	                        resultSet.getInt("home_team_shots"),
	                        resultSet.getInt("away_team_shots"),
	                        resultSet.getInt("home_team_shots_on_goal"),
	                        resultSet.getInt("away_team_shots_on_goal"),
	                        resultSet.getInt("home_team_fouls"),
	                        resultSet.getInt("away_team_fouls"),
	                        resultSet.getInt("home_team_corners"),
	                        resultSet.getInt("away_team_corners"),
	                        resultSet.getInt("home_team_yellow_cards"),
	                        resultSet.getInt("away_team_yellow_cards"),
	                        resultSet.getInt("home_team_red_cards"),
	                        resultSet.getInt("away_team_red_cards")
	                ));
				}
			}
		} catch(Exception e) {
			System.out.println("MatchesOfTeam -> Operació no completada: " + e);
		} finally {
			if(cS != null) {
				try {
					cS.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return matches;
	}

	public int RedCards(Team oneTeam) {
		int redCards = 0;
		CallableStatement cS = null;
		
		try {
			String storedProcedureCall = "{call GetTeamRedCards(?, ?)}";
			cS = conn.prepareCall(storedProcedureCall);
			
			cS.setString(1, oneTeam.getClubName());
			cS.registerOutParameter(2, Types.INTEGER);
			
			cS.execute();
			
			redCards = cS.getInt(2);
		} catch(Exception e) {
			System.out.println("RedCards() -> Operació no completada: " + e);
		} finally {
			if(cS != null) {
				try {
					cS.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return redCards;
	}

	public ArrayList<Team> TopRedCards() {
		ArrayList<Team> topRedCardsTeams = null;
		PreparedStatement pS = null;
		try {
			pS = conn.prepareStatement("SELECT * FROM team");
			
			ResultSet resultSet = pS.executeQuery();
			topRedCardsTeams = new ArrayList<Team>();
			while(resultSet.next()) {
				Team team = new Team(
						resultSet.getString("club_name"),
						resultSet.getString("abbreviation"),
						resultSet.getString("hex_code"),
						resultSet.getString("logo_link")
				);
				if(topRedCardsTeams.size() == 0) topRedCardsTeams.add(team);
				else {
					if(RedCards(team) > RedCards(topRedCardsTeams.get(0))) {
						topRedCardsTeams.clear();
						topRedCardsTeams.add(team);
					}
					else if(RedCards(team) > RedCards(topRedCardsTeams.get(0))) {
						topRedCardsTeams.add(team);
					}
				}
			}
			
		} catch (SQLException e) {
			System.out.println("Partit no trobat: " + e);
		} finally {
			if(pS != null)
				try {
					pS.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
		return topRedCardsTeams;
	}

	public void close() throws Exception {
		try {
			if(conn != null) conn.close();
		} catch (SQLException e) {
			System.out.println("Exception closing Connection: " + e);
		}
	}



}
