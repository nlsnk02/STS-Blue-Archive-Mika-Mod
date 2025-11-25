package bamika.patches.cards;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.defect.ShuffleAllAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ZhuanzhuPatch {

    public static AbstractCreature lastAttacked = null;

    @SpirePatch(clz = UseCardAction.class,
            method = "<ctor>",
            paramtypez = {
                    AbstractCard.class,
                    AbstractCreature.class
            })
    public static class MarkDamgeTarget {
        public static void Postfix(UseCardAction __instance, AbstractCard card, AbstractCreature target) {
            if (card.type == AbstractCard.CardType.ATTACK) {
                if (card.target == AbstractCard.CardTarget.ENEMY)
                    lastAttacked = target;
                else lastAttacked = null;
            }
        }
    }
}
