package bamika.patches.cards;

import bamika.powers.BeishuiyizhanPower;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.stances.DivinityStance;

public class BeishuiyizhanPatch {

    @SpirePatch(clz = DivinityStance.class,
            method = "atStartOfTurn")
    public static class DontQuitDivinityStance {
        public static SpireReturn<Void> Prefix(DivinityStance __instance) {
            if(AbstractDungeon.player.hasPower(BeishuiyizhanPower.POWER_ID)){
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }
}
