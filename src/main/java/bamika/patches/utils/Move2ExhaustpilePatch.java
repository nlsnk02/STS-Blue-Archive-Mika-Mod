package bamika.patches.utils;

import bamika.fantasyCard.AbstractMikaCard;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;

import java.util.ArrayList;

public class Move2ExhaustpilePatch {
    @SpirePatch(clz = CardGroup.class, method = "moveToExhaustPile")
    public static class RemoveEhaust {
        @SpirePrefixPatch
        public static void Prefix(CardGroup group, AbstractCard c) {
            if(c instanceof AbstractMikaCard){
                AbstractMikaCard mikaCard = (AbstractMikaCard)c;
                if(mikaCard.exhaustOnlyOnce){
                    mikaCard.exhaustOnlyOnce = false;
                    mikaCard.exhaust = false;
                }
            }
        }
    }
}
