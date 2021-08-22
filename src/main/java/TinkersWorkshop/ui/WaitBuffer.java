package TinkersWorkshop.ui;

import TinkersWorkshop.relics.selection.SelectionToken;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import static TinkersWorkshop.util.actionShortcuts.p;

public class WaitBuffer extends AbstractGameEffect
{

    public WaitBuffer(float time)
    {
        duration = time;
    }

    @Override
    public void update()
    {
        duration -= Gdx.graphics.getDeltaTime();
        isDone = duration <= 0f;
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