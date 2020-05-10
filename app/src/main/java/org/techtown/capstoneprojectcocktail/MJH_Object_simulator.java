package org.techtown.capstoneprojectcocktail;

public class MJH_Object_simulator {

    public MJH_Object_cocktail[] simulator_step = new MJH_Object_cocktail[100]; //step은 백 까지만

    public MJH_Object_simulator(int _glass_type, int _ice_type){ // step1: 잔, 얼음타입 선택
        this.simulator_step[0] = new MJH_Object_cocktail();
        this.simulator_step[0].ice_type = _ice_type; // 0=없음, 1=박스얼음, 2=원형
        this.simulator_step[0].glass_type = _glass_type; // 0=하이볼, 1=콜린스잔, 2=락글라스, 3=마티니, 4=허리케인?
    }

    //빌드 스터 셰이크 푸어 롤링 (스탭번호, 재료로 사용할 스탭의 갯수, 재료로 사요할 스탭들의 인덱스, 재료의 갯수, 재료, 재료의 양)
    public void add_step_buildings(int step_num, int associate_num, int[] associate_step, int ingredient_num, MJH_Object_ingredient[] input_ingredient, float[] amount){
        // (스탭번호, 재료로 사용할 스탭의 갯수, 재료로 사요할 스탭들의 인덱스, 재료의 갯수, 재료, 재료의 양)
        this.simulator_step[step_num - 1] =  calc_kind_build(step_num, associate_num, associate_step, ingredient_num, input_ingredient, amount);
    }

    public void add_step_layering(int step_num, int associate_step_num, int ingredient_num, MJH_Object_ingredient[] input_ingredient, float[] amount){ // (스탭번호, 재료인풋, 재료량(ml))
        //민준홍씹탱이거 지건맞아라
        //컴파일 되는걸로 올려라
        // this.simulator_step[step_num - 1] =
    }

    public void add_step_muddle(int step_num, int associate_step_num, int ingredient_num, MJH_Object_ingredient[] input_ingredient, float[] amount){ // (스탭번호, 재료인풋, 재료량(ml류))

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

}
