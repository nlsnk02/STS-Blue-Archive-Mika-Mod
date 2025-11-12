package bamika.misc;

import com.megacrit.cardcrawl.cards.AbstractCard;

/*
一张牌触发回想效果时的接口，暂时只对能力生效
 */
public interface OnRecallSubscriber {
    void onRecall(AbstractCard card);
}
