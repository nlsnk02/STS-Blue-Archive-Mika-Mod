package bamika.modcore;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.CardLibrary;


public class Enums {

    @SpireEnum
    public static AbstractPlayer.PlayerClass MIKA_CLASS;
    @SpireEnum(name = "bamika")
    public static CardLibrary.LibraryType MikaLibraryType;
    @SpireEnum(name = "bamika")
    public static AbstractCard.CardColor MIKA_COLOR;
    @SpireEnum
    public static AbstractCard.CardTags RECOLLECT;
}