import java.util.ArrayList;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;


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


// KPFL - Core Functionality items 5 to 8
// Item 5: match officials 
class MatchOfficial extends Person {
    private String officiatingRole;
    private String rank;           
    private boolean available;


    public MatchOfficial(String id, String name, int age, String gender, String officiatingRole, String rank) {
        super(id, name, age, gender);
        this.officiatingRole = officiatingRole;
        this.rank = rank;
        this.available = true;
    }


    public String getOfficiatingRole() {
        return officiatingRole;
    }


    public String getRank() {
        return rank;
    }


    public boolean isAvailable() {
        return available;
    }


    public void setAvailable(boolean available) {
        this.available = available;
    }


    public void showInfo() {
        System.out.println("Official: " + getName() + " [" + getId() + "] - " + officiatingRole + " (" + rank + ")"
                + (available ? "" : " - UNAVAILABLE"));
    }
}


// Links one official to one fixture
class OfficialAssignment {
    private Fixture fixture;
    private MatchOfficial official;


    public OfficialAssignment(Fixture fixture, MatchOfficial official) {
        this.fixture = fixture;
        this.official = official;
    }


    public Fixture getFixture() {
        return fixture;
    }


    public MatchOfficial getOfficial() {
        return official;
    }
}


// Item 6: match results
class MatchResult {
    private Fixture fixture;
    private int homeGoals;
    private int awayGoals;


    public MatchResult(Fixture fixture, int homeGoals, int awayGoals) throws LeagueRuleException {
        if (homeGoals < 0 || awayGoals < 0) {
            throw new LeagueRuleException("Match scores cannot be negative.");
        }
        this.fixture = fixture;
        this.homeGoals = homeGoals;
        this.awayGoals = awayGoals;
    }


    public Fixture getFixture() {
        return fixture;
    }


    public int getHomeGoals() {
        return homeGoals;
    }


    public int getAwayGoals() {
        return awayGoals;
    }


    // returns null when the match is a draw
    public FootballTeam getWinner() {
        if (homeGoals > awayGoals) {
            return fixture.getHomeTeam();
        } else if (awayGoals > homeGoals) {
            return fixture.getAwayTeam();
        }
        return null;
    }


    public void displayResult() {
        FootballTeam winner = getWinner();
        System.out.println("Result [" + fixture.getFixtureId() + "] " + fixture.getHomeTeam().getName() + " "
                + homeGoals + " - " + awayGoals + " " + fixture.getAwayTeam().getName()
                + (winner == null ? " (Draw)" : " (Winner: " + winner.getName() + ")"));
    }
}


// Items 5 and 6: league operations
class LeagueOperations {
    private SetupManager setup; 
    private List<MatchOfficial> officials;
    private List<OfficialAssignment> assignments;
    private List<MatchResult> results;


    public LeagueOperations(SetupManager setup) {
        this.setup = setup;
        this.officials = new ArrayList<>();
        this.assignments = new ArrayList<>();
        this.results = new ArrayList<>();
    }


    public List<MatchOfficial> getOfficials() {
        return officials;
    }


    public List<OfficialAssignment> getAssignments() {
        return assignments;
    }


    public List<MatchResult> getResults() {
        return results;
    }


    // 5. register officials
    public void registerOfficial(MatchOfficial official) throws LeagueRuleException {
        if (findOfficialById(official.getId()) != null) {
            throw new LeagueRuleException("Official " + official.getId() + " is already registered.");
        }
        officials.add(official);
    }


    public MatchOfficial findOfficialById(String officialId) {
        for (int i = 0; i < officials.size(); i++) {
            if (officials.get(i).getId().equalsIgnoreCase(officialId)) {
                return officials.get(i);
            }
        }
        return null;
    }


    // 5. assign an official to a fixture without scheduling conflicts
    public void assignOfficial(Fixture fixture, MatchOfficial official) throws LeagueRuleException {
        if (fixture == null || official == null) {
            throw new LeagueRuleException("Fixture and official must both exist.");
        }
        if (!official.isAvailable()) {
            throw new LeagueRuleException(official.getName() + " is not available.");
        }
        for (int i = 0; i < assignments.size(); i++) {
            OfficialAssignment a = assignments.get(i);
            if (a.getOfficial() == official) {
                Fixture other = a.getFixture();
                if (other == fixture) {
                    throw new LeagueRuleException(official.getName() + " is already assigned to this fixture.");
                }
                if (other.getDate().equals(fixture.getDate()) && other.getTime().equals(fixture.getTime())) {
                    throw new LeagueRuleException(official.getName() + " already has fixture "
                            + other.getFixtureId() + " at " + fixture.getDate() + " " + fixture.getTime() + ".");
                }
            }
        }
        assignments.add(new OfficialAssignment(fixture, official));
    }


