package init;

import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import lombok.Getter;
import lombok.Setter;
import model.KillerPerk;
import model.SurvivorPerk;

import java.util.ArrayList;
import java.util.List;

@Singleton
@Startup
@Getter
@Setter
public class GlobalState {
    public List<KillerPerk> killerPerks = new ArrayList<>();
    public List<SurvivorPerk> survivorPerks = new ArrayList<>();
}
