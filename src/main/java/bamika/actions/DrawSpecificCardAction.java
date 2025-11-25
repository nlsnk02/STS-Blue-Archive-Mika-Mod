package bamika.actions;

import bamika.utils.ModHelper;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DrawSpecificCardAction extends AbstractGameAction {

    private int num;
    private Predicate<AbstractCard> predicate;

    public DrawSpecificCardAction(int num, Predicate<AbstractCard> predicate) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.num = num;
        this.predicate = predicate;
    }

    @Override
    public void update() {
        if (AbstractDungeon.player.hasPower("No Draw")) {
            AbstractDungeon.player.getPower("No Draw").flash();
            tickDuration();
            return;
        }


        if (this.duration == Settings.ACTION_DUR_FAST) {
            int free = Math.min(10 - AbstractDungeon.player.hand.size(), num);

            if (free == 0) {
                tickDuration();
                return;
            }

            ArrayList<AbstractCard> cardsInDeck = AbstractDungeon.player.hand.group.stream()
                    .filter(predicate).collect(Collectors.toCollection(ArrayList::new));

            boolean flag = cardsInDeck.size() >= free;

            int tmp = free;
            for (AbstractCard c : cardsInDeck) {
                tmp--;
                ModHelper.moveCard(c, AbstractDungeon.player.drawPile, AbstractDungeon.player.hand);
                AbstractDungeon.player.draw();
                AbstractDungeon.player.hand.refreshHandLayout();
                if (tmp <= 0) break;
            }

            if (flag) {
                addToTop(new DrawSpecificCardAction(tmp, predicate));
                addToTop(new EmptyDeckShuffleAction());
            }
        }
        tickDuration();
    }
}