    public List<MatchOfficial> getOfficialsForFixture(Fixture fixture) {
        List<MatchOfficial> list = new ArrayList<>();
        for (int i = 0; i < assignments.size(); i++) {
            if (assignments.get(i).getFixture() == fixture) {
                list.add(assignments.get(i).getOfficial());
            }
        }
        return list;
    }


    public void displayOfficials() {
        System.out.println("--- Officials ---");
        for (int i = 0; i < officials.size(); i++) {
            officials.get(i).showInfo();
        }
    }


    // 6. record a result, award points (win 3, draw 1, loss 0) and complete the fixture
    public void recordResult(Fixture fixture, int homeGoals, int awayGoals) throws LeagueRuleException {
        if (fixture == null) {
            throw new LeagueRuleException("Fixture does not exist.");
        }
        if ("COMPLETED".equalsIgnoreCase(fixture.getStatus())) {
            throw new LeagueRuleException("Fixture " + fixture.getFixtureId() + " already has a result.");
        }
        MatchResult result = new MatchResult(fixture, homeGoals, awayGoals); // rejects negative scores
        results.add(result);


        FootballTeam winner = result.getWinner();
        if (winner == null) {
            fixture.getHomeTeam().addPoints(1);
            fixture.getAwayTeam().addPoints(1);
        } else {
            winner.addPoints(3);
        }
        completeFixture(fixture);
    }


    public MatchResult findResult(Fixture fixture) {
        for (int i = 0; i < results.size(); i++) {
            if (results.get(i).getFixture() == fixture) {
                return results.get(i);
            }
        }
        return null;
    }


    // Business rule: a fixture cannot be completed until a valid result has been recorded
    public void completeFixture(Fixture fixture) throws LeagueRuleException {
        if (findResult(fixture) == null) {
            throw new LeagueRuleException("Fixture " + fixture.getFixtureId() + " cannot be completed without a result.");
        }
        fixture.setStatus("COMPLETED");
    }


    // goal difference worked out from the recorded results
    public int getGoalDifference(FootballTeam team) {
        int diff = 0;
        for (int i = 0; i < results.size(); i++) {
            MatchResult r = results.get(i);
            if (r.getFixture().getHomeTeam() == team) {
                diff = diff + r.getHomeGoals() - r.getAwayGoals();
            } else if (r.getFixture().getAwayTeam() == team) {
                diff = diff + r.getAwayGoals() - r.getHomeGoals();
            }
        }
        return diff;
    }


    // 6. standings: most points first, then best goal difference
    public List<FootballTeam> getStandings() {
        List<FootballTeam> table = new ArrayList<>(setup.getTeams());
        for (int i = 0; i < table.size() - 1; i++) {
            for (int j = 0; j < table.size() - 1 - i; j++) {
                FootballTeam a = table.get(j);
                FootballTeam b = table.get(j + 1);
                boolean swap = b.getPoints() > a.getPoints()
                        || (b.getPoints() == a.getPoints() && getGoalDifference(b) > getGoalDifference(a));
                if (swap) {
                    table.set(j, b);
                    table.set(j + 1, a);
                }
            }
        }
        return table;
    }


    public void displayStandings() {
        System.out.println("--- Standings ---");
        List<FootballTeam> table = getStandings();
        for (int i = 0; i < table.size(); i++) {
            FootballTeam t = table.get(i);
            System.out.println((i + 1) + ". " + t.getName() + " | Pts: " + t.getPoints() + " | GD: " + getGoalDifference(t));
        }
    }
}


// Item 7: tickets
class Spectator extends Person {
    public Spectator(String id, String name, int age, String gender) {
        super(id, name, age, gender);
    }


    public void showInfo() {
        System.out.println("Spectator: " + getName() + " [" + getId() + "]");
    }
}


class Ticket {
    private String ticketNumber;
    private Fixture fixture;
    private Spectator spectator;
    private String category;
    private double price; // stadium ticket price before GCT (tax is item 9)


