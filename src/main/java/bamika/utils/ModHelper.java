package bamika.utils;

import bamika.misc.OnTimeTracingSubscriber;
import bamika.misc.ReCollectGlowModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.colorless.Madness;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import bamika.modcore.Enums;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class ModHelper {

    public static String makeID(String s) {
        return "bamika:" + s;
    }

    public static String getImgPath(String indirPath) {
        return "bamikaResources/img/" + indirPath;
    }

    public static String getShaderPath(String indirPath) {
        return "bamikaResources/shaders/" + indirPath;
    }

    public static String makeRelicAd(String name, boolean isPortrait) {
        String isP = "32";
        if (isPortrait) isP = "84";

        return "bamikaResources/img/powers/" + name + isP + ".png";
    }

    public static final Logger logger = LogManager.getLogger(ModHelper.class.getName());


    public static boolean isInCombat() {
        return AbstractDungeon.player != null &&
                AbstractDungeon.currMapNode != null &&
                AbstractDungeon.currMapNode.room != null &&
                AbstractDungeon.currMapNode.room.monsters != null &&
                (AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT;
    }


    //照抄的cardgroup的resetCardBeforeMoving
    public static void refreshCard(AbstractCard c) {
        if (AbstractDungeon.player.hoveredCard == c) {
            AbstractDungeon.player.releaseCard();
        }

        AbstractDungeon.actionManager.removeFromQueue(c);
        c.unhover();
        c.untip();
        c.stopGlowing();
        c.setAngle(0);
        c.flashVfx = null;
    }

    //得到一张符合条件的卡
    public static AbstractCard getCard(Predicate<AbstractCard> predicate, Random rng) {
        ArrayList<AbstractCard> list = CardLibrary.getAllCards().stream()
                .filter(predicate)
                .collect(Collectors.toCollection(ArrayList::new));

        if (list.isEmpty())
            return new Madness();

        return list.get(rng.random(list.size() - 1)).makeCopy();
    }

    //溯时
    public static void timeTracing(AbstractCard c, CardGroup cardGroup) {
        if (cardGroup.type == CardGroup.CardGroupType.HAND) {
            moveCard(c, cardGroup, AbstractDungeon.player.drawPile);
        }
        if (cardGroup.type == CardGroup.CardGroupType.DISCARD_PILE) {
            moveCard(c, cardGroup, AbstractDungeon.player.hand);
        }
        if (cardGroup.type == CardGroup.CardGroupType.DRAW_PILE) {
            moveCard(c, cardGroup, AbstractDungeon.player.discardPile);
        }
        if (cardGroup.type == CardGroup.CardGroupType.EXHAUST_PILE) {
            moveCard(c, cardGroup, AbstractDungeon.player.hand);
        }

        AbstractDungeon.player.powers.forEach(power -> {
            if(power instanceof OnTimeTracingSubscriber){
                ((OnTimeTracingSubscriber) power).OnTimeTracing(c);
            }
        });
        if(c instanceof  OnTimeTracingSubscriber){
            ((OnTimeTracingSubscriber) c).OnTimeTracing(c);
        }
    }

    public static boolean moveCard(AbstractCard c, CardGroup to) {
        if (to.contains(c)) return false;

        if (AbstractDungeon.player.hand.contains(c)) {
            moveCard(c, AbstractDungeon.player.hand, to);
            return true;
        }
        if (AbstractDungeon.player.discardPile.contains(c)) {
            moveCard(c, AbstractDungeon.player.discardPile, to);
            return true;
        }
        if (AbstractDungeon.player.drawPile.contains(c)) {
            moveCard(c, AbstractDungeon.player.drawPile, to);
            return true;
        }
        if (AbstractDungeon.player.exhaustPile.contains(c)) {
            moveCard(c, AbstractDungeon.player.exhaustPile, to);
            return true;
        }

        return false;
    }

    //将牌从一个牌堆移动到一个新的牌堆
    public static void moveCard(AbstractCard c, CardGroup from, CardGroup to) {
        if (to.type == CardGroup.CardGroupType.HAND && AbstractDungeon.player.hand.size() >= 10) {
            return;
        }

        if (to.type == from.type) {
            return;
        }

        if (from.type == CardGroup.CardGroupType.DISCARD_PILE) {
            AbstractDungeon.player.discardPile.removeCard(c);
        }
        if (from.type == CardGroup.CardGroupType.EXHAUST_PILE) {
            c.unfadeOut();
            if (AbstractDungeon.player.hasPower("Corruption") && c.type == AbstractCard.CardType.SKILL) {
                c.setCostForTurn(-9);
            }

            AbstractDungeon.player.exhaustPile.removeCard(c);

            c.unhover();
            c.fadingOut = false;
        }
        if (from.type == CardGroup.CardGroupType.HAND) {
            AbstractDungeon.player.hand.removeCard(c);
        }
        if (from.type == CardGroup.CardGroupType.DRAW_PILE) {
            AbstractDungeon.player.drawPile.removeCard(c);
        }

        if (to.type == CardGroup.CardGroupType.DRAW_PILE) {
            from.moveToDeck(c, true);
        }
        if (to.type == CardGroup.CardGroupType.EXHAUST_PILE) {
            from.moveToExhaustPile(c);
        }
        if (to.type == CardGroup.CardGroupType.HAND) {
            from.moveToHand(c);
        }
        if (to.type == CardGroup.CardGroupType.DISCARD_PILE) {
            from.moveToDiscardPile(c);
        }
        c.lighten(false);
        c.applyPowers();
        AbstractDungeon.player.hand.refreshHandLayout();
    }
}
