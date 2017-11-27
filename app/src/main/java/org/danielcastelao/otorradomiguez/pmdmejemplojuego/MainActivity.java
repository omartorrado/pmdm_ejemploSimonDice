package org.danielcastelao.otorradomiguez.pmdmejemplojuego;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {

    int turnosTotales=0;
    int posicionSecuencia =0;
    int posicionCheck=0;
    int[] colores=new int[50];
    boolean esCorrecto=false;

    Button botonVerde;
    Button botonAmarillo;
    Button botonAzul;
    Button botonRojo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botonVerde=(Button)findViewById(R.id.boton_verde);
        botonAmarillo=(Button)findViewById(R.id.boton_amarillo);
        botonAzul=(Button)findViewById(R.id.boton_azul);
        botonRojo=(Button)findViewById(R.id.boton_rojo);

        botonVerde.setBackgroundColor(Color.GREEN);
        botonVerde.setAlpha(0.5f);
        botonAmarillo.setBackgroundColor(Color.YELLOW);
        botonAmarillo.setAlpha(0.5f);
        botonAzul.setBackgroundColor(Color.BLUE);
        botonAzul.setAlpha(0.5f);
        botonRojo.setBackgroundColor(Color.RED);
        botonRojo.setAlpha(0.5f);

        findViewById(R.id.boton_nueva).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nuevaPartida();
            }
        });
        findViewById(R.id.boton_empezar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generarSecuencia();

            }
        });
        botonAzul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comprobarColor(Color.BLUE);
                iluminarBoton(botonAzul);
            }
        });
        botonRojo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comprobarColor(Color.RED);
                iluminarBoton(botonRojo);
            }
        });
        botonVerde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comprobarColor(Color.GREEN);
                iluminarBoton(botonVerde);
            }
        });
        botonAmarillo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comprobarColor(Color.YELLOW);
                iluminarBoton(botonAmarillo);
            }
        });



    }

    public void nuevaPartida(){
        findViewById(R.id.color_center).setBackgroundColor(Color.GRAY);
        turnosTotales=0;
    }

    public void updateBackground(int color){
        View fondo=findViewById(R.id.color_center);
        fondo.setBackgroundColor(color);
    }

    public void generarSecuencia(){
        Random rd=new Random();
        int[] coloresDisponibles={Color.RED,Color.BLUE,Color.GREEN,Color.YELLOW};
        colores[turnosTotales]=coloresDisponibles[rd.nextInt(4)];
        TextView t= (TextView) findViewById(R.id.turno);
        t.setText("Turno: "+(turnosTotales+1));
        turnosTotales++;
        mostrarSecuencia();
        esCorrecto=true;
        posicionCheck=0;
    }

    public void mostrarSecuencia(){
        final View fondo=findViewById(R.id.color_center);
        posicionSecuencia=0;
        for(int i=0;i<turnosTotales;i++) {
            if (colores[i] != 0) {
                fondo.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        updateBackground(colores[posicionSecuencia]);
                        fondo.setAlpha(0.2f);
                    }
                }, (i+1) * 500);

                //cambios de brillo aqui dentro
                fondo.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        fondo.setAlpha(0.5f);
                    }
                },(500*(i+1))+100);
                fondo.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        fondo.setAlpha(0.9f);
                    }
                },(500*(i+1))+150);

                fondo.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        fondo.setAlpha(1f);
                    }
                },(500*(i+1))+250);
                fondo.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        fondo.setAlpha(0.9f);
                    }
                },(500*(i+1))+350);
                fondo.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        fondo.setAlpha(0.5f);
                    }
                },(500*(i+1))+400);

                fondo.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        fondo.setAlpha(0.2f);
                        posicionSecuencia++;
                    }
                },(500*(i+1))+450);

            }

        }
        fondo.postDelayed(new Runnable() {
            @Override
            public void run() {
                updateBackground(Color.GRAY);
                fondo.setAlpha(1f);
                botonAmarillo.setEnabled(true);
                botonAzul.setEnabled(true);
                botonRojo.setEnabled(true);
                botonVerde.setEnabled(true);
            }
        },(turnosTotales+1)*500);

        Button botonStart=(Button)findViewById(R.id.boton_empezar);
        botonStart.setEnabled(false);

    }

    public void comprobarColor(int color){
        Button botonStart=(Button)findViewById(R.id.boton_empezar);
        final Button loseMessage=(Button)findViewById(R.id.color_center);
        if(color==colores[posicionCheck]){
            posicionCheck++;
        }else{
            esCorrecto=false;
        }

        if(posicionCheck==turnosTotales && esCorrecto){
            botonStart.setEnabled(true);
            botonAmarillo.setEnabled(false);
            botonAzul.setEnabled(false);
            botonRojo.setEnabled(false);
            botonVerde.setEnabled(false);
            loseMessage.postDelayed(new Runnable() {
                @Override
                public void run() {
                    loseMessage.setText("CORRECTO");
                    loseMessage.setScaleX(3f);
                    loseMessage.setScaleY(3f);
                    loseMessage.setBackgroundColor(Color.WHITE);
                }
            },50);
            loseMessage.postDelayed(new Runnable() {
                @Override
                public void run() {
                    loseMessage.setBackgroundColor(Color.GRAY);
                    loseMessage.setScaleX(1f);
                    loseMessage.setScaleY(1f);
                    loseMessage.setText("");
                                    }
            },1000);
        }
        if(!esCorrecto && turnosTotales>0){
            loseMessage.postDelayed(new Runnable() {
                @Override
                public void run() {
                    loseMessage.setBackgroundColor(Color.WHITE);
                    loseMessage.setText("HAS PERDIDO");
                    loseMessage.setScaleX(3f);
                    loseMessage.setScaleY(3f);
                }
            },50);
            loseMessage.postDelayed(new Runnable() {
                @Override
                public void run() {
                    loseMessage.setText("");
                    loseMessage.setScaleX(1f);
                    loseMessage.setScaleY(1f);
                    loseMessage.setBackgroundColor(Color.GRAY);
                    TextView t= (TextView) findViewById(R.id.turno);
                    t.setText("Turno: 0");
                }
            },1000);

            nuevaPartida();
            botonStart.setEnabled(true);
        }
    }

    public void iluminarBoton(Button boton){
        final Button b=boton;
        b.postDelayed(new Runnable() {
            @Override
            public void run() {
                b.setAlpha(0.5f);
            }
        },0);
        b.postDelayed(new Runnable() {
            @Override
            public void run() {
                b.setAlpha(0.75f);
            }
        },50);
        b.postDelayed(new Runnable() {
            @Override
            public void run() {
                b.setAlpha(1f);
            }
        },100);
        b.postDelayed(new Runnable() {
            @Override
            public void run() {
                b.setAlpha(0.75f);
            }
        },250);
        b.postDelayed(new Runnable() {
            @Override
            public void run() {
                b.setAlpha(0.5f);
            }
        },300);
    }
}
