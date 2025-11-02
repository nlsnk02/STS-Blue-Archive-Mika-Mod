package bamika.misc;

import bamika.utils.RecollectManager;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class GlowModifier extends AbstractCardModifier {
    @Override
    public Color getGlow(AbstractCard card) {
        if (RecollectManager.isRecalled(card))
            return Color.WHITE.cpy();
        return null;
    }

    @Override
    public boolean shouldApply(AbstractCard card) {

        for (AbstractCardModifier modifier : CardModifierManager.modifiers(card))
            if (modifier instanceof GlowModifier) {
                return false;
            }
        return true;
    }

    @Override
    public boolean isInherent(AbstractCard card) {
        return true;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new GlowModifier();
    }
}
