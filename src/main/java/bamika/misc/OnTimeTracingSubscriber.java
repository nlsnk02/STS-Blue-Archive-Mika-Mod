package bamika.misc;

import com.megacrit.cardcrawl.cards.AbstractCard;

/*
触发回溯时触发，一张牌一次，暂时只能对能力和被触发的牌用
 */
public interface OnTimeTracingSubscriber {
    void OnTimeTracing(AbstractCard card);
}
