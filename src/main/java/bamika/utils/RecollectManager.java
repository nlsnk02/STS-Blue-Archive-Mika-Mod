package bamika.utils;

import bamika.cards.Tangruochonglai;
import bamika.fantasyCard.AbstractMikaCard;
import bamika.misc.OnRecallSubscriber;
import bamika.modcore.Enums;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.HashMap;

/*
创建了类CardPosition，用于判断一张牌的位置是否变化，记录是否回响,判断是否需要触发回响

update方法在core中调用，记录所有卡牌的位置

isRecalled方法判断一张牌是否已经回想
 */
public class RecollectManager {
    public static class CardPosition {
        public CardGroup.CardGroupType position;
        public CardGroup.CardGroupType position_last;

        //这个在update中更新
        public boolean isRecalled;

        //暂时给倘若重来用的
        public boolean leaveDiscard = false;

        public CardPosition() {
            this.position = CardGroup.CardGroupType.UNSPECIFIED;
            this.position_last = CardGroup.CardGroupType.UNSPECIFIED;
            this.isRecalled = false;
        }

        public boolean checkStatus() {
            /*
            判断卡牌是否应该回想，返回卡牌是否应该触发回响
            还加上了一些接口
             */
            boolean shouldRecall = false;

            if (position == CardGroup.CardGroupType.UNSPECIFIED) return shouldRecall;

            if (position_last == CardGroup.CardGroupType.UNSPECIFIED)
                position_last = position;

            if (getPositionValue(position) - getPositionValue(position_last) == 1) {
                shouldRecall = true;
            }

            //特判倘若重来
            if (position_last == CardGroup.CardGroupType.DISCARD_PILE && position_last != position) {
                leaveDiscard = true;
            } else {
                leaveDiscard = false;
            }

            position_last = position;
            return shouldRecall;
        }

        private int getPositionValue(CardGroup.CardGroupType type) {
            if (type == CardGroup.CardGroupType.DRAW_PILE) return 2;
            if (type == CardGroup.CardGroupType.DISCARD_PILE) return 0;
            if (type == CardGroup.CardGroupType.EXHAUST_PILE) return 0;
            if (type == CardGroup.CardGroupType.HAND) return 1;

            ModHelper.logger.error("=========enter error position======");
            return 1;
        }
    }

    public static HashMap<AbstractCard, CardPosition> cardPositions = new HashMap<>();


    public static void update() {
        if (!ModHelper.isInCombat()) return;

        updatePile(AbstractDungeon.player.hand);
        updatePile(AbstractDungeon.player.drawPile);
        updatePile(AbstractDungeon.player.discardPile);
        updatePile(AbstractDungeon.player.exhaustPile);

        cardPositions.keySet().stream().forEach(c -> {
            CardPosition p = cardPositions.get(c);
            if (p.checkStatus()) {

                if (!p.isRecalled) {
                    cardRecalling(c);
                }

                if (p.leaveDiscard) {
                    Tangruochonglai.onLeaveDiscardPile(c);
                }

                p.isRecalled = true;
            }
        });
    }

    private static void updatePile(CardGroup group) {
        /*
        确定状态，更新或添加到cardPosition里
         */
        for (AbstractCard c : group.group) {
            if (cardPositions.containsKey(c)) {
                CardPosition pos = cardPositions.get(c);
                pos.position = group.type;
            } else {
                cardPositions.put(c, new CardPosition());
            }
        }
    }

    private static void cardRecalling(AbstractCard c) {
        if (c instanceof AbstractMikaCard && c.hasTag(Enums.RECOLLECT)) {
            ((AbstractMikaCard) c).onRecall();

            AbstractDungeon.player.powers.forEach(power -> {
                if (power instanceof OnRecallSubscriber) {
                    ((OnRecallSubscriber) power).onRecall(c);
                }
            });
        }
    }

    /*
    给外部使用的接口
     */
    public static boolean isRecalled(AbstractCard c) {
        return c.hasTag(Enums.RECOLLECT) && isRecalledAny(c);
    }

    /*
    管理器内部判断，任何一张牌都能被视为“被回想状态”
     */
    private static boolean isRecalledAny(AbstractCard c) {
        if (cardPositions.containsKey(c)) return cardPositions.get(c).isRecalled;
        cardPositions.put(c, new CardPosition());
        return false;
    }

    public static void flushRecall(AbstractCard c, boolean triggerEffect) {
        if (!isRecalledAny(c)) {
            cardPositions.get(c).isRecalled = true;

            if (triggerEffect) {
                cardRecalling(c);
            }
        }
    }

    public static void clearRecall(AbstractCard c) {
        if (isRecalledAny(c)) {
            cardPositions.get(c).isRecalled = false;
        }
    }

    public static void copyRecollectStatus(AbstractCard toCopy, AbstractCard newCard) {
        if (isRecalledAny(toCopy)) {
            CardPosition pNewCard = new CardPosition();
            CardPosition p2Copy = cardPositions.get(toCopy);

            pNewCard.position = p2Copy.position;
            pNewCard.position_last = p2Copy.position_last;
            pNewCard.isRecalled = p2Copy.isRecalled;

            cardPositions.put(newCard, pNewCard);
        } else {
            cardPositions.put(newCard, new CardPosition());
        }
    }
}