    public Ticket(String ticketNumber, Fixture fixture, Spectator spectator, String category, double price) {
        this.ticketNumber = ticketNumber;
        this.fixture = fixture;
        this.spectator = spectator;
        this.category = category;
        this.price = price;
    }


    public String getTicketNumber() {
        return ticketNumber;
    }


    public Fixture getFixture() {
        return fixture;
    }


    public Spectator getSpectator() {
        return spectator;
    }


    public String getCategory() {
        return category;
    }


    public double getPrice() {
        return price;
    }


    public void displayTicket() {
        System.out.println("Ticket " + ticketNumber + " | " + fixture.getHomeTeam().getName() + " vs "
                + fixture.getAwayTeam().getName() + " | " + category + " | " + spectator.getName() + " | $" + price);
    }
}


// Item 8: products
abstract class Product {
    private String productId;
    private String name;
    private double price; // before GCT (tax is item 9)
    private int quantityInStock;


    public Product(String productId, String name, double price, int quantityInStock) throws LeagueRuleException {
        if (price < 0 || quantityInStock < 0) {
            throw new LeagueRuleException("Price and quantity cannot be negative.");
        }
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantityInStock = quantityInStock;
    }


    public String getProductId() {
        return productId;
    }


    public String getName() {
        return name;
    }


    public double getPrice() {
        return price;
    }


    public void setPrice(double price) throws LeagueRuleException {
        if (price < 0) {
            throw new LeagueRuleException("Price cannot be negative.");
        }
        this.price = price;
    }


    public int getQuantityInStock() {
        return quantityInStock;
    }


    public void addStock(int qty) throws LeagueRuleException {
        if (qty <= 0) {
            throw new LeagueRuleException("Restock quantity must be greater than zero.");
        }
        quantityInStock = quantityInStock + qty;
    }


    public void reduceStock(int qty) throws LeagueRuleException {
        if (qty <= 0) {
            throw new LeagueRuleException("Quantity must be greater than zero.");
        }
        if (qty > quantityInStock) {
            throw new LeagueRuleException("Only " + quantityInStock + " of " + name + " in stock.");
        }
        quantityInStock = quantityInStock - qty;
    }


    public abstract String getCategory();


    public void showInfo() {
        System.out.println("[" + productId + "] " + name + " | " + getCategory() + " | $" + price + " | Stock: " + quantityInStock);
    }
}


class Merchandise extends Product {
    private String merchandiseType; 


    public Merchandise(String productId, String name, double price, int quantityInStock, String merchandiseType)
            throws LeagueRuleException {
        super(productId, name, price, quantityInStock);
        this.merchandiseType = merchandiseType;
    }


    public String getMerchandiseType() {
        return merchandiseType;
    }


    public String getCategory() {
        return "Merchandise (" + merchandiseType + ")";
    }
}


class FoodItem extends Product {
    private boolean beverage;


    public FoodItem(String productId, String name, double price, int quantityInStock, boolean beverage)
            throws LeagueRuleException {
        super(productId, name, price, quantityInStock);
        this.beverage = beverage;
    }


    public boolean isBeverage() {
        return beverage;
    }


    public String getCategory() {
        return beverage ? "Beverage" : "Food";
    }
}


//Items 7 and 8: stadium shop (sells tickets and products)
// Each sale is in two steps so item 9 (tax, total, payment, change) can check payment in between:
//   step 1: get...Subtotal(...)  -> checks the rules, returns the subtotal, changes nothing
//   step 2: issueTickets(...) / completeSale(...) -> only called after payment is accepted
class StadiumShop {
    private List<Product> inventory;
    private List<Ticket> tickets;
    private int nextTicketNumber;


    public StadiumShop() {
        this.inventory = new ArrayList<>();
        this.tickets = new ArrayList<>();
        this.nextTicketNumber = 1;
    }


    public List<Product> getInventory() {
        return inventory;
    }


    public List<Ticket> getTickets() {
        return tickets;
    }


    // ===== 7. ticket sales with capacity monitoring =====
    public int getTicketsSold(Fixture fixture) {
        int count = 0;
        for (int i = 0; i < tickets.size(); i++) {
            if (tickets.get(i).getFixture() == fixture) {
                count++;
            }
        }
        return count;
    }


