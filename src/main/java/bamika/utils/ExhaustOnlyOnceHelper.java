package bamika.utils;

import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.HashSet;

/*
当一张牌加入其中之后，代表这张牌的消耗是一次性的，因此在core中将它的exhaust重新设置为false

每张战斗开始时刷新set
 */
public class ExhaustOnlyOnceHelper {
    public static HashSet<AbstractCard> cards2Recover = new HashSet<>();

    public static void reset(AbstractCard card) {
        if (cards2Recover.contains(card)) {
            cards2Recover.remove(card);
            card.exhaust = false;
        }
    }
}
