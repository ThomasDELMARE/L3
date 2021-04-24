package com.example.allumettes.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.util.AttributeSet;
import androidx.annotation.Nullable;
import com.example.allumettes.R;

public class Allumettes extends View {

    int nbLigne = 2;
    int hauteurAllumette;

    Drawable allumette;

    public Allumettes(Context context) {
        super(context);
        espace = 10;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            allumette = context.getDrawable(R.drawable.allumettes);
        }
        else {
            allumette = context.getResources().getDrawable(R.drawable.allumettes);
        }
    }

    public Allumettes(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        calculerDimensionAllumettes();
    }

    protected int calculerDimensionAllumettes() {
        int hauteurFenetre = getHeight();

        this.hauteurAllumette = (hauteurFenetre - 3 * this.padding)/3;
        this.largeurAllumette = hauteurAllumette * ratioMaximum;
        return 1;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int lx = (int) this.padding;
        int ly = (int) 2 * this.padding + lx + this.hauteurAllumette;
        int dx = (int) lx + largeurAllumette;
        int dy = (int) ly + hauteurAllumette;

        while(allumettesAPlacer != 0){
            for(int i =0; i< nombreAllumettesParLigne; i++){
                for(int j =0; i < nombreLignes; i++){
                    allumette.setBounds(lx, ly, dx, dy);
                    allumette.draw(canvas);
                }
            }
        }
    }
}