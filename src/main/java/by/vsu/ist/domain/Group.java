package by.vsu.ist.domain;

import java.util.List;

public class Group extends Entity {
	private Account coach;
	private List<Account> participants;
	private int maxParticipants;
	private String date;
	private String place;


	@Override
	public String toString() {
		return "Account{" +
				"id=" + getId() +
				", name='" + coach + '\'' +
				", name='" + participants + '\'' +
				", name='" + maxParticipants + '\'' +
				", name='" + date + '\'' +
				", name='" + place + '\'' +
				'}';
	}

	public int getMaxParticipants() {
		return maxParticipants;
	}

	public void setMaxParticipants(int maxParticipants) {
		this.maxParticipants = maxParticipants;
	}

    public Account getCoach() {
        return coach;
    }

    public void setCoach(Account coach) {
        this.coach = coach;
    }

    public List<Account> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Account> participants) {
        this.participants = participants;
    }

	public String getDate() {
		return date;
	}

    public void setDate(String date) {
        this.date = date;
    }

    public void setPlace(String place) {
        this.place = place;
    }

	public String getPlace() {
		return place;
	}
}
