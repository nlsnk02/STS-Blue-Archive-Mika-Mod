package bamika.utils;

import bamika.fantasyCard.AbstractMikaCard;
import bamika.misc.GlowModifier;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Color;
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

        public boolean isRecalled;

        public CardPosition() {
            this.position = CardGroup.CardGroupType.UNSPECIFIED;
            this.position_last = CardGroup.CardGroupType.UNSPECIFIED;
            this.isRecalled = false;
        }

        public boolean update() {
            /*
            判断卡牌是否应该回想，返回卡牌是否应该触发回响
             */
            boolean shouldRecall = false;

            if (position == CardGroup.CardGroupType.UNSPECIFIED) return shouldRecall;

            if (position_last == CardGroup.CardGroupType.UNSPECIFIED)
                position_last = position;

            if (getPositionValue(position) - getPositionValue(position_last) == 1) {
                isRecalled = true;
                shouldRecall = true;
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
            if (p.update()) {
                if (c instanceof AbstractMikaCard) {
                    ((AbstractMikaCard) c).onRecall();
                }

                CardModifierManager.addModifier(c, new GlowModifier());
            }
        });
    }

    private static void updatePile(CardGroup group) {
        for (AbstractCard c : group.group) {
            if (cardPositions.containsKey(c)) {
                CardPosition pos = cardPositions.get(c);
                pos.position = group.type;
            } else {
                cardPositions.put(c, new CardPosition());
            }
        }
    }

    public static boolean isRecalled(AbstractCard c) {
        if (cardPositions.containsKey(c)) return cardPositions.get(c).isRecalled;
        ModHelper.logger.error("=========error recalled======");
        cardPositions.put(c, new CardPosition());
        return false;
    }
}
