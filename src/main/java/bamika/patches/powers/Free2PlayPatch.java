package bamika.patches.powers;

import bamika.powers.SuijiyingbianPower;
import bamika.powers.XvshiPower;
import bamika.utils.ModHelper;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

@SpirePatch(clz = AbstractCard.class, method = "freeToPlay")
public class Free2PlayPatch {
    @SpirePrefixPatch
    public static SpireReturn<Boolean> Prefix(AbstractCard c) {
        if (ModHelper.isInCombat()
                && (AbstractDungeon.player.hasPower(SuijiyingbianPower.POWER_ID) ||
                AbstractDungeon.player.hasPower(XvshiPower.POWER_ID))) {
            return SpireReturn.Return(true);
        }
        return SpireReturn.Continue();
    }
}