    public int getSeatsRemaining(Fixture fixture) {
        return fixture.getStadium().getCapacity() - getTicketsSold(fixture);
    }


    // step 1: checks quantity and capacity, returns the price before tax
    public double getTicketSubtotal(Fixture fixture, int qty) throws LeagueRuleException {
        if (fixture == null) {
            throw new LeagueRuleException("Fixture does not exist.");
        }
        if (qty <= 0) {
            throw new LeagueRuleException("Ticket quantity must be greater than zero.");
        }
        if (qty > getSeatsRemaining(fixture)) {
            throw new LeagueRuleException("Only " + getSeatsRemaining(fixture) + " seat(s) left for fixture "
                    + fixture.getFixtureId() + ".");
        }
        return fixture.getStadium().getTicketPrice() * qty;
    }


    // step 2: creates the tickets (re-checks capacity so it can never be exceeded)
    public List<Ticket> issueTickets(Fixture fixture, Spectator spectator, String category, int qty)
            throws LeagueRuleException {
        getTicketSubtotal(fixture, qty);
        if (spectator == null) {
            throw new LeagueRuleException("Spectator is required.");
        }
        List<Ticket> issued = new ArrayList<>();
        for (int i = 0; i < qty; i++) {
            String number = "T" + String.format("%04d", nextTicketNumber);
            nextTicketNumber++;
            Ticket t = new Ticket(number, fixture, spectator, category, fixture.getStadium().getTicketPrice());
            tickets.add(t);
            issued.add(t);
        }
        return issued;
    }


    //8. product management
    public void addProduct(Product p) throws LeagueRuleException {
        if (findProductById(p.getProductId()) != null) {
            throw new LeagueRuleException("Product " + p.getProductId() + " already exists.");
        }
        inventory.add(p);
    }


    public Product findProductById(String productId) {
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).getProductId().equalsIgnoreCase(productId)) {
                return inventory.get(i);
            }
        }
        return null;
    }


    public void updatePrice(String productId, double newPrice) throws LeagueRuleException {
        Product p = findProductById(productId);
        if (p == null) {
            throw new LeagueRuleException("Product " + productId + " not found.");
        }
        p.setPrice(newPrice);
    }


    public void restock(String productId, int qty) throws LeagueRuleException {
        Product p = findProductById(productId);
        if (p == null) {
            throw new LeagueRuleException("Product " + productId + " not found.");
        }
        p.addStock(qty);
    }


    public void removeProduct(String productId) throws LeagueRuleException {
        Product p = findProductById(productId);
        if (p == null) {
            throw new LeagueRuleException("Product " + productId + " not found.");
        }
        inventory.remove(p);
    }


    public void displayInventory() {
        System.out.println("--- Shop Inventory ---");
        for (int i = 0; i < inventory.size(); i++) {
            inventory.get(i).showInfo(); // polymorphism: getCategory() differs per subclass
        }
    }


    //8. shop transaction (cart = product ID -> quantity) 
    // step 1: checks every item and stock, returns the subtotal before tax, changes nothing
    public double getCartSubtotal(Map<String, Integer> cart) throws LeagueRuleException {
        if (cart == null || cart.isEmpty()) {
            throw new LeagueRuleException("Cart is empty.");
        }
        double subtotal = 0;
        for (Map.Entry<String, Integer> item : cart.entrySet()) {
            Product p = findProductById(item.getKey());
            if (p == null) {
                throw new LeagueRuleException("Product " + item.getKey() + " not found.");
            }
            int qty = item.getValue();
            if (qty <= 0) {
                throw new LeagueRuleException("Quantity for " + p.getName() + " must be greater than zero.");
            }
            if (qty > p.getQuantityInStock()) {
                throw new LeagueRuleException("Only " + p.getQuantityInStock() + " of " + p.getName() + " in stock.");
            }
            subtotal = subtotal + p.getPrice() * qty;
        }
        return subtotal;
    }


    // step 2: completes the sale by taking the items out of stock
    public void completeSale(Map<String, Integer> cart) throws LeagueRuleException {
        getCartSubtotal(cart); // re-check so stock never goes below zero
        for (Map.Entry<String, Integer> item : cart.entrySet()) {
            findProductById(item.getKey()).reduceStock(item.getValue());
        }
    }
}


