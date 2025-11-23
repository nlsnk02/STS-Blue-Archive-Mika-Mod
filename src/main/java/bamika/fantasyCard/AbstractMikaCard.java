package bamika.fantasyCard;

import bamika.misc.ReCollectGlowModifier;
import bamika.modcore.Enums;
import bamika.utils.ModHelper;
import bamika.utils.RecollectManager;
import basemod.abstracts.AbstractCardModifier;
import basemod.abstracts.CustomCard;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public abstract class AbstractMikaCard extends CustomCard {
    private static final TextureAtlas cardAtlas = new TextureAtlas(Gdx.files.internal("cards/cards.atlas"));

    public CardStrings cardStrings;

    public boolean exhaustOnlyOnce;

    public AbstractMikaCard(
            String NAME,
            int COST,
            CardType TYPE,
            CardRarity RARITY,
            CardTarget TARGET
    ) {
        this(NAME, COST, TYPE, RARITY, TARGET, Enums.MIKA_COLOR);
    }

    public AbstractMikaCard(
            String NAME,
            int COST,
            CardType TYPE,
            CardRarity RARITY,
            CardTarget TARGET,
            CardColor color
    ) {
        super("bamika:" + NAME, getCardStrings(NAME).NAME, getPicPath(NAME, TYPE),
                COST, getCardStrings(NAME).DESCRIPTION, TYPE, color, RARITY, TARGET);
        cardStrings = getCardStrings(NAME);
        exhaustOnlyOnce = false;
    }

    /*
    自动加载卡牌的卡图
    如果cards文件夹下方有某个卡图皮肤id的文件夹，则优先使用其中的皮肤卡图
    什么是卡图皮肤id可以看ui.json里面的注释
     */
    public static String getPicPath(String name, CardType type) {
        if (Gdx.files.internal(ModHelper.getImgPath("cards/" + name + ".png")).exists())
            return ModHelper.getImgPath("cards/" + name + ".png");

        if (type == CardType.ATTACK)
            return ModHelper.getImgPath("cards/attack.png");
        else if (type == CardType.SKILL)
            return ModHelper.getImgPath("cards/skill.png");
        else if (type == CardType.POWER)
            return ModHelper.getImgPath("cards/power.png");
        else
            return ModHelper.getImgPath("cards/temp.png");
    }

    public static CardStrings getCardStrings(String name) {
        return CardCrawlGame.languagePack.
                getCardStrings("bamika:" + name);
    }

    public void steal(AbstractCard c) {
        String img = c.assetUrl;
        this.portrait = AbstractMikaCard.cardAtlas.findRegion(img);
        this.assetUrl = img;
    }

    public void onRecall() {

    }

    public void setRecollectCard() {
        this.tags.add(Enums.RECOLLECT);
        CardModifierManager.addModifier(this, new ReCollectGlowModifier());
    }

    @Override
    public abstract void use(AbstractPlayer p, AbstractMonster m);

    @Override
    public abstract void upgrade();
}
