import java.util.ArrayList;
import java.util.List;

// exception for rule and input checks
class LeagueRuleException extends Exception {
    public LeagueRuleException(String msg) {
        super(msg);
    }
}

abstract class Person {
    private String id;
    private String name;
    private int age;
    private String gender;

    public Person(String id, String name, int age, String gender) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public abstract void showInfo();
}

class Player extends Person {
    private String position;
    private int jerseyNumber;
    private int goals;
    private boolean active;

    public Player(String id, String name, int age, String gender, String position, int jerseyNumber)
            throws LeagueRuleException {
        super(id, name, age, gender);
        if (age < 16) {
            throw new LeagueRuleException("Player must be at least 16 years old.");
        }
        this.position = position;
        this.jerseyNumber = jerseyNumber;
        this.goals = 0;
        this.active = true;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getJerseyNumber() {
        return jerseyNumber;
    }

    public void setJerseyNumber(int jerseyNumber) {
        this.jerseyNumber = jerseyNumber;
    }

    public int getGoals() {
        return goals;
    }

    public void addGoals(int g) {
        if (g > 0) {
            this.goals = this.goals + g;
        }
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void showInfo() {
        System.out.println("Player: " + getName() + " (#" + jerseyNumber + ") - " + position);
    }
}

class Coach extends Person {
    private String qualification;
    private String role;

    public Coach(String id, String name, int age, String gender, String qualification, String role) {
        super(id, name, age, gender);
        this.qualification = qualification;
        this.role = role;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void showInfo() {
        System.out.println("Coach: " + getName() + " - " + role + " (" + qualification + ")");
    }
}

class LeagueCommissioner extends Person {
    public LeagueCommissioner(String id, String name, int age, String gender) {
        super(id, name, age, gender);
    }

    public void approveTeam(FootballTeam team) throws LeagueRuleException {
        if ("SUSPENDED".equalsIgnoreCase(team.getStatus())) {
            throw new LeagueRuleException("Suspended team cannot be approved directly.");
        }
        team.setStatus("ACTIVE");
    }

    public void showInfo() {
        System.out.println("Commissioner: " + getName() + " [" + getId() + "]");
    }
}

class FootballTeam {
    private String regNumber;
    private String name;
    private String parish;
    private String status;
    private int points;
    private List<Player> players;
    private List<Coach> coaches;

    public FootballTeam(String regNumber, String name, String parish) {
        this.regNumber = regNumber;
        this.name = name;
        this.parish = parish;
        this.status = "PENDING";
        this.points = 0;
        this.players = new ArrayList<>();
        this.coaches = new ArrayList<>();
    }

    public String getRegNumber() {
        return regNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParish() {
        return parish;
    }

    public void setParish(String parish) {
        this.parish = parish;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPoints() {
        return points;
    }

    public void addPoints(int pts) {
        this.points = this.points + pts;
    }

    public int getSquadSize() {
        return players.size() + coaches.size();
    }

    public List<Player> getPlayers() {
        return players;
    }

    public List<Coach> getCoaches() {
        return coaches;
    }

    // 2. add, update, find, and remove players and coaches
    public void addPlayer(Player p) {
        players.add(p);
    }

    public Player findPlayerById(String playerId) {
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getId().equalsIgnoreCase(playerId)) {
                return players.get(i);
            }
        }
        return null;
    }

    public void updatePlayer(String playerId, String newPos, int newJersey) {
        Player p = findPlayerById(playerId);
        if (p != null) {
            p.setPosition(newPos);
            p.setJerseyNumber(newJersey);
        }
    }

    public void removePlayer(String playerId) {
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getId().equalsIgnoreCase(playerId)) {
                players.remove(i);
                return;
            }
        }
    }

    public void addCoach(Coach c) {
        coaches.add(c);
    }

    public Coach findCoachById(String coachId) {
        for (int i = 0; i < coaches.size(); i++) {
            if (coaches.get(i).getId().equalsIgnoreCase(coachId)) {
                return coaches.get(i);
            }
        }
        return null;
    }

    public void updateCoach(String coachId, String newRole, String newQual) {
        Coach c = findCoachById(coachId);
        if (c != null) {
            c.setRole(newRole);
            c.setQualification(newQual);
        }
    }

    public void removeCoach(String coachId) {
        for (int i = 0; i < coaches.size(); i++) {
            if (coaches.get(i).getId().equalsIgnoreCase(coachId)) {
                coaches.remove(i);
                return;
            }
        }
    }

    public void displayTeam() {
        System.out.println("Team: " + name + " [" + regNumber + "] | Parish: " + parish +
                " | Status: " + status + " | Squad: " + getSquadSize() + " | Pts: " + points);
    }
}

class Stadium {
    private String stadiumId;
    private String name;
    private String location;
    private int capacity;
    private String fieldCondition;
    private double ticketPrice;
    private String currentFixtureTeams;

