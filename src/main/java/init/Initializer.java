package init;

import _repository.*;
import _repository.build.KillerBuildRepository;
import _repository.build.SurvivorBuildRepository;
import _repository.perk.KillerPerkRepository;
import _repository.perk.SurvivorPerkRepository;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Startup;
import jakarta.ejb.Singleton;
import jakarta.json.*;
import model.*;
import model.build.KillerBuild;
import model.build.SurvivorBuild;
import model.character.Killer;
import model.character.Survivor;
import model.perk.KillerPerk;
import model.perk.SurvivorPerk;
import model._utils.User;
import utils.Utility;

import java.io.FileInputStream;
import java.time.LocalDate;
import java.util.*;

import static utils.UserGenerator.generateUser;

@Startup
@Singleton
public class Initializer {
    private static final String PATH = "C:\\Users\\danis\\Desktop\\is-course-project\\is-course-project\\src\\main\\resources\\json\\final-output.json";

    @EJB
    protected GlobalState globalState;

    @EJB
    protected Utility utility;

    @EJB
    protected UserRepository userRepository;

    @EJB
    protected DlcRepository dlcRepository;

    @EJB
    protected KillerPerkRepository killerPerkRepository;

    @EJB
    protected KillerBuildRepository killerBuildRepository;

    @EJB
    protected SurvivorPerkRepository survivorPerkRepository;

    @EJB
    protected SurvivorBuildRepository survivorBuildRepository;
    
    public final static boolean printingAllowed = false;
    
    public void conditionalPrint(String message) {
        if (printingAllowed) {
            System.out.println(message);
        }
    }

