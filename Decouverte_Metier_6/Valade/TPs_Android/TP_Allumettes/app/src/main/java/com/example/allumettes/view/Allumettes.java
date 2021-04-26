package com.example.allumettes.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
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
    int nbAlumettesSelectionnees = 3;
    int nombreAllumettesVisibles = 18;

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

        pTiret = new Paint(pPlein);
        DashPathEffect effet = new DashPathEffect(new float[]{10, 25}, 0);
        pTiret.setPathEffect(effet);
        pTiret.setStrokeWidth(2);
        pTiret.setColor(Color.GRAY);
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

        int aPlacer = this.nombreTotalAllumettes;
        int dx, dy, lx = padding, ly = padding;

        // Allumettes normales

        for (int j = 0; j < this.nbLigne; j++) {
            for (int i = 0; i < this.nombreAllumettesParLigne; i++) {

                dx = lx + this.largeurAllumette;
                dy = ly + this.hauteurAllumette;

                if (nombreAllumettesVisibles > 0) {
                    allumette.setBounds(lx, ly, dx, dy);
                    allumette.draw(canvas);
                    nombreAllumettesVisibles -= 1;

                    // CAS POUR LES ALLUMETTES SELECTIONNES
                    // A CHANGER PLUS TARD POUR LES ALLUMETTES CLIQUEES OU PAS
                    if (nombreAllumettesVisibles < getNbAlumettesSelectionnees()) {
                        canvas.drawRect(lx - padding / 4, ly - padding / 4, dx + padding / 4, dy + padding / 4, pPlein);
                    }
                } else {
                    // CAS POUR LES ALLUMETTTES ENLEVEES
                    canvas.drawRect(lx, ly, dx, dy, pTiret);
                }

                lx += 2 * largeurAllumette;

                aPlacer -= 1;

                if (aPlacer == 0) {
                    break;
                }

            }
            lx = padding;
            ly += hauteurAllumette + padding;
        }
    }

    public int getNbAlumettesSelectionnees() {
        return nbAlumettesSelectionnees;
    }

    public void setNbAlumettesSelectionnees(int nbAlumettesSelectionnees) {
        this.nbAlumettesSelectionnees = nbAlumettesSelectionnees;
    }
}