    public Stadium(String stadiumId, String name, String location, int capacity, String fieldCondition, double ticketPrice)
            throws LeagueRuleException {
        if (capacity <= 0 || ticketPrice < 0) {
            throw new LeagueRuleException("Invalid stadium capacity or ticket price.");
        }
        this.stadiumId = stadiumId;
        this.name = name;
        this.location = location;
        this.capacity = capacity;
        this.fieldCondition = fieldCondition;
        this.ticketPrice = ticketPrice;
        this.currentFixtureTeams = "None";
    }

    public String getStadiumId() {
        return stadiumId;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getFieldCondition() {
        return fieldCondition;
    }

    public void setFieldCondition(String fieldCondition) {
        this.fieldCondition = fieldCondition;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(double ticketPrice) throws LeagueRuleException {
        if (ticketPrice < 0) {
            throw new LeagueRuleException("Ticket price cannot be negative.");
        }
        this.ticketPrice = ticketPrice;
    }

    public String getCurrentFixtureTeams() {
        return currentFixtureTeams;
    }

    public void setCurrentFixtureTeams(String currentFixtureTeams) {
        this.currentFixtureTeams = currentFixtureTeams;
    }

    public void displayStadium() {
        System.out.println("Stadium: " + name + " [" + stadiumId + "] | Location: " + location +
                " | Cap: " + capacity + " | Pitch: " + fieldCondition + " | Ticket: " + ticketPrice);
    }
}

class Fixture {
    private String fixtureId;
    private FootballTeam homeTeam;
    private FootballTeam awayTeam;
    private Stadium stadium;
    private String date;
    private String time;
    private String status;

    public Fixture(String fixtureId, FootballTeam homeTeam, FootballTeam awayTeam, Stadium stadium, String date, String time) {
        this.fixtureId = fixtureId;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.stadium = stadium;
        this.date = date;
        this.time = time;
        this.status = "SCHEDULED";
    }

    public String getFixtureId() {
        return fixtureId;
    }

    public FootballTeam getHomeTeam() {
        return homeTeam;
    }

    public FootballTeam getAwayTeam() {
        return awayTeam;
    }

    public Stadium getStadium() {
        return stadium;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void displayFixture() {
        System.out.println("Fixture [" + fixtureId + "] " + homeTeam.getName() + " vs " + awayTeam.getName() +
                " at " + stadium.getName() + " on " + date + " at " + time + " (" + status + ")");
    }
}

class SetupManager {
    private List<FootballTeam> teams;
    private List<Stadium> stadiums;
    private List<Fixture> fixtures;

    public SetupManager() {
        this.teams = new ArrayList<>();
        this.stadiums = new ArrayList<>();
        this.fixtures = new ArrayList<>();
    }

    public List<FootballTeam> getTeams() {
        return teams;
    }

    public List<Stadium> getStadiums() {
        return stadiums;
    }

    public List<Fixture> getFixtures() {
        return fixtures;
    }

    // 1. register, update, search, and display football teams
    public void registerTeam(FootballTeam team) {
        teams.add(team);
    }

    public void updateTeam(String regNumber, String newName, String newParish, String newStatus) {
        FootballTeam t = findTeamByReg(regNumber);
        if (t != null) {
            t.setName(newName);
            t.setParish(newParish);
            t.setStatus(newStatus);
        }
    }

    public FootballTeam findTeamByReg(String regNumber) {
        for (int i = 0; i < teams.size(); i++) {
            if (teams.get(i).getRegNumber().equalsIgnoreCase(regNumber)) {
                return teams.get(i);
            }
        }
        return null;
    }

    public void displayAllTeams() {
        System.out.println("--- Teams ---");
        for (int i = 0; i < teams.size(); i++) {
            teams.get(i).displayTeam();
        }
    }

    // 3. register and find stadiums
    public void registerStadium(Stadium stadium) {
        stadiums.add(stadium);
    }

    public Stadium findStadiumById(String stadiumId) {
        for (int i = 0; i < stadiums.size(); i++) {
            if (stadiums.get(i).getStadiumId().equalsIgnoreCase(stadiumId)) {
                return stadiums.get(i);
            }
        }
        return null;
    }

    public void displayAllStadiums() {
        System.out.println("--- Stadiums ---");
        for (int i = 0; i < stadiums.size(); i++) {
            stadiums.get(i).displayStadium();
        }
    }

    // 4. create and find fixtures
    public Fixture createFixture(String fixtureId, FootballTeam home, FootballTeam away,
                                 Stadium stadium, String date, String time) throws LeagueRuleException {
        if (home.getRegNumber().equalsIgnoreCase(away.getRegNumber())) {
            throw new LeagueRuleException("A team cannot play against itself.");
        }
        checkTeamForFixture(home, date, time);
        checkTeamForFixture(away, date, time);

        Fixture f = new Fixture(fixtureId, home, away, stadium, date, time);
        fixtures.add(f);
        stadium.setCurrentFixtureTeams(home.getName() + " vs " + away.getName());
        return f;
    }

    public Fixture findFixtureById(String fixtureId) {
        for (int i = 0; i < fixtures.size(); i++) {
            if (fixtures.get(i).getFixtureId().equalsIgnoreCase(fixtureId)) {
                return fixtures.get(i);
            }
        }
        return null;
    }

    private void checkTeamForFixture(FootballTeam team, String date, String time) throws LeagueRuleException {
        if (!"ACTIVE".equalsIgnoreCase(team.getStatus())) {
            throw new LeagueRuleException("Team " + team.getName() + " is not active.");
        }
        // check if team already has a fixture at same date and time
        for (int i = 0; i < fixtures.size(); i++) {
            Fixture f = fixtures.get(i);
            if (f.getDate().equals(date) && f.getTime().equals(time)) {
                boolean sameTeam = f.getHomeTeam().getRegNumber().equalsIgnoreCase(team.getRegNumber())
                        || f.getAwayTeam().getRegNumber().equalsIgnoreCase(team.getRegNumber());
                if (sameTeam) {
                    throw new LeagueRuleException("Team " + team.getName() + " has a fixture conflict.");
                }
            }
        }
    }

    public void displayFixtures() {
        System.out.println("--- Fixtures ---");
        for (int i = 0; i < fixtures.size(); i++) {
            fixtures.get(i).displayFixture();
        }
    }
}

public class Main {
    public static void main(String[] args) {
        SetupManager manager = new SetupManager();
        LeagueCommissioner comm = new LeagueCommissioner("C01", "Mr. Brown", 48, "Male");

        try {
            FootballTeam t1 = new FootballTeam("T01", "Harbour View", "Kingston");
            FootballTeam t2 = new FootballTeam("T02", "Waterhouse", "Kingston");
            manager.registerTeam(t1);
            manager.registerTeam(t2);

            comm.approveTeam(t1);
            comm.approveTeam(t2);

            Player p1 = new Player("P01", "Andre Reid", 21, "Male", "Forward", 9);
            Coach c1 = new Coach("CH01", "Coach Davis", 42, "Male", "CAF A", "Head Coach");
            t1.addPlayer(p1);
            t1.addCoach(c1);

            Stadium s1 = new Stadium("S01", "National Stadium", "Kingston", 35000, "Good", 1500.0);
            manager.registerStadium(s1);

            manager.createFixture("F01", t1, t2, s1, "2026-12-05", "15:00");

            manager.displayAllTeams();
            manager.displayAllStadiums();
            manager.displayFixtures();
        } catch (LeagueRuleException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