// Test driver for items 5-8 (run with: java TestParts5to8) 
// Test driver for items 5-8: sets up sample data with Parts 1-4, then checks normal, boundary and invalid cases.
class TestParts5to8 {
    private static int passed = 0;
    private static int failed = 0;


    private interface Action {
        void run() throws LeagueRuleException;
    }


    private static void expectOk(String label, Action a) {
        try {
            a.run();
            passed++;
            System.out.println("PASS  " + label);
        } catch (LeagueRuleException e) {
            failed++;
            System.out.println("FAIL  " + label + " -> " + e.getMessage());
        }
    }


    private static void expectError(String label, Action a) {
        try {
            a.run();
            failed++;
            System.out.println("FAIL  " + label + " -> no error thrown");
        } catch (LeagueRuleException e) {
            passed++;
            System.out.println("PASS  " + label + "  [" + e.getMessage() + "]");
        }
    }


    private static void check(String label, boolean ok) {
        if (ok) {
            passed++;
            System.out.println("PASS  " + label);
        } else {
            failed++;
            System.out.println("FAIL  " + label);
        }
    }


    public static void main(String[] args) throws LeagueRuleException {
        // ---- sample data using Parts 1-4 ----
        SetupManager setup = new SetupManager();
        LeagueCommissioner comm = new LeagueCommissioner("C01", "Mr. Brown", 48, "Male");
        FootballTeam t1 = new FootballTeam("T01", "Harbour View", "Kingston");
        FootballTeam t2 = new FootballTeam("T02", "Waterhouse", "Kingston");
        FootballTeam t3 = new FootballTeam("T03", "Arnett Gardens", "Kingston");
        FootballTeam t4 = new FootballTeam("T04", "Tivoli Gardens", "Kingston");
        FootballTeam[] all = {t1, t2, t3, t4};
        for (FootballTeam t : all) {
            setup.registerTeam(t);
            comm.approveTeam(t);
        }
        Stadium s1 = new Stadium("S01", "Anthony Spaulding", "Kingston", 100, "Good", 1000.0);
        Stadium s2 = new Stadium("S02", "Edward Seaga Complex", "Kingston", 50, "Fair", 800.0);
        setup.registerStadium(s1);
        setup.registerStadium(s2);
        Fixture f1 = setup.createFixture("F01", t1, t2, s1, "2026-12-05", "15:00");
        Fixture f2 = setup.createFixture("F02", t3, t4, s2, "2026-12-05", "15:00");


        LeagueOperations ops = new LeagueOperations(setup);
        StadiumShop shop = new StadiumShop();


        System.out.println("\nItem 5: officials");
        MatchOfficial o1 = new MatchOfficial("O01", "Oshane Nation", 38, "Male", "Referee", "FIFA");
        MatchOfficial o2 = new MatchOfficial("O02", "Daneon Parchment", 40, "Male", "Referee", "National");
        expectOk("Register official O01", () -> ops.registerOfficial(o1));
        expectOk("Register official O02", () -> ops.registerOfficial(o2));
        expectError("Duplicate official rejected", () -> ops.registerOfficial(o1));
        expectOk("Assign O01 to F01", () -> ops.assignOfficial(f1, o1));
        expectError("O01 to F01 again rejected", () -> ops.assignOfficial(f1, o1));
        expectError("O01 to F02 (same date/time) rejected", () -> ops.assignOfficial(f2, o1));
        o2.setAvailable(false);
        expectError("Unavailable official rejected", () -> ops.assignOfficial(f2, o2));
        o2.setAvailable(true);
        expectOk("Assign O02 to F02", () -> ops.assignOfficial(f2, o2));
        check("F01 has 1 official", ops.getOfficialsForFixture(f1).size() == 1);


        System.out.println("\n=== Item 6: results and standings ===");
        expectError("Negative score rejected", () -> ops.recordResult(f1, -1, 0));
        expectError("Complete F02 without result rejected", () -> ops.completeFixture(f2));
        expectOk("Record F01 2-1", () -> ops.recordResult(f1, 2, 1));
        expectOk("Record F02 0-0 (draw, lowest valid score)", () -> ops.recordResult(f2, 0, 0));
        expectError("Second result for F01 rejected", () -> ops.recordResult(f1, 5, 0));
        check("F01 status COMPLETED", f1.getStatus().equals("COMPLETED"));
        check("Winner Harbour View has 3 pts", t1.getPoints() == 3);
        check("Loser Waterhouse has 0 pts", t2.getPoints() == 0);
        check("Draw gives Arnett and Tivoli 1 pt each", t3.getPoints() == 1 && t4.getPoints() == 1);
        check("Harbour View tops standings", ops.getStandings().get(0) == t1);
        check("Waterhouse bottom (GD -1)", ops.getStandings().get(3) == t2);
        ops.displayStandings();


        System.out.println("\n=== Item 7: tickets (F01 capacity 100, $1000 each) ===");
        Spectator fan = new Spectator("SP01", "Tanya Reid", 30, "Female");
        expectOk("Subtotal for 98 tickets is 98000",
                () -> check("  subtotal = 98000", shop.getTicketSubtotal(f1, 98) == 98000.0));
        expectOk("Issue 98 tickets", () -> shop.issueTickets(f1, fan, "General", 98));
        expectOk("Issue last 2 (stadium exactly full)", () -> shop.issueTickets(f1, fan, "General", 2));
        check("Seats remaining = 0", shop.getSeatsRemaining(f1) == 0);
        expectError("Ticket 101 rejected", () -> shop.issueTickets(f1, fan, "General", 1));
        expectError("Zero tickets rejected", () -> shop.getTicketSubtotal(f2, 0));
        expectError("Negative tickets rejected", () -> shop.issueTickets(f2, fan, "General", -3));
        check("Ticket count for F01 = 100", shop.getTicketsSold(f1) == 100);


        System.out.println("\n=== Item 8: products and shop sale ===");
        expectOk("Add jersey", () -> shop.addProduct(new Merchandise("M01", "Home Jersey", 4000, 10, "Jersey")));
        expectOk("Add flag", () -> shop.addProduct(new Merchandise("M02", "Team Flag", 1200, 3, "Flag")));
        expectOk("Add food", () -> shop.addProduct(new FoodItem("FD1", "Jerk Chicken", 1500, 40, false)));
        expectOk("Add drink", () -> shop.addProduct(new FoodItem("BV1", "Bottled Water", 200, 100, true)));
        expectError("Duplicate product rejected", () -> shop.addProduct(new FoodItem("BV1", "Juice", 300, 5, true)));
        expectError("Negative stock rejected", () -> new FoodItem("BV2", "Juice", 300, -5, true));
        expectOk("Update price", () -> shop.updatePrice("BV1", 250));
        expectError("Negative price rejected", () -> shop.updatePrice("BV1", -1));
        expectOk("Restock flags +5", () -> shop.restock("M02", 5));
        expectOk("Remove product", () -> shop.removeProduct("FD1"));
        check("Inventory now has 3 products", shop.getInventory().size() == 3);


        Map<String, Integer> cart = new LinkedHashMap<>();
        cart.put("M01", 1);
        cart.put("BV1", 2);
        expectOk("Cart subtotal 4000 + 2x250 = 4500",
                () -> check("  subtotal = 4500", shop.getCartSubtotal(cart) == 4500.0));
        check("Stock unchanged after subtotal only", shop.findProductById("M01").getQuantityInStock() == 10);
        expectOk("Complete sale", () -> shop.completeSale(cart));
        check("Jersey stock now 9", shop.findProductById("M01").getQuantityInStock() == 9);
        check("Water stock now 98", shop.findProductById("BV1").getQuantityInStock() == 98);


        Map<String, Integer> allFlags = new LinkedHashMap<>();
        allFlags.put("M02", 8);
        expectOk("Buy all 8 flags (stock to exactly 0)", () -> shop.completeSale(allFlags));
        Map<String, Integer> oneMore = new LinkedHashMap<>();
        oneMore.put("M02", 1);
        expectError("Flag when out of stock rejected", () -> shop.completeSale(oneMore));
        Map<String, Integer> badQty = new LinkedHashMap<>();
        badQty.put("BV1", -2);
        expectError("Negative quantity rejected", () -> shop.getCartSubtotal(badQty));
        expectError("Empty cart rejected", () -> shop.getCartSubtotal(new LinkedHashMap<>()));
        Map<String, Integer> unknown = new LinkedHashMap<>();
        unknown.put("ZZZ", 1);
        expectError("Unknown product rejected", () -> shop.getCartSubtotal(unknown));
        shop.displayInventory();


        System.out.println("\nRESULT: " + passed + " passed, " + failed + " failed");
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
