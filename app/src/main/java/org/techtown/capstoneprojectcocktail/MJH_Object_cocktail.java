package org.techtown.capstoneprojectcocktail;

public class MJH_Object_cocktail implements Cloneable{

    int ice_type = 0;
    int glass_type = 0;

    int is_layering = 0; // n+1: 색의 갯수
    float total_volume = 0;
    float total_abv = 0; // 단위 %

    float[] each_volume = new float[15];
    float[] each_abv = new float[15]; //단위 %
    float[] specific_gravity = new float[15];
    MJH_Object_color[] is_color = new MJH_Object_color[15];

    float[] sugar = new float[15]; // 단위 g
    float[] sour = new float[15];
    float[] salty = new float[15];
    float[] bitter = new float[15];

    String[] flavour = new String[15];

    public MJH_Object_cocktail(){

    }

    @Override
    public Object clone() throws CloneNotSupportedException{ //깊은복사
        //CloneNotSupportedException 처리
        return super.clone();
    }

    public void add_step_result(int _is_layering, int now_layer, float now_volume, float now_abv, float now_gravity, MJH_Object_color now_color, float now_sugar, float now_sour, float now_salty, float now_bitter,
                                String now_flavour){
        this.is_layering = _is_layering;

        if (this.is_layering == 0 && now_layer == 0){
            this.total_volume = now_volume;
            this.each_volume[now_layer] = now_volume;
            this.total_abv = now_abv;
            this.each_abv[now_layer] = now_abv;
            this.specific_gravity[now_layer] = now_gravity;
            this.is_color[now_layer] = now_color;
            this.sugar[now_layer] = now_sugar;
            this.sour[now_layer] = now_sour;
            this.salty[now_layer] = now_salty;
            this.bitter[now_layer] = now_bitter;
            this.flavour[now_layer] = now_flavour;
        }
        else{
            this.each_volume[now_layer-1] = now_volume;
            this.each_abv[now_layer-1] = now_abv;
            this.specific_gravity[now_layer-1] = now_gravity;
            this.is_color[now_layer-1] = now_color;
            this.sugar[now_layer-1] = now_sugar;
            this.sour[now_layer-1] = now_sour;
            this.salty[now_layer-1] = now_salty;
            this.bitter[now_layer-1] = now_bitter;
            this.flavour[now_layer-1] = now_flavour;

            for(int i = 0; i < now_layer; i++){
                this.total_volume = this.total_volume + this.each_volume[i];
                this.total_abv = this.total_abv + this.each_abv[i];
            }
        }

    }

}
