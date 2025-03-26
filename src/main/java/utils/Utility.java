package utils;

import jakarta.ejb.Stateless;
import model.build.KillerBuild;
import model.build.SurvivorBuild;
import model.perk.KillerPerk;
import model.perk.SurvivorPerk;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Stateless
public class Utility {

    // TODO: уж либо перки не передавать, либо совсем общий метод делать
    public KillerBuild generateRandomKillerBuild(List<KillerPerk> perks) {
        if (perks.size() < 4) throw new IllegalStateException("Вероятно, произошла ошибка в инициализации перков.");

        // TODO: перемешивается список из глобального состояния, но нам порядок и не важен
        Collections.shuffle(perks);

        KillerBuild build = new KillerBuild();
        for(KillerPerk perk : perks.subList(0, 4)) {
            build.getPerks().add(perk);
        }

        build.getPerks().sort(Comparator.comparing(KillerPerk::getId));
        return build;
    }

    public SurvivorBuild generateRandomSurvivorBuild(List<SurvivorPerk> perks) {
        if (perks.size() < 4) throw new IllegalStateException("Вероятно, произошла ошибка в инициализации перков.");

        // перемешивается список из глобального состояния, но нам порядок и не важен
        Collections.shuffle(perks);

        SurvivorBuild build = new SurvivorBuild();
        for(SurvivorPerk perk : perks.subList(0, 4)) {
            build.getPerks().add(perk);
        }

        build.getPerks().sort(Comparator.comparing(SurvivorPerk::getId));
        return build;
    }
}
