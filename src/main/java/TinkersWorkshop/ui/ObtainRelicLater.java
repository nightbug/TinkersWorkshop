package TinkersWorkshop.ui;

import TinkersWorkshop.relics.selection.SelectionToken;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
        import com.megacrit.cardcrawl.core.Settings;
        import com.megacrit.cardcrawl.relics.AbstractRelic;
        import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import static TinkersWorkshop.util.actionShortcuts.p;

public class ObtainRelicLater extends AbstractGameEffect
{
    private AbstractRelic relic;

    public ObtainRelicLater(AbstractRelic relic)
    {
        this.relic = relic;
        duration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update()
    {
        if(p().hasRelic(SelectionToken.ID)){
            p().relics.remove(p().getRelic(SelectionToken.ID));
        }
        relic.obtain();
        isDone = true;
    }

    @Override
    public void render(SpriteBatch spriteBatch)
    {

    }

    @Override
    public void dispose()
    {

    }
}