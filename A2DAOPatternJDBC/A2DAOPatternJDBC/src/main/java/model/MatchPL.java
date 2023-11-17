package model;

import java.sql.Time;
import java.util.Date;

public class MatchPL {
    private Date date;
    private Date time;
    private String homeTeam;
    private String awayTeam;
    private int FTHG;
    private int FTAG;
    private String FTR;
    private int HTHG;
    private int HTAG;
    private String HTR;
    private String Referee;
    private int HS;
    private int AS;
    private int HST;
    private int AST;
    private int HF;
	private int AF;
    private int HC;
    private int AC;
    private int HY;
    private int AY;
    private int HR;
    private int AR;
    
    

    public MatchPL(Date _date, Date _time, String homeTeam, String awayTeam, int fTHG, int fTAG, String fTR, int hTHG,
			int hTAG, String hTR, String referee, int hS, int aS, int hST, int aST, int hF, int aF, int hC, int aC,
			int hY, int aY, int hR, int aR) {
		super();
		date = _date;
		time = _time;
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
		FTHG = fTHG;
		FTAG = fTAG;
		FTR = fTR;
		HTHG = hTHG;
		HTAG = hTAG;
		HTR = hTR;
		Referee = referee;
		HS = hS;
		AS = aS;
		HST = hST;
		AST = aST;
		HF = hF;
		AF = aF;
		HC = hC;
		AC = aC;
		HY = hY;
		AY = aY;
		HR = hR;
		AR = aR;
	}
	public Date getDateOfMatch() {
		return date;
	}
	public Date getTimeOfMatch() {
		return time;
	}
	public String getHomeTeam() {
		return homeTeam;
	}
	public String getAwayTeam() {
		return awayTeam;
	}
	public int getFTHG() {
		return FTHG;
	}
	public int getFTAG() {
		return FTAG;
	}
	public String getFTR() {
		return FTR;
	}
	public int getHTHG() {
		return HTHG;
	}
	public int getHTAG() {
		return HTAG;
	}
	public String getHTR() {
		return HTR;
	}
	public String getReferee() {
		return Referee;
	}
	public int getHS() {
		return HS;
	}
	public int getAS() {
		return AS;
	}
	public int getHST() {
		return HST;
	}
	public int getAST() {
		return AST;
	}
	public int getHF() {
		return HF;
	}
	public int getAF() {
		return AF;
	}
	public int getHC() {
		return HC;
	}
	public int getAC() {
		return AC;
	}
	public int getHY() {
		return HY;
	}
	public int getAY() {
		return AY;
	}
	public int getHR() {
		return HR;
	}
	public int getAR() {
		return AR;
	}
}

