package com.example.allumettes.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.renderscript.ScriptIntrinsicConvolve5x5;
import android.view.View;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.example.allumettes.R;

public class Allumettes extends View {

    int padding = 30;
    int nbLigne = 2;
    int nombreAllumettesParLigne = 11;

    int nombreTotalAllumettes = 21;
    int nbAlumettesSelectionnees = 0;
    int nombreAllumettesVisibles = 21;

    int largeurAllumette;
    int hauteurAllumette;
    Paint pTiret;
    Paint pPlein;
    boolean etatInitial = false;


    Drawable allumette;

    public Allumettes(Context context) {
        super(context, null);
    }

    public Allumettes(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            allumette = context.getDrawable(R.drawable.allumettes);
        } else {
            allumette = context.getResources().getDrawable(R.drawable.allumettes);
        }

        pPlein = new Paint();

        pPlein.setColor(Color.rgb(0, 128, 0));
        pPlein.setAntiAlias(true);
        pPlein.setStrokeWidth(padding / 2);
        pPlein.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        calculerDimensionAllumettes();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        calculerDimensionAllumettes();
    }

    protected void calculerDimensionAllumettes() {
        int largeurFenetre = getWidth();

        this.largeurAllumette = (largeurFenetre - 2 * this.padding) / (this.nombreAllumettesParLigne * 2 - 1);
        this.hauteurAllumette = (int) (largeurAllumette / 0.15);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        int aPlacer = this.nombreAllumettesVisibles;
        int dx, dy, lx = padding, ly = padding;

        // Allumettes normales

        for (int j = 0; j < this.nbLigne; j++) {
            for (int i = 0; i < nombreAllumettesParLigne; i++) {

                dx = lx + this.largeurAllumette;
                dy = ly + this.hauteurAllumette;

                allumette.setBounds(lx, ly, dx, dy);
                allumette.draw(canvas);

                lx += 2 * largeurAllumette;

                // CAS POUR LES ALLUMETTES SELECTIONNES
                if (aPlacer <= nbAlumettesSelectionnees) {
                    // TODO : Completer
                }

                // CAS POUR LES ALLUMETTTES VIDES
                if (aPlacer < nombreAllumettesParLigne * nbLigne) {
                    // TODO : Completer
                }

                aPlacer -= 1;

                if (aPlacer == 0) {
                    break;
                }

            }
            lx = padding;
            ly += hauteurAllumette + padding;
        }
    }
}