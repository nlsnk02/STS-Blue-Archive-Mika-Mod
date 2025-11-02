package bamika.utils;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.blue.Hologram;
import com.megacrit.cardcrawl.cards.red.Exhume;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.BufferPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import bamika.modcore.Enums;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;


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

    //溯时
    public static void timeTracing(AbstractCard c, CardGroup group) {
        if(group.type == CardGroup.CardGroupType.DISCARD_PILE) {
            if (AbstractDungeon.player.hand.size() < 10) {
                AbstractDungeon.player.hand.addToHand(c);
                AbstractDungeon.player.discardPile.removeCard(c);
            }
        }

        if(group.type == CardGroup.CardGroupType.EXHAUST_PILE) {
            if (AbstractDungeon.player.hand.size() < 10) {

                c.unfadeOut();
                AbstractDungeon.player.hand.addToHand(c);
                if (AbstractDungeon.player.hasPower("Corruption") && c.type == AbstractCard.CardType.SKILL) {
                    c.setCostForTurn(-9);
                }

                AbstractDungeon.player.exhaustPile.removeCard(c);

                c.unhover();
                c.fadingOut = false;
            }
        }

        if(group.type == CardGroup.CardGroupType.HAND){
            AbstractDungeon.player.hand.moveToDeck(c, true);
            AbstractDungeon.player.hand.removeCard(c);
        }

        if(group.type == CardGroup.CardGroupType.DRAW_PILE){
            AbstractDungeon.player.drawPile.moveToDiscardPile(c);
        }

        c.lighten(false);
        c.applyPowers();
        AbstractDungeon.player.hand.refreshHandLayout();
    }
}
