package bamika.misc;

import com.megacrit.cardcrawl.cards.CardGroup;

/*
给牌使用
 */
public interface ChangePositionSubscriber {
    void onChangePosition(CardGroup.CardGroupType from, CardGroup.CardGroupType to);
}
