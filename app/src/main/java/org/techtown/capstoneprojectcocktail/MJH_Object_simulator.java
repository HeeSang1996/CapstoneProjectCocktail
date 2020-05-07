package org.techtown.capstoneprojectcocktail;

public class MJH_Object_simulator {

    public MJH_Object_cocktail[] simulator_step = new MJH_Object_cocktail[100]; //step은 백 까지만

    public MJH_Object_simulator(int _glass_type, int _ice_type){ // step1: 잔, 얼음타입 선택
        this.simulator_step[0] = new MJH_Object_cocktail();
        this.simulator_step[0].ice_type = _ice_type; // 0=없음, 1=박스얼음, 2=원형
        this.simulator_step[0].glass_type = _glass_type; // 0=하이볼, 1=락글라스, 2=마티니, 3=허리케인?
    }

    //빌드 스터 셰이크 푸어 롤링
    public void add_step_buildings(int step_num, int associate_num, int[] associate_step, int ingredient_num, MJH_Object_ingredient[] input_ingredient, float[] amount){
        // 스탭번호, 재료로 사용할 스탭의 갯수, 재료로 사요할 스탭들의 인덱스, 재료의 갯수, 재료, 재료의 양
        this.simulator_step[step_num - 1] =  calc_kind_build(step_num, associate_num, associate_step, ingredient_num, input_ingredient, amount);
    }

    public void add_step_layering(int step_num, int associate_step_num, int ingredient_num, MJH_Object_ingredient[] input_ingredient, float[] amount){ // (스탭번호, 재료인풋, 재료량(ml))

    }

    public void add_step_muddle(int step_num, int associate_step_num, int ingredient_num, MJH_Object_ingredient[] input_ingredient, float[] amount){ // (스탭번호, 재료인풋, 재료량(ml류))

    }


    // 빌드 같이 한번에 재료 한개 넣는 칵테일 (빌드 스터 셰이크 푸어 롤링)
    public MJH_Object_cocktail calc_kind_build(int step_num, int associate_num, int[] associate_step, int ingredient_num, MJH_Object_ingredient[] input_ingredient, float[] input_amount){

        MJH_Object_cocktail cocktail_buffer = new MJH_Object_cocktail();

        try {
            if(associate_num == 1){
                cocktail_buffer = (MJH_Object_cocktail) this.simulator_step[associate_step[0]].clone();
            }
            else if(associate_num >= 1){ // 여러 단계를 사용할때
                for(int i = 0; i < associate_num; i++){
                    //도수계산
                    cocktail_buffer.total_abv = (cocktail_buffer.total_volume * cocktail_buffer.total_abv + this.simulator_step[associate_step[i]].total_volume * this.simulator_step[associate_step[i]].total_abv)
                            / (cocktail_buffer.total_volume + this.simulator_step[associate_step[i]].total_volume);

                    //비중계산
                    float weight_origin, weight_add;
                    float volume_origin = cocktail_buffer.total_volume, volume_add = this.simulator_step[associate_step[i]].total_volume;
                    float new_gravity;
                    weight_origin = cocktail_buffer.total_volume * cocktail_buffer.specific_gravity[0];
                    weight_add = this.simulator_step[associate_step[i]].total_volume * this.simulator_step[associate_step[i]].specific_gravity[0];
                    new_gravity = (weight_origin + weight_add) / (volume_origin + volume_add);
                    cocktail_buffer.specific_gravity[0] = new_gravity;

                    //색 변경
                    MJH_Object_color color_buffer;
                    if (i == 0) // 첫 인풋일때 색 셋팅
                        cocktail_buffer.is_color[0] = new MJH_Object_color(this.simulator_step[associate_step[i]].is_color[0].rgb_red,
                                simulator_step[associate_step[i]].is_color[0].rgb_green, simulator_step[associate_step[i]].is_color[0].rgb_blue);
                    else { // 색 섞일때
                        color_buffer = add_color(cocktail_buffer.is_color[0], simulator_step[associate_step[i]].is_color[0], cocktail_buffer.total_volume, this.simulator_step[associate_step[i]].total_volume);
                        cocktail_buffer.is_color[0] = new MJH_Object_color(color_buffer.rgb_red, color_buffer.rgb_green, color_buffer.rgb_blue);
                    }

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
                cocktail_buffer.total_abv = (cocktail_buffer.total_volume * cocktail_buffer.total_abv + input_amount[i] * input_ingredient[i].abv)
                        / (cocktail_buffer.total_volume + input_amount[i]);

                //비중계산
                float weight_origin, weight_add;
                float volume_origin = cocktail_buffer.total_volume, volume_add = input_amount[i];
                float new_gravity;
                weight_origin = cocktail_buffer.total_volume * cocktail_buffer.specific_gravity[0];
                weight_add = input_amount[i] * input_ingredient[i].specific_gravity;
                new_gravity = (weight_origin + weight_add) / (volume_origin + volume_add);
                cocktail_buffer.specific_gravity[0] = new_gravity;

                //색 변경
                MJH_Object_color color_buffer;
                if (step_num == 1) // 첫 인풋일때 색 셋팅
                    cocktail_buffer.is_color[0] = new MJH_Object_color(input_ingredient[i].my_color.rgb_red, input_ingredient[i].my_color.rgb_green, input_ingredient[i].my_color.rgb_blue);
                else { // 색 섞일때
                    color_buffer = add_color(cocktail_buffer.is_color[0], input_ingredient[i].my_color, cocktail_buffer.total_volume, input_amount[i]);
                    cocktail_buffer.is_color[0] = new MJH_Object_color(color_buffer.rgb_red, color_buffer.rgb_green, color_buffer.rgb_blue);
                }

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
}
