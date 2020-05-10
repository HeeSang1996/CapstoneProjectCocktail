package org.techtown.capstoneprojectcocktail;

public class MJH_Object_simulator {

    int total_step = 0;
    int in_glass_step = 0;
    public MJH_Object_cocktail[] simulator_step = new MJH_Object_cocktail[100]; //step은 백 까지만

    MJH_Object_ingredient[] muddle_list = new MJH_Object_ingredient[100];
    int muddle_num = 0;

    public MJH_Object_simulator(int _glass_type, int _ice_type){ // step1: 잔, 얼음타입 선택
        this.simulator_step[0] = new MJH_Object_cocktail();
        this.simulator_step[0].ice_type = _ice_type; // 0=없음, 1=박스얼음, 2=원형
        this.simulator_step[0].glass_type = _glass_type; // 0=하이볼, 1=콜린스잔, 2=락글라스, 3=마티니, 4=허리케인?
    }

    //빌드 스터 셰이크 푸어 롤링 (스탭번호, 재료로 사용할 스탭의 갯수, 재료로 사요할 스탭들의 인덱스, 재료의 갯수, 재료, 재료의 양)
    public void add_step_buildings(int step_num, int associate_num, int[] associate_step, int ingredient_num, MJH_Object_ingredient[] input_ingredient, float[] amount, boolean is_in){
        // (스탭번호, 재료로 사용할 스탭의 갯수, 재료로 사용할 스탭들의 인덱스, 재료의 갯수, 재료, 재료의 양)
        this.simulator_step[step_num - 1] =  calc_kind_build(step_num, associate_num, associate_step, ingredient_num, input_ingredient, amount);

<<<<<<< HEAD
        if(is_in == true){ // 현재 글래스에 담긴 음료에 대한 설정
           for(int i = 0; i < step_num; i++){
                if(this.simulator_step[i].is_in_glass == true)
                    this.simulator_step[i].is_in_glass = false;
            }
            this.simulator_step[step_num - 1].is_in_glass = true;
           in_glass_step = step_num;
        }

        total_step++;
=======
    public void add_step_layering(int step_num, int associate_step_num, int ingredient_num, MJH_Object_ingredient[] input_ingredient, float[] amount){ // (스탭번호, 재료인풋, 재료량(ml))
        //민준홍씹탱이거 지건맞아라
        //컴파일 되는걸로 올려라
        // this.simulator_step[step_num - 1] =
>>>>>>> 6b177e6df28ddd0eaa30d59f0e9a9b46e8c3d744
    }

    public void add_step_layering(int step_num, int associate_step_num, int ingredient_num, MJH_Object_ingredient[] input_ingredient, float[] amount, int layering_type){ // (스탭번호, 재료인풋, 재료량(ml))
        MJH_Object_cocktail cocktail_buffer_layering = new MJH_Object_cocktail();

        if(is_there_cocktail_in_glass() != 9999){
            try{
                this.simulator_step[step_num - 1] = (MJH_Object_cocktail) this.simulator_step[is_there_cocktail_in_glass()].clone();

                calc_kind_layering(ingredient_num, input_ingredient, amount, layering_type);

                for(int i = 0; i < step_num; i++){
                    if(this.simulator_step[i].is_in_glass == true)
                        this.simulator_step[i].is_in_glass = false;
                }
                this.simulator_step[step_num - 1].is_in_glass = true;
                in_glass_step = step_num;
            }catch (Exception e){ }
        }
        else{
            calc_kind_layering(ingredient_num, input_ingredient, amount, layering_type);
            this.simulator_step[step_num - 1].is_in_glass = true;
            this.simulator_step[step_num - 1].is_layering = ingredient_num;
            in_glass_step = step_num;
        }
        total_step++;
    }

    public void add_step_muddle(int step_num, int associate_step_num, int ingredient_num, MJH_Object_ingredient[] input_ingredient){
        for( int i = muddle_num; i < (muddle_num+ingredient_num); i++){
            muddle_list[i] = input_ingredient[i - muddle_num];
        }
    }


    // 빌드 같이 한번에 재료 한개 넣는 칵테일 (빌드 스터 셰이크 푸어 롤링)
    // (현재스텝번호, 연관된 스텝갯수, 연관된 스텝번호....)_
    public MJH_Object_cocktail calc_kind_build(int step_num, int associate_num, int[] associate_step, int ingredient_num, MJH_Object_ingredient[] input_ingredient, float[] input_amount){

        MJH_Object_cocktail cocktail_buffer = new MJH_Object_cocktail();

        try { // 기존 스탭을 이용해 칵테일을 만들때
            if(associate_num > 0)
                cocktail_buffer = (MJH_Object_cocktail) this.simulator_step[associate_step[0]].clone();

            if(associate_num > 1){ // 여러 단계를 사용할때
                for(int i = 1; i < associate_num; i++){
                    //도수계산
                    cocktail_buffer.total_abv = calc_abv(cocktail_buffer.total_volume, cocktail_buffer.total_abv,
                            this.simulator_step[associate_step[i]].total_volume, this.simulator_step[associate_step[i]].total_abv);

                    //비중계산
                    cocktail_buffer.specific_gravity[0] = calc_specific_gravity(cocktail_buffer.total_volume, this.simulator_step[associate_step[i]].total_volume,
                            cocktail_buffer.specific_gravity[0], this.simulator_step[associate_step[i]].specific_gravity[0]);

                    //색 변경
                    cocktail_buffer.is_color[0] = change_color(cocktail_buffer.is_color[0], simulator_step[associate_step[i]].is_color[0], cocktail_buffer.total_volume, this.simulator_step[associate_step[i]].total_volume);

                    //량 추가
                    cocktail_buffer.total_volume = cocktail_buffer.total_volume + this.simulator_step[associate_step[i]].total_volume;

                    //기타 피쳐 추가
                    cocktail_buffer.sugar[0] = cocktail_buffer.sugar[0] + simulator_step[associate_step[i]].sugar[0];
                    cocktail_buffer.sour[0] = cocktail_buffer.sour[0] + simulator_step[associate_step[i]].sour[0];
                    cocktail_buffer.salty[0] = cocktail_buffer.salty[0] + simulator_step[associate_step[i]].salty[0];
                    cocktail_buffer.bitter[0] = cocktail_buffer.bitter[0] + simulator_step[associate_step[i]].bitter[0];
                    //(this.simulator_step[step_num].flavour[0] = @@@

                }
            }


            for(int i = 0; i < ingredient_num; i++){
                //도수계산
                cocktail_buffer.total_abv = calc_abv(cocktail_buffer.total_volume, cocktail_buffer.total_abv,
                        input_amount[i], input_ingredient[i].abv);

                //비중계산
                calc_specific_gravity(cocktail_buffer.total_volume, input_amount[i], cocktail_buffer.specific_gravity[0], input_ingredient[i].specific_gravity);

                //색 변경
                if (step_num == 1 && i == 0)
                    cocktail_buffer.is_color[0] = new MJH_Object_color(input_ingredient[i].my_color.rgb_red, input_ingredient[i].my_color.rgb_green, input_ingredient[i].my_color.rgb_blue);
                else
                    cocktail_buffer.is_color[0] = change_color(cocktail_buffer.is_color[0], input_ingredient[i].my_color, cocktail_buffer.total_volume, input_amount[i]);

                //량 추가
                cocktail_buffer.total_volume = cocktail_buffer.total_volume + input_amount[i];

                //기타 피쳐 추가
                cocktail_buffer.sugar[0] = cocktail_buffer.sugar[0] + input_ingredient[i].sugar;
                cocktail_buffer.sour[0] = cocktail_buffer.sour[0] + input_ingredient[i].sour;
                cocktail_buffer.salty[0] = cocktail_buffer.salty[0] + input_ingredient[i].salty;
                cocktail_buffer.bitter[0] = cocktail_buffer.bitter[0] + input_ingredient[i].bitter;

                //(this.simulator_step[step_num].flavour[0] = @@@
            }

        }catch (Exception e){ }

        return cocktail_buffer;
    }

    //현재 그래디언트는 한번만됨
    public void calc_kind_layering(int ingredient_num, MJH_Object_ingredient[] input_ingredient, float[] amount, int layering_type){
        //int layering_type: 0,1,2->약,중,강 그라데이션, 3->완벽한 플로팅, 4->어설픈 플로팅

        int layer_num_buffer = this.simulator_step[in_glass_step - 1].is_layering;

        if(layering_type == 0){
            array_push_for_gradient(input_ingredient[0], amount[0], 30);
            this.simulator_step[in_glass_step - 1].is_layering++;
        }
        if(layering_type == 1){
            array_push_for_gradient(input_ingredient[0], amount[0], 50);
            this.simulator_step[in_glass_step - 1].is_layering++;
        }
        if(layering_type == 2){
            this.simulator_step[in_glass_step - 1].is_layering = make_layer(layer_num_buffer, ingredient_num, input_ingredient, amount);
        }
        if(layering_type == 3){
            this.simulator_step[in_glass_step - 1].is_layering = make_layer(layer_num_buffer, ingredient_num, input_ingredient, amount);
            for(int i = layer_num_buffer; i < this.simulator_step[in_glass_step - 1].is_layering; i++){
                this.simulator_step[in_glass_step - 1].is_boundary_dirty[i] = 1; //1이면 yes
            }
        }

        this.simulator_step[in_glass_step - 1].add_vol_abv_total();
    }

    public MJH_Object_color add_color(MJH_Object_color color_one, MJH_Object_color color_two, float vol_one, float vol_two){
        float result_red;
        float result_blue;
        float result_green;

        result_red = ((color_one.rgb_red * vol_one) + (color_two.rgb_red * vol_two)) / (vol_one + vol_two);
        result_green = ((color_one.rgb_green * vol_one) + (color_two.rgb_green * vol_two)) / (vol_one + vol_two);
        result_blue = ((color_one.rgb_blue * vol_one) + (color_two.rgb_blue* vol_two)) / (vol_one + vol_two);

        MJH_Object_color color_result = new MJH_Object_color(result_red, result_green, result_blue);
        return color_result;
    }

    public float calc_abv(float a_volume, float a_abv, float b_volume, float b_abv){
       return (a_volume * a_abv + b_volume * b_abv)
                / (a_volume + b_volume);
    }

    public float calc_specific_gravity(float volume_origin, float volume_add, float specific_gravity_origin, float specific_gravity_add){
        float weight_origin = volume_origin * specific_gravity_origin;
        float weight_add = volume_add * specific_gravity_add;
        return (weight_origin + weight_add) / (volume_origin + volume_add);
    }

    public MJH_Object_color change_color( MJH_Object_color a_color, MJH_Object_color b_color, float a_volume, float b_volume ){
        MJH_Object_color color_buffer;
        color_buffer = add_color(a_color, b_color, a_volume, b_volume);
        return color_buffer;
    }

    public int is_there_cocktail_in_glass(){

        for(int i = 0; i < total_step; i++){
            if(this.simulator_step[i].is_in_glass == true)
                return i;
        }
        return 9999;
    }

    public void array_push_for_gradient(MJH_Object_ingredient input_ingredient, float amount, int gradient_rate){
        for(int i = 14; i > 0; i--){ // 내용물 shift, 맨밑엔 시럽이!
            this.simulator_step[in_glass_step - 1].each_volume[i] = this.simulator_step[in_glass_step - 1].each_volume[i-1];
            this.simulator_step[in_glass_step - 1].each_abv[i] = this.simulator_step[in_glass_step - 1].each_abv[i-1];
            this.simulator_step[in_glass_step - 1].specific_gravity [i] = this.simulator_step[in_glass_step - 1].specific_gravity [i-1];
            this.simulator_step[in_glass_step - 1].is_color[i] = this.simulator_step[in_glass_step - 1].is_color[i-1];

            this.simulator_step[in_glass_step - 1].sugar[i] = this.simulator_step[in_glass_step - 1].sugar[i-1];
            this.simulator_step[in_glass_step - 1].sour[i] = this.simulator_step[in_glass_step - 1].sour[i-1];
            this.simulator_step[in_glass_step - 1].salty[i] = this.simulator_step[in_glass_step - 1].salty[i-1];
            this.simulator_step[in_glass_step - 1].bitter[i] = this.simulator_step[in_glass_step - 1].bitter[i-1];
        }

        this.simulator_step[in_glass_step - 1].each_volume[0] = amount;
        this.simulator_step[in_glass_step - 1].each_abv[0] = input_ingredient.abv;
        this.simulator_step[in_glass_step - 1].specific_gravity [0] = input_ingredient.specific_gravity;
        this.simulator_step[in_glass_step - 1].is_color[0] = input_ingredient.my_color;
        this.simulator_step[in_glass_step - 1].is_gradient = gradient_rate;

        this.simulator_step[in_glass_step - 1].sugar[0]= input_ingredient.sugar;
        this.simulator_step[in_glass_step - 1].sour[0] = input_ingredient.sour;
        this.simulator_step[in_glass_step - 1].salty[0] = input_ingredient.salty;
        this.simulator_step[in_glass_step - 1].bitter[0] = input_ingredient.bitter;
    }

    public int make_layer(int now_layer, int ingredient_num, MJH_Object_ingredient[] input_ingredient, float[] amount){
        int return_value = now_layer + ingredient_num;

        for(int i = now_layer; i <  return_value ; i++){
            this.simulator_step[in_glass_step - 1].each_volume[i] = amount[i -now_layer];
            this.simulator_step[in_glass_step - 1].each_abv[i] = input_ingredient[i -now_layer].abv;
            this.simulator_step[in_glass_step - 1].specific_gravity[i] = input_ingredient[i -now_layer].specific_gravity;
            this.simulator_step[in_glass_step - 1].is_color[i] = input_ingredient[i -now_layer].my_color;

            this.simulator_step[in_glass_step - 1].sugar[i]= input_ingredient[i -now_layer].sugar;
            this.simulator_step[in_glass_step - 1].sour[i] = input_ingredient[i -now_layer].sour;
            this.simulator_step[in_glass_step - 1].salty[i] = input_ingredient[i -now_layer].salty;
            this.simulator_step[in_glass_step - 1].bitter[i] = input_ingredient[i -now_layer].bitter;
        }

        return return_value;
    }
}

/*
public MJH_Object_ingredient simul_step_to_ingredient(MJH_Object_cocktail s){
    MJH_Object_ingredient retrun_ingre = new MJH_Object_ingredient(s.specific_gravity[0], s.total_abv, s.sugar[0], s.sour[0], s.salty[0], s.bitter[0], s.is_color[0]);
    return retrun_ingre;
}
*/