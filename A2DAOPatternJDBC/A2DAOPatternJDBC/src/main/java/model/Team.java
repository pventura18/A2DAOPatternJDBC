package model;

public class Team {
	private String clubName;
	private String abreviation;
	private String hexCode;
	private String logoLink;
	
	public Team(String clubName, String abreviation, String hexCode, String logoLink) {
		this.clubName = clubName;
		this.abreviation = abreviation;
		this.hexCode = hexCode;
		this.logoLink = logoLink;
	}

	public String getClubName() {
		return clubName;
	}

	public String getAbreviation() {
		return abreviation;
	}

	public String getHexCode() {
		return hexCode;
	}

	public String getLogoLink() {
		return logoLink;
	}
}