    public void parseJson(String filePath) {
        try (FileInputStream fis = new FileInputStream(filePath);
             JsonReader reader = Json.createReader(fis)) {

            // Читаем весь JSON как объект
            JsonObject rootObject = reader.readObject();

            // Получаем массив "dlc"
            JsonArray dlcArray = rootObject.getJsonArray("dlc");

            // -----------
            List<Dlc> dlcs = new ArrayList<>();
            // -----------

            // Проходим по каждому DLC
            for (JsonValue dlcValue : dlcArray) {
                JsonObject dlc = dlcValue.asJsonObject();
                conditionalPrint("DLC: " + dlc.getString("name") + " (ID: " + dlc.getInt("dlc_id") + ")");

                // -----------
                Dlc dlcJPA = new Dlc();
                dlcJPA.setId(dlc.getInt("dlc_id"));
                dlcJPA.setName(dlc.getString("name"));
                dlcJPA.setDescription(dlc.getString("description"));
                dlcJPA.setReleaseDate(LocalDate.parse(dlc.getString("release_date").isEmpty() ? "1970-01-01" : dlc.getString("release_date")));

                List<Killer> killersJPA = new ArrayList<>();
                List<Survivor> survivorsJPA = new ArrayList<>();
                // -----------

                // Получаем массив "killer"
                JsonArray killers = dlc.getJsonArray("killer");
                conditionalPrint("Killers:");
                for (JsonValue killerValue : killers) {
                    JsonObject killer = killerValue.asJsonObject();
                    conditionalPrint("  - " + killer.getString("name") + " (ID: " + killer.getInt("killer_id") + ")");

                    // -----------
                    Killer killerJPA = new Killer();
                    killerJPA.setId(killer.getInt("killer_id"));
                    killerJPA.setName(killer.getString("name"));
                    killerJPA.setLore(killer.getString("lore"));
                    killerJPA.setDlc(dlcJPA);
                    List<KillerPerk> killerPerksJPA = new ArrayList<>();
                    // -----------

                    // Получаем массив "perk" для убийцы
                    JsonArray perks = killer.getJsonArray("perk");
                    for (JsonValue perkValue : perks) {
                        JsonObject perk = perkValue.asJsonObject();
                        conditionalPrint("    - Perk: " + perk.getString("name") + " (ID: " + perk.getInt("perk_id") + ")");

                        // -----------
                        KillerPerk killerPerkJPA = new KillerPerk();
                        killerPerkJPA.setId(perk.getInt("perk_id"));
                        killerPerkJPA.setName(perk.getString("name"));
                        // TODO: fix
                        //killerPerkJPA.setDescription(perk.getString("description") == null ? "" : perk.getString("description"));
                        killerPerkJPA.setKiller(killerJPA);
                        killerPerksJPA.add(killerPerkJPA);
                        // -----------
                    }

                    // TODO: для аддонов повторить

                    // -----------
                    killerJPA.setPerks(killerPerksJPA);
                    killersJPA.add(killerJPA);
                    // -----------
                }

                // Получаем массив "survivor"
                JsonArray survivors = dlc.getJsonArray("survivor");
                conditionalPrint("Survivors:");
                for (JsonValue survivorValue : survivors) {
                    JsonObject survivor = survivorValue.asJsonObject();
                    conditionalPrint("  - " + survivor.getString("name") + " (ID: " + survivor.getInt("survivor_id") + ")");

                    // -----------
                    Survivor survivorJPA = new Survivor();
                    survivorJPA.setId(survivor.getInt("survivor_id"));
                    survivorJPA.setName(survivor.getString("name"));
                    survivorJPA.setLore(survivor.getString("lore"));
                    survivorJPA.setDlc(dlcJPA);
                    List<SurvivorPerk> survivorPerksJPA = new ArrayList<>();
                    // -----------

                    // Получаем массив "perk" для выжившего
                    JsonArray perks = survivor.getJsonArray("perk");
                    for (JsonValue perkValue : perks) {
                        JsonObject perk = perkValue.asJsonObject();
                        conditionalPrint("    - Perk: " + perk.getString("name") + " (ID: " + perk.getInt("perk_id") + ")");

                        // -----------
                        SurvivorPerk survivorPerkJPA = new SurvivorPerk();
                        survivorPerkJPA.setId(perk.getInt("perk_id"));
                        survivorPerkJPA.setName(perk.getString("name"));
                        //survivorPerkJPA.setDescription(perk.getString("description") == null ? "" : perk.getString("description"));
                        survivorPerkJPA.setSurvivor(survivorJPA);
                        survivorPerksJPA.add(survivorPerkJPA);
                        // -----------
                    }
                    // -----------
                    survivorJPA.setPerks(survivorPerksJPA);
                    survivorsJPA.add(survivorJPA);
                    // -----------
                }
                // -----------
                dlcJPA.setSurvivors(survivorsJPA);
                dlcJPA.setKillers(killersJPA);
                dlcs.add(dlcJPA);
                // -----------
            }

            dlcRepository.createAll(dlcs);

            // инициализация GlobalState
            globalState.setKillerPerks(killerPerkRepository.findAll());
            globalState.setSurvivorPerks(survivorPerkRepository.findAll());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void generateUsers(int count) {
        List<User> users = new ArrayList<>();
        for(int i = 0; i < count; i++) {
            User user = new User();
            // нужно ли инжектить сервис?
            users.add(generateUser());
        }
        userRepository.createAll(users);
    }

    public void generateBuilds(int count) {
        List<KillerBuild> killerBuilds = new ArrayList<>();
        List<SurvivorBuild> survivorBuilds = new ArrayList<>();
        for(int i = 0; i < count; i++) {
            killerBuilds.add(utility.generateRandomKillerBuild(globalState.getKillerPerks()));
            survivorBuilds.add(utility.generateRandomSurvivorBuild(globalState.getSurvivorPerks()));
        }
        killerBuildRepository.createAll(killerBuilds);
        survivorBuildRepository.createAll(survivorBuilds);
    }

    public void generateReviews() {

    }

    // --------------

    public void generateTriggers() {

    }

    public void generateFunctionsAndProcedures() {

    }

    @PostConstruct
    public void init() {
        System.out.println("Initializing...");
        generateUsers(10);
        parseJson(PATH);
        generateBuilds(25);
    }

}
