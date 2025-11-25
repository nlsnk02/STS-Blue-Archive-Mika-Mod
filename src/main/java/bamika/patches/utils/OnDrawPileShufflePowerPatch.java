package bamika.patches.utils;

import bamika.cards.Tangruochonglai;
import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.common.ShuffleAction;
import com.megacrit.cardcrawl.actions.defect.ShuffleAllAction;


public class OnDrawPileShufflePowerPatch {

    private static void OnDrawPileShuffle() {
//        Tangruochonglai.onShuffle();
    }

    @SpirePatch(clz = EmptyDeckShuffleAction.class, method = "<ctor>")
    public static class ShufflePatch1 {
        public static void Postfix(EmptyDeckShuffleAction __instance) {
            OnDrawPileShuffle();
        }
    }

    @SpirePatch(clz = ShuffleAction.class, method = "update")
    public static class ShufflePatch2 {
        public static void Postfix(ShuffleAction __instance) {
            boolean b = ((Boolean) ReflectionHacks.getPrivate(__instance, ShuffleAction.class, "triggerRelics")).booleanValue();
            if (b)
                OnDrawPileShuffle();
        }
    }

    @SpirePatch(clz = ShuffleAllAction.class, method = "<ctor>")
    public static class ShufflePatch3 {
        public static void Postfix(ShuffleAllAction __instance) {
            OnDrawPileShuffle();
        }
    }
}