package bamika.patches.utils;

import bamika.utils.RecollectManager;
import basemod.helpers.CardModifierManager;
import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.CardModifierPatches;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import javassist.CtBehavior;

public class MakeStatEquivalentCopyPatch {
    @SpirePatch(clz = AbstractCard.class, method = "makeStatEquivalentCopy")
    public static class PatchAbstractCard {
        @SpireInsertPatch(locator = Locator.class, localvars = {"card"})
        public static void Insert(AbstractCard __instance, AbstractCard card) {
            RecollectManager.copyRecollectStatus(__instance, card);
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher.FieldAccessMatcher fieldAccessMatcher = new Matcher.FieldAccessMatcher(AbstractCard.class, "name");
                return LineFinder.findInOrder(ctMethodToPatch, fieldAccessMatcher);
            }
        }
    }
}